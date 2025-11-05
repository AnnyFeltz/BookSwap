package com.bookswap.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bookswap.models.Credito;
import com.bookswap.models.User;
import com.bookswap.models.Livro;
import com.bookswap.repository.CreditoRepository;
import com.bookswap.repository.UserRepository;
import com.bookswap.repository.LivroRepository;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.mindrot.jbcrypt.BCrypt; 

public class UserController {

    private final UserRepository userRepository;
    private final ImgbbService imgbbService;
    private final CreditoRepository creditoRepository;
    private final LivroRepository livroRepository;

    public UserController() {
        this.userRepository = new UserRepository();
        this.imgbbService = new ImgbbService(); 
        this.creditoRepository = new CreditoRepository();
        this.livroRepository = new LivroRepository();
    }

    public void verPerfil(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.redirect("/login");
            return;
        }

        User userAtualizado = userRepository.findById(user.getId()); 
        if (userAtualizado == null) {
            ctx.sessionAttribute("user", null);
            ctx.redirect("/login?msg=sessao_expirada");
            return;
        }
        ctx.sessionAttribute("user", userAtualizado);
        
        Credito credito = creditoRepository.findByIdUsuario(userAtualizado.getId());

        double saldo = 0.0;
        if (credito != null){
            saldo = credito.getSaldoAtual();
        }

        List<Livro> livrosDoUsuario = livroRepository.findAvailableByUserId(userAtualizado.getId());

        Map<String, Object> model = new HashMap<>();
        model.put("user", userAtualizado);
        model.put("saldo_usuario", saldo);
        model.put("livrosPraTroca", livrosDoUsuario);
        
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
            if (userRepository.findByEmail(novoEmail) != null) {
                ctx.status(409).result("O novo e-mail já está sendo usado por outro usuário.");
                return;
            }
            user.setEmail(novoEmail);
        }

        userRepository.update(user);
        ctx.sessionAttribute("user", user); 

        ctx.redirect("/perfil?msg=conta_atualizada");
    }
    
    public void atualizarSenha(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.status(401).result("Acesso não autorizado.");
            return;
        }

        String senhaAtual = ctx.formParam("senha_atual");
        String novaSenha = ctx.formParam("nova_senha");

        if (senhaAtual == null || senhaAtual.isEmpty() || novaSenha == null || novaSenha.isEmpty()) {
            ctx.status(400).result("Preencha todos os campos de senha.");
            return;
        }

        if (!BCrypt.checkpw(senhaAtual, user.getSenha())) {
            ctx.status(403).result("A senha atual fornecida está incorreta.");
            return;
        }
        
        String hashedNovaSenha = BCrypt.hashpw(novaSenha, BCrypt.gensalt());
        
        userRepository.updateSenha(user.getId(), hashedNovaSenha);
        
        user.setSenha(hashedNovaSenha); 
        ctx.sessionAttribute("user", user);

        ctx.redirect("/perfil?msg=senha_atualizada");
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