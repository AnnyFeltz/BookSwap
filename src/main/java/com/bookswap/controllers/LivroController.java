package com.bookswap.controllers;

import java.io.IOException; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bookswap.models.Livro;
import com.bookswap.models.User;
import com.bookswap.models.subModels.LivroStatus;
import com.bookswap.repository.LivroRepository;
import com.bookswap.repository.UserRepository;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile; 

public class LivroController {

    private final LivroRepository livroRepository;
    private final UserRepository userRepository;
    private final ImgbbService imgbbService; 

    public LivroController() {
        this.livroRepository = new LivroRepository();
        this.userRepository = new UserRepository();
        this.imgbbService = new ImgbbService(); 
    }

    public void cadastrarLivro(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.status(401).result("Acesso não autorizado. Faça login novamente");
            return;
        }
        
        String titulo = ctx.formParam("titulo");
        String autor = ctx.formParam("autor"); 
        String estadoCondicao = ctx.formParam("estadoCondicao"); 
        String precoCreditosStr = ctx.formParam("precoCreditos"); 
        UploadedFile fotoFile = ctx.uploadedFile("fotoCapa"); 

        if (titulo == null || titulo.isEmpty() || autor == null || autor.isEmpty() || estadoCondicao == null || estadoCondicao.isEmpty() || precoCreditosStr == null || precoCreditosStr.isEmpty() || fotoFile == null || fotoFile.contentType() == null || !fotoFile.contentType().startsWith("image")) {
            ctx.status(400).result("Todos os campos de livro, incluindo a imagem, são obrigatórios e devem ser válidos.");
            return;
        }

        try {
            double precoCreditos = Double.parseDouble(precoCreditosStr);

            if (precoCreditos <= 0) {
                ctx.status(400).result("O preço em créditos deve ser maior que zero.");
                return;
            }
            
            String fotoUrl = imgbbService.uploadImage(fotoFile);
            
            Livro novoLivro = new Livro(user.getId(), titulo, autor, estadoCondicao, precoCreditos, fotoUrl);
            
            livroRepository.save(novoLivro);

            ctx.redirect("/livro/" + novoLivro.getId() + "?msg=livro_cadastrado");

        } catch (NumberFormatException e) {
            ctx.status(400).result("Preço em créditos inválido.");
        } catch (IOException e) {
            ctx.status(500).result("Erro ao fazer upload da imagem.");
        }
    }

    public void verLivro(Context ctx) {
        User user = ctx.sessionAttribute("user");
        
        if (user == null) {
            ctx.redirect("/login");
            return;
        }
        
        try {
            int livroId = Integer.parseInt(ctx.pathParam("id"));
            
            Livro livro = livroRepository.findById(livroId);
            
            if (livro == null) {
                ctx.status(404).result("Livro não encontrado.");
                return;
            }

            User dono = userRepository.findById(livro.getIdUsuario());

            Map<String, Object> model = new HashMap<>();
            model.put("livro", livro);
            model.put("dono", dono);
            model.put("userLogado", user);
            model.put("isDono", livro.getIdUsuario() == user.getId());
            model.put("creditosUsuario", new CreditoController().getSaldo(user.getId()));
            
            ctx.render("livro.ftl", model);

        } catch (NumberFormatException e) {
            ctx.status(400).result("ID do livro inválido.");
        }
    }

    public void editarLivro(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.status(401).result("Acesso não autorizado. Faça login novamente");
            return;
        }

        try {
            int livroId = Integer.parseInt(ctx.pathParam("id"));
            
            Livro livro = livroRepository.findById(livroId);

            if (livro == null) {
                ctx.status(404).result("Livro não encontrado.");
                return;
            }

            if (livro.getIdUsuario() != user.getId()) {
                ctx.status(403).result("Você não tem permissão para editar este livro.");
                return;
            }
            
            String titulo = ctx.formParam("titulo");
            String autor = ctx.formParam("autor"); 
            String estadoCondicao = ctx.formParam("estadoCondicao"); 
            String precoCreditosStr = ctx.formParam("precoCreditos"); 
            UploadedFile fotoFile = ctx.uploadedFile("fotoCapa"); 

            if (titulo == null || titulo.isEmpty() || autor == null || autor.isEmpty() || estadoCondicao == null || estadoCondicao.isEmpty() || precoCreditosStr == null || precoCreditosStr.isEmpty()) {
                ctx.status(400).result("Todos os campos de texto do livro são obrigatórios.");
                return;
            }
            
            double precoCreditos = Double.parseDouble(precoCreditosStr);

            if (precoCreditos <= 0) {
                ctx.status(400).result("O preço em créditos deve ser maior que zero.");
                return;
            }
            
            livro.setTitulo(titulo);
            livro.setAutor(autor);
            livro.setCondicaoEstado(estadoCondicao);
            livro.setPrecoCreditos(precoCreditos);

            if (fotoFile != null && fotoFile.contentType() != null && fotoFile.contentType().startsWith("image")) {
                try {
                    String novaFotoUrl = imgbbService.uploadImage(fotoFile);
                    livro.setFotoCapa(novaFotoUrl);
                } catch (IOException e) {
                    ctx.status(500).result("Erro ao fazer upload da nova imagem. Livro não atualizado.");
                    return;
                }
            }
            
            livroRepository.update(livro);

            ctx.redirect("/livro/" + livroId + "?msg=livro_atualizado");

        } catch (NumberFormatException e) {
            ctx.status(400).result("ID do livro inválido.");
        }
    }

    public void deletarLivro(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.status(401).result("Acesso não autorizado. Faça login novamente");
            return;
        }

        try {
            int livroId = Integer.parseInt(ctx.pathParam("id"));
            
            Livro livro = livroRepository.findById(livroId);

            if (livro == null) {
                ctx.status(404).result("Livro não encontrado.");
                return;
            }

            if (livro.getIdUsuario() != user.getId()) {
                ctx.status(403).result("Você não tem permissão para deletar este livro.");
                return;
            }
            
            if (livro.getStatusLivro() != LivroStatus.DISPONIVEL) {
                ctx.status(403).result("Não é possível deletar o livro. Ele está envolvido em uma transação (Status: " + livro.getStatusLivro().name() + ").");
                return;
            }
            
            livroRepository.delete(livro.getId());

            ctx.redirect("/perfil?tab=livros&msg=livro_deletado");

        } catch (NumberFormatException e) {
            ctx.status(400).result("ID do livro inválido.");
        }
    }
    
    public void listarTodosLivros(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null){
            ctx.redirect("/login");
            return;
        }
        
        List<Livro> livrosDisponiveis = livroRepository.findAllAvailable();
        
        Map<String, Object> model = new HashMap<>();
        model.put("livrosDisponiveis", livrosDisponiveis);
        model.put("userLogado", user);
        
        ctx.render("index.ftl", model);
    }
}