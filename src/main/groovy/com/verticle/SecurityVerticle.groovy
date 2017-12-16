package com.verticle

import com.Config
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.mongo.MongoAuth
import io.vertx.ext.mongo.MongoClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.*
import io.vertx.ext.web.sstore.LocalSessionStore
import io.vertx.ext.web.templ.ThymeleafTemplateEngine

class SecurityVerticle extends AbstractVerticle {

    MongoClient mongoClient = null
    MongoAuth authProvider

    void start() {
        mongoClient = MongoClient.createShared(vertx, new JsonObject().put("db_name", Config.dbName))
        JsonObject config = new JsonObject()
        ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create()

        authProvider = MongoAuth.create(mongoClient, config)

        mongoClient.count("user", new JsonObject().put("username", "user"), { count ->
            if (count.succeeded()) {
                if (count.result() == 0) {
                    List<String> roles = []
                    List<String> permissions = []
                    roles.add("user")
                    permissions.add("user")
                    authProvider.insertUser("user", "P@ssw0rd", roles, permissions, { res -> })
                }
            }
        })

        Router router = Router.router(vertx)
        createCORSSetting(router)

        router.route().handler(CookieHandler.create())
        router.route().handler(BodyHandler.create())
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)))
        router.route().handler(UserSessionHandler.create(authProvider))

        router.route("/private/*").handler(RedirectAuthHandler.create(authProvider, "/login"))
        router.route("/private/*").handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("private"))
        router.route("/loginhandler").handler(this.&loginForm)

        router.route("/logout").handler({ context ->
            context.clearUser()
            context.response().putHeader("location", "/").setStatusCode(302).end()
        })
        router.route().handler(StaticHandler.create())
        router.get("/login").handler({ ctx ->
            engine.render(ctx, "login.html", { res ->
                if (res.succeeded()) {
                    ctx.response().end(res.result());
                } else {
                    ctx.fail(res.cause());
                }
            })
        })

        router.get("/").handler({ context ->
            engine.render(context, "private/index.html", { res ->
                if (res.succeeded()) {
                    context.response().end(res.result());
                } else {
                    context.fail(res.cause());
                }
            })

        })
        vertx.createHttpServer().requestHandler(router.&accept).listen(8085)
    }

    void loginForm(RoutingContext routingContext) {
        println("//////??????????????????????????????????????????////////")
        println("//////??????????????????????????????????????????////////")
        println("//////??????????????????????????????????????????////////")
        println("//////??????????????????????????????????????????////////")
        JsonObject authConfig = new JsonObject()
        String username = routingContext.request().getFormAttribute("username")
        authConfig.put("username", username)
        authConfig.put("password", routingContext.request().getFormAttribute("password"))
        authProvider.authenticate(authConfig, { response ->
            if (response.succeeded()) {
                println("Logged IN")
                routingContext.response().putHeader("location", "/").setStatusCode(302).end();
            } else {
                println("//////////////////////////////////")
                println("//////////////////////////////////")
                println("//////////////////////////////////")
                println("//////////////////////////////////")
                routingContext.response().setStatusCode(202).putHeader("content-type", "text/html; charset=utf-8").end("No user with ${username}/paswword")
            }
        })
    }

    void createCORSSetting(Router router) {
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("X-PINGARUNER")
                .allowedHeader("Content-Type"))
    }

    void stop() {
        mongoClient.close()
    }
}