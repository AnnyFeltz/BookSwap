package com.bookswap.controllers;

import java.util.HashMap;
import java.util.Map;

import com.bookswap.models.User;
import com.bookswap.repository.UserRepository;

import io.javalin.http.Context;

public class UserController {

    //variaveis
    private final UserRepository userRepository;

    public UserController() {
        this.userRepository = new UserRepository();
    }

    //perfil quando logado
    public void verPerfil(Context ctx) {
        User user = ctx.sessionAttribute("user");

        //se não tiver logado vai pro login
        if (user == null) {
            ctx.redirect("/login");
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        
        ctx.render("profile.ftl", model);
    }

    //atualiza o perfil
    public void atualizarPerfil(Context ctx) {
        User user = ctx.sessionAttribute("user");

        //se não tiver logado não acessa 
        if (user == null) {
            ctx.status(401).result("Acesso não autorizado. Faça login novamente");
            return;
        }

        //novos valores
        String novoNome = ctx.formParam("nome");
        String novoEmail = ctx.formParam("email");

        //mudando dos valores antigos pros novos
        if (novoNome != null && !novoNome.isEmpty()) {
            user.setNome(novoNome);
        }

        if (novoEmail != null && !novoEmail.isEmpty() && !novoEmail.equals(user.getEmail())) {
            user.setEmail(novoEmail);
        }

        //salvar no bd
        userRepository.update(user);
        ctx.sessionAttribute("user", user);

        ctx.redirect("/perfil?msg=conta_atualizada");
    }

    //deletar o conta (requer muito cuidado)
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
}
