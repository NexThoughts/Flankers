package com.verticle

import com.model.Comment
import io.vertx.core.AbstractVerticle
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler

class CommentVerticle extends AbstractVerticle {

    MongoClient mongoClient = null
    Router router = null

    void start() {
        mongoClient = ApiVerticle.mongoClient
        router = ApiVerticle.router
        createRouteForcomment(router)
    }

    void createRouteForcomment(Router router) {
        router.route("/comments*").handler(BodyHandler.create())
        router.get("/comments").handler(this.&fetchAllComments)
        router.post("/comments").handler(this.&addComment)
        router.get("/comments/count").handler(this.&fetchCommentsCount)
        router.get("/comments/:id").handler(this.&fetchSingleComment)
        router.put("/comments/:id").handler(this.&updateComment)
        router.delete("/comments/:id").handler(this.&deleteComment)
    }

    void deleteComment(RoutingContext routingContext) {
        mongoClient.remove("comments", new JsonObject().put("_id", routingContext.request().getParam("id")), { lookup ->
            if (lookup.failed()) {
                routingContext.fail(lookup.cause())
                return
            }

            routingContext.response().setStatusCode(204)
            routingContext.response().end("comment deleted")
        })
    }

    void addComment(RoutingContext routingContext) {
        println("...................................................")
        Comment comment = Json.decodeValue(routingContext.getBodyAsString(), Comment.class)
        mongoClient.insert("comments", comment.jsonObject(), { res ->
            if (res.failed()) {
                routingContext.fail(res.cause())
            }

            comment.id = res.result()
            routingContext.response()
                    .setStatusCode(201)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(comment))
        })

    }

    void fetchCommentsCount(RoutingContext routingContext) {
        mongoClient.count("comments", new JsonObject().put("todo", null), new Handler<AsyncResult<Long>>() {
            @Override
            void handle(AsyncResult<Long> event) {
                println(event.result())
                routingContext.response()
                        .setStatusCode(201)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(event.result()))
            }
        })
    }

    void fetchAllComments(RoutingContext routingContext) {
        mongoClient.find("comments", new JsonObject(), new Handler<AsyncResult<List<JsonObject>>>() {
            @Override
            void handle(AsyncResult<List<JsonObject>> event) {

                List<JsonObject> jsonObjectList = event.result()
                List<Comment> comments = []

                jsonObjectList.each {
                    comments.add(new Comment(it))
                }

                routingContext.response()
                        .setStatusCode(201)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(comments))
            }
        })


    }

    void updateComment(RoutingContext routingContext) {

    }

    void fetchSingleComment(RoutingContext routingContext) {
        mongoClient.find("comments", new JsonObject().put("_id", routingContext.request().getParam("id")), new Handler<AsyncResult<List<JsonObject>>>() {
            @Override
            void handle(AsyncResult<List<JsonObject>> event) {
                routingContext.response()
                        .setStatusCode(201)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(event.result()))
            }
        })
    }

    void stop() {

    }
}