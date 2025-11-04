package com.bookswap.controllers;

import java.util.HashMap;
import java.util.Map;

import com.bookswap.dao.LivroDao;
import com.bookswap.models.Livro;
import com.bookswap.models.User;
import com.bookswap.repository.LivroRepository;

import io.javalin.http.Context;

public class LivroController {

    private final LivroRepository livroRepository;

    public LivroController() {
        this.livroRepository = new LivroRepository();
    }

    public void verLivro(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.status(401).result("Acesso não autorizado. Faça login novamente");
            return;
        }

        try {
            int livroId = Integer.parseInt(ctx.pathParam("id"));
            LivroDao dao = new LivroDao();
            Livro livro = dao.findById(livroId);

            Map<String, Object> model = new HashMap<>();
            model.put("livro", livro);

            if (livro == null) {
                ctx.status(404).result("Livro não encontrado.");
                return;
            }

            if (livro.getIdUsuario() != user.getId()) {
                ctx.status(403).result("Você não tem permissão para ver este livro.");
                return;
            }

            ctx.render("livro.ftl", model); // acho q nao tem ftl pra livro

        } catch (NumberFormatException e) {
            ctx.status(400).result("ID do livro inválido.");
        }
    }

    public void atualizarLivro(Context ctx) {
        User user = ctx.sessionAttribute("user");

        if (user == null) {
            ctx.status(401).result("Acesso não autorizado. Faça login novamente");
            return;
        }

        try {
            int livroId = Integer.parseInt(ctx.pathParam("id"));
            LivroDao dao = new LivroDao();
            Livro livro = dao.findById(livroId);

            Map<String, Object> model = new HashMap<>();
            model.put("livro", livro);

            if (livro == null) {
                ctx.status(404).result("Livro não encontrado.");
                return;
            }

            if (livro.getIdUsuario() != user.getId()) {
                ctx.status(403).result("Você não tem permissão para editar este livro.");
                return;
            }

            String novoTitulo = ctx.formParam("titulo");
            // String novoEstadoCondicao = ctx.formParam("estadoCondicao"); nao tenho
            // certeza doq colocar, ent vou deixar comentado dai vcs decidem

            if (novoTitulo != null && !novoTitulo.isEmpty()) {
                livro.setTitulo(novoTitulo);
            }

            // if (novoEstadoCondicao != null && !novoEstadoCondicao.isEmpty() &&
            // !novoEstadoCondicao.equals(livro.getEstadoCondicao())) {
            // livro.setEstadoCondicao(novoEstadoCondicao);
            // }

            livroRepository.update(livro);
            ctx.sessionAttribute("livro", livro);

            ctx.redirect("/perfil?msg=livro_atualizado");

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

        int livroId = Integer.parseInt(ctx.pathParam("id"));
        LivroDao dao = new LivroDao();
        Livro livro = dao.findById(livroId);

        if (livro == null) {
            ctx.status(404).result("Livro não encontrado.");
            return;
        }

        if (livro.getIdUsuario() != user.getId()) {
            ctx.status(403).result("Você não tem permissão para deletar este livro.");
            return;
        }

        livroRepository.delete(livro.getId());

        ctx.sessionAttribute("livro", null);

        ctx.redirect("/?msg=livro_deletado");
    }

}
