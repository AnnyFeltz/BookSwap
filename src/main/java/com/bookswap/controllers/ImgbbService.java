package com.bookswap.controllers; 

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL; 
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import io.javalin.http.UploadedFile;

public class ImgbbService {

    private static final String IMGBB_API_KEY = "91b3954fffff0ca96f04a0d9ce319344"; 
    private static final String IMGBB_UPLOAD_URL = "https://api.imgbb.com/1/upload";

    public String uploadImage(UploadedFile file) throws IOException {
        
        byte[] fileBytes = file.content().readAllBytes(); 
        String base64Image = Base64.getEncoder().encodeToString(fileBytes);

        String encodedBase64 = URLEncoder.encode(base64Image, StandardCharsets.UTF_8.toString());

        String payloadString = "key=" + IMGBB_API_KEY + "&image=" + encodedBase64;

        byte[] payloadBytes = payloadString.getBytes(StandardCharsets.UTF_8);

        URL url;
        try {
            url = new URI(IMGBB_UPLOAD_URL).toURL();
        } catch (URISyntaxException e) {
            throw new IOException("URL do ImgBB inválida: " + e.getMessage());
        }
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(payloadBytes.length)); 

        try (var os = conn.getOutputStream()) {
            os.write(payloadBytes); 
        }

        int responseCode = conn.getResponseCode();
        
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String jsonResponse;
            try (var is = conn.getInputStream();
                 var reader = new java.io.BufferedReader(new java.io.InputStreamReader(is))) {
                jsonResponse = reader.lines().collect(java.util.stream.Collectors.joining("\n"));
            }

            if (jsonResponse.contains("\"url\":")) {
                int start = jsonResponse.indexOf("\"url\":\"") + 7;
                int end = jsonResponse.indexOf("\"", start);
                return jsonResponse.substring(start, end).replace("\\/", "/"); 
            } else { 
                throw new IOException("Erro ao extrair URL da resposta do ImgBB. JSON: " + jsonResponse);
            }
        } else {
            String errorMsg = "";
            try (var is = conn.getErrorStream();
                 var reader = new java.io.BufferedReader(new java.io.InputStreamReader(is))) {
                errorMsg = reader.lines().collect(java.util.stream.Collectors.joining("\n"));
            } catch (Exception ignore) {}
            
            throw new IOException("Erro no upload para ImgBB. Código: " + responseCode + " - Resposta: " + errorMsg);
        }
    }
}