package com.verticle

import com.Config
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler

class CommentVerticle extends AbstractVerticle {

    MongoClient mongoClient = null

    void start() {
        mongoClient = MongoClient.createShared(vertx, new JsonObject().put("db_name", Config.dbName))

        Router router = Router.router(vertx)

        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("X-PINGARUNER")
                .allowedHeader("Content-Type"))


        vertx.createHttpServer().requestHandler(router.&accept).listen(8085)
    }

    void createRouteForUser(Router router) {
        router.get("/users").handler(this.&fetchAllUsers)
        router.route("/users*").handler(BodyHandler.create())
        router.post("/users").handler(this.&addUser)
        router.get("/users/count").handler(this.&fetchUsersCount)
        router.get("/users/:id").handler(this.&fetchSingleUser)
        router.put("/users/:id").handler(this.&updateUser)
        router.delete("/users/:id").handler(this.&deleteUser)
    }

    void deleteUser(RoutingContext routingContext) {

    }

    void addUser(RoutingContext routingContext) {

    }

    void fetchUsersCount(RoutingContext routingContext) {

    }

    void fetchAllUsers(RoutingContext routingContext) {

    }

    void updateUser(RoutingContext routingContext) {

    }

    void fetchSingleUser(RoutingContext routingContext) {

    }

    void stop() {

    }
}