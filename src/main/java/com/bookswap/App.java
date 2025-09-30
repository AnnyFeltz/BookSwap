package com.bookswap;

import com.bookswap.controllers.AuthenticationController;
import com.bookswap.controllers.UserController;

import freemarker.template.Configuration;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinFreemarker;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // configurações
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);
        cfg.setClassForTemplateLoading(App.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");

        // criando e configurando o javalin
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = "/public";
                staticFiles.location = Location.CLASSPATH;
                staticFiles.precompress = false;
            });

            config.fileRenderer(new JavalinFreemarker(cfg));
        }).start(3030);

        app.before(ctx -> {
            String path = ctx.path();
            if (path.startsWith("/login") || path.startsWith("/register") || path.startsWith("/public")) {
                return;
            }

            if (ctx.sessionAttribute("user") == null) {
                ctx.redirect("/login");
            }
        });

        // controllers
        AuthenticationController authenticationController = new AuthenticationController();
        UserController userController = new UserController();

        // formularios de autenticação
        app.get("/register", ctx -> ctx.render("register.ftl"));
        app.get("/login", ctx -> ctx.render("login.ftl"));

        // rotas post de autenticação
        app.post("/register", authenticationController::register);
        app.post("/login", authenticationController::login);
        app.post("/logout", authenticationController::logout);

        // rotas de perfil
        app.get("/perfil", userController::verPerfil);
        app.post("/perfil/update", userController::atualizarPerfil);
        app.post("/perfil/delete", userController::deletarConta);

        // rota "/" protegida
        app.get("/", ctx -> ctx.render("index.ftl"));

    }
}