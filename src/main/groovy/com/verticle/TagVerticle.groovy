package com.verticle

import com.Config
import com.model.Tag
import com.model.User
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler

class TagVerticle extends AbstractVerticle {

    MongoClient mongoClient = null

    void start() {
        mongoClient = MongoClient.createShared(vertx, new JsonObject().put("db_name", Config.dbName))

        Router router = Router.router(vertx)
        println("********Start************")
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("X-PINGARUNER")
                .allowedHeader("Content-Type"))

        println("********Start************")
        createRouteForTag(router)
        vertx.createHttpServer().requestHandler(router.&accept).listen(8085)

    }

    void createRouteForTag(Router router) {
        router.route("/tags*").handler(BodyHandler.create())
        router.get("/tags").handler(this.&fetchAllUsers)
        router.post("/addTag").handler(this.&addTag)
        router.get("/tags/count").handler(this.&fetchUsersCount)
        router.get("/tags/:id").handler(this.&fetchSingleUser)
        router.put("/tags/:id").handler(this.&updateUser)
        router.delete("/tags/:id").handler(this.&deleteTags)
    }

    void deleteTags(RoutingContext routingContext) {
        println("**************IN Tag Delete Method *****************")
        mongoClient.removeOne("tag", new JsonObject().put("uuid", routingContext.request().getParam("id")), { lookup ->
            if (lookup.failed()) {
                routingContext.fail(lookup.cause())
                return
            }

            routingContext.response().setStatusCode(204)
            routingContext.response().end()
        })

    }

    void addTag(RoutingContext routingContext) {
        println("**************IN Tag Add Method *****************")
        Tag tag = Json.decodeValue(routingContext.getBodyAsString(), Tag.class)
        println("tagggg" + tag)
        println("tagggg" + tag)
        println("tagggg" + tag)

        mongoClient.insert("tag", tag.jsonObject(), { handeller ->
            if (handeller.failed()) {
                routingContext.fail(handeller.cause())
            }
            tag.id = handeller.result()
            routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(tag))
        })

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