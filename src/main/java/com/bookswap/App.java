package com.bookswap;

import com.bookswap.controllers.AuthenticationController;
import com.bookswap.controllers.LivroController;
import com.bookswap.controllers.TrocaController;
import com.bookswap.controllers.UserController;

import freemarker.template.Configuration;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinFreemarker;

public class App {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);
        cfg.setClassForTemplateLoading(App.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = "/public";
                staticFiles.location = Location.CLASSPATH;
                staticFiles.precompress = false;
                staticFiles.hostedPath = "/static";
            });

            config.fileRenderer(new JavalinFreemarker(cfg));
        }).start(3030);

        app.before(ctx -> {
            String path = ctx.path();

            if (path.startsWith("/login") || path.startsWith("/register") || path.startsWith("/static")) {
                return;
            }

            if (ctx.sessionAttribute("user") == null) {
                ctx.redirect("/login");
            }
        });

        AuthenticationController authenticationController = new AuthenticationController();
        UserController userController = new UserController();
        LivroController livroController = new LivroController();
        TrocaController trocaController = new TrocaController();
        

        app.get("/register", ctx -> ctx.render("register.ftl"));
        app.get("/login", ctx -> ctx.render("login.ftl"));

        app.post("/register", authenticationController::register);
        app.post("/login", authenticationController::login);
        app.post("/logout", authenticationController::logout);

        app.get("/perfil", userController::verPerfil);
        app.post("/perfil/update", userController::atualizarPerfil);
        app.post("/perfil/delete", userController::deletarConta);
        app.post("/perfil/upload-foto", userController::uploadFotoPerfil);
        
        app.get("/perfil/senha", ctx -> ctx.render("mudarSenha.ftl"));
        app.post("/perfil/senha", userController::atualizarSenha); 

        app.get("/livros", livroController::listarTodosLivros);

        app.get("/", new LivroController()::listarTodosLivros);

        app.get("/livro/cadastrar", ctx -> ctx.render("criar_livro.ftl"));
        app.post("/livro/cadastrar", livroController::cadastrarLivro);
        app.get("/livro/{id}", livroController::verLivro);
        
        app.get("/livro/editar/{id}", ctx -> ctx.render("editarLivro.ftl")); 
        app.post("/livro/editar/{id}", livroController::editarLivro); 
        app.post("/livro/deletar/{id}", livroController::deletarLivro);

        app.post("/troca/iniciar/{idLivro}", trocaController::iniciarTroca);
        
        app.post("/troca/aceitar/{idTroca}", trocaController::aceitarTroca); 
        app.post("/troca/recusar/{idTroca}", trocaController::recusarTroca); 
        app.post("/troca/confirmar/{idTroca}", trocaController::confirmarRecebimento); 
        app.post("/troca/cancelar/{idTroca}", trocaController::cancelarTroca); 
    }
}
