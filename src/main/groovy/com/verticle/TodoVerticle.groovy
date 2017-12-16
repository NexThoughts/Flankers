package com.verticle

import com.model.Todo
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler

class TodoVerticle extends AbstractVerticle {
    MongoClient mongoClient = null
    Router router = null

    void start() {
        mongoClient = ApiVerticle.mongoClient
        router = ApiVerticle.router
        createRouterTodo(router)
    }

    void createRouterTodo(Router router) {
        router.route("/todo*").handler(BodyHandler.create())
        router.get("/todo").handler(this.&fetchAllTodo)
        router.post("/todo").handler(this.&addTodo)
        router.get("/todo/count").handler(this.&fetchTodoCount)
        router.get("/todo/:id").handler(this.&fetchSingleTodo)
        router.put("/todo/:id").handler(this.&updateTodo)
        router.delete("/todo/:id").handler(this.&deleteTodo)
    }

    void deleteTodo(RoutingContext routingContext) {

    }

    void addTodo(RoutingContext routingContext) {
        println("...................................................")
        Todo todo = Json.decodeValue(routingContext.getBodyAsString(), Todo.class)
        mongoClient.insert("todos", todo.jsonObject(), { res ->
            if (res.failed()) {
                routingContext.fail(res.cause())
            }

            todo.id = res.result()
            routingContext.response()
                    .setStatusCode(201)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(todo))
        })

    }

    void fetchTodoCount(RoutingContext routingContext) {
        mongoClient.count("todos", new JsonObject(), { res ->
            if (res.succeeded()) {
                long num = res.result()
                routingContext
                        .response()
                        .setStatusCode(201)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encode(num))
            } else {
                routingContext.fail(res.cause())
            }
        })
    }

    void fetchAllTodo(RoutingContext routingContext) {
        println("*****************************************")
        println("*****************************************")
        println("*****************************************")
        println("*****************************************")
        mongoClient.find("todos", new JsonObject(), { lookup ->
            if (lookup.failed()) {
                routingContext.fail(lookup.cause())
                return
            }

            List<JsonObject> jsonObjectList = lookup.result()
            List<Todo> todos = []

            jsonObjectList.each {
                todos.add(new Todo(it))
            }

            println("todos ${todos}")
            routingContext.response()
                    .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                    .end(Json.encodePrettily(todos))
        })
    }

    void updateTodo(RoutingContext routingContext) {

    }

    void fetchSingleTodo(RoutingContext routingContext) {
        
    }

    void stop() {

    }

}
