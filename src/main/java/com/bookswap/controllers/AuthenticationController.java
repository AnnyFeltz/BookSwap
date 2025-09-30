package com.bookswap.controllers;

import org.mindrot.jbcrypt.BCrypt;

import com.bookswap.models.Role;
import com.bookswap.models.User;
import com.bookswap.repository.IAuthenticationRepository;
import com.bookswap.repository.UserRepository;

import io.javalin.http.Context;

public class AuthenticationController implements IAuthenticationRepository{
    private final UserRepository userRepository;

    public AuthenticationController () {
        this.userRepository = new UserRepository();
    }

    @Override
    public void register(Context ctx) {
        //variaveis
        String nome = ctx.formParam("nome");
        String email = ctx.formParam("email");
        String senha = ctx.formParam("senha");

        //validação simples
        if (nome == null || nome.isEmpty() || email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            ctx.status(400).result("Todos os campos são obrigatórios.");
            return;
        }

        if (userRepository.findByEmail(email) != null) {
            ctx.status(409).result("E-mail já cadastrado.");
            return;
        }

        //hashing da senha
        String hashedSenha = BCrypt.hashpw(senha, BCrypt.gensalt());

        //criar usuario e salvar no banco
        User newUser = new User(nome, email, hashedSenha, Role.USER);
        userRepository.save(newUser);

        ctx.redirect("/login");
    }

    @Override
    public void login(Context ctx) {
        //variaveis
        String email = ctx.formParam("email");
        String senha = ctx.formParam("senha");

        User user = userRepository.findByEmail(email);

        //verifica a existencia do usuario e a senha 
        if (user != null && BCrypt.checkpw(senha, user.getSenha())) {
            ctx.sessionAttribute("user", user);
            ctx.redirect("/perfil");
        } else {
            ctx.status(401).result("E-mail ou senha inválidos.");
        }
    }

    @Override
    public void logout(Context ctx) {
        ctx.sessionAttribute("user", null);
        ctx.redirect("/login");
    }
}

