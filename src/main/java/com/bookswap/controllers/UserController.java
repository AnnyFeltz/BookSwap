package com.bookswap.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bookswap.models.User;
import com.bookswap.repository.UserRepository;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

public class UserController {

    private final UserRepository userRepository;
    private final ImgbbService imgbbService;

    public UserController() {
        this.userRepository = new UserRepository();
        this.imgbbService = new ImgbbService();
    }

    public void verPerfil(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.redirect("/login");
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        
        ctx.render("profile.ftl", model);
    }

    public void atualizarPerfil(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.status(401).result("Acesso não autorizado. Faça login novamente");
            return;
        }

        String novoNome = ctx.formParam("nome");
        String novoEmail = ctx.formParam("email");
        
        if (novoNome != null && !novoNome.isEmpty()) {
            user.setNome(novoNome);
        }

        if (novoEmail != null && !novoEmail.isEmpty() && !novoEmail.equals(user.getEmail())) {
            user.setEmail(novoEmail);
        }

        userRepository.update(user);
        ctx.sessionAttribute("user", user); 

        ctx.redirect("/perfil?msg=conta_atualizada");
    }

    public void deletarConta(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if(user == null) {
            ctx.status(401).result("Não autorizado");
            return;
        }

        userRepository.delete(user.getId());

        ctx.sessionAttribute("user", null); 

        ctx.redirect("/?msg=conta_deletada");
    }
    
    public void uploadFotoPerfil(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.status(401).result("Acesso não autorizado. Faça login novamente.");
            return;
        }

        UploadedFile file = ctx.uploadedFile("foto");

        if (file == null || file.contentType() == null || !file.contentType().startsWith("image")) {
            ctx.status(400).result("Nenhum arquivo de imagem válido enviado.");
            return;
        }

        try {
            String newUrl = imgbbService.uploadImage(file);
            
            user.setFotoPerfil(newUrl);
            
            userRepository.update(user);
            ctx.sessionAttribute("user", user);

            ctx.redirect("/perfil?msg=foto_atualizada");

        } catch (IOException e) {
            e.printStackTrace();
            ctx.status(500).result("Erro ao fazer upload da foto: " + e.getMessage());
        }
    }
}