package com.bookswap.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bookswap.models.Livro;
import com.bookswap.models.User;
import com.bookswap.repository.LivroRepository;

import io.javalin.http.Context;

public class DashboardController {

    private final LivroRepository livroRepository;

    public DashboardController() {
        this.livroRepository = new LivroRepository();
    }

    public void mostrarDashboard(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.redirect("/login");
            return;
        }

        try {
            List<Livro> livrosDisponiveis = livroRepository.findAllAvailable();

            System.out.println("📚 Livros disponíveis encontrados: " + livrosDisponiveis.size());
            for (Livro livro : livrosDisponiveis) {
                System.out.println("→ " + livro.getTitulo() + " | " + livro.getStatusLivro());
            }

            Map<String, Object> model = new HashMap<>();
            model.put("livrosDisponiveis", livrosDisponiveis);
            model.put("userLogado", user);

            ctx.render("index.ftl", model);

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Erro ao carregar o dashboard 😢");
        }
    }
}
