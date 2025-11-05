package com.bookswap.controllers;

import org.mindrot.jbcrypt.BCrypt;

import com.bookswap.models.Credito;
import com.bookswap.models.User;
import com.bookswap.repository.CreditoRepository;
import com.bookswap.repository.UserRepository;

import io.javalin.http.Context;
public class AuthenticationController { 
    private final UserRepository userRepository;
    private final CreditoRepository creditoRepository;

    public AuthenticationController () {
        this.userRepository = new UserRepository();
        this.creditoRepository = new CreditoRepository();
    }

    public void register(Context ctx) {
        String nome = ctx.formParam("nome");
        String email = ctx.formParam("email");
        String senha = ctx.formParam("senha");

        if (nome == null || nome.isEmpty() || email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            ctx.status(400).result("Todos os campos são obrigatórios.");
            return;
        }

        if (userRepository.findByEmail(email) != null) {
            ctx.status(409).result("E-mail já cadastrado.");
            return;
        }

        String hashedSenha = BCrypt.hashpw(senha, BCrypt.gensalt());

        User novoUser = new User(nome, email, hashedSenha);
                
        userRepository.save(novoUser);

        Credito novoCredito = new Credito();

        novoCredito.setIdUsuario(novoUser.getId());
        novoCredito.setSaldoAtual(0);

        creditoRepository.save(novoCredito);

        ctx.redirect("/login");
    }

    public void login(Context ctx) {
        String email = ctx.formParam("email");
        String senha = ctx.formParam("senha");

        User user = userRepository.findByEmail(email);

        if (user != null && BCrypt.checkpw(senha, user.getSenha())) {
            ctx.sessionAttribute("user", user);
            ctx.redirect("/perfil");
        } else {
            ctx.status(401).result("E-mail ou senha inválidos.");
        }
    }

    public void logout(Context ctx) {
        ctx.sessionAttribute("user", null);
        ctx.redirect("/login");
    }
}