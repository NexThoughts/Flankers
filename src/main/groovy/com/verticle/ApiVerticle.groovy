package com.verticle

import com.Config
import com.model.Notification
import com.model.User
import com.utils.Enums
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpHeaders
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler

class ApiVerticle extends AbstractVerticle {

    static MongoClient mongoClient = null
    static Router router = null

    @Override
    public void start() throws Exception {
        System.out.println("############### Starting Mongo Verticle ###################")
        mongoClient = MongoClient.createShared(vertx, new JsonObject().put("db_name", Config.dbName))

        router = Router.router(vertx)
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("X-PINGARUNER")
                .allowedHeader("Content-Type"))

        vertx.deployVerticle(new TodoVerticle())
        vertx.deployVerticle(new CommentVerticle())
        vertx.deployVerticle(new TagVerticle())

        router.get("/users").handler(this.&fetchAllUsers)
        router.route("/users*").handler(BodyHandler.create())
        router.post("/users").handler(this.&addUser)
        router.post("/deleteUser").handler(this.&deleteUser)
        router.get("/users/count").handler(this.&fetchUsersCount)
        router.get("/users/:id").handler(this.&fetchSingleUser)
        router.put("/users/:id").handler(this.&updateUser)
        router.delete("/users/:id").handler(this.&deleteUser)
        vertx.createHttpServer().requestHandler(router.&accept).listen(8085)
    }

    void updateUser(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id")
        JsonObject json = routingContext.getBodyAsJson()
        if (id == null || json == null) {
            routingContext.response().setStatusCode(400).end()
        } else {
            mongoClient.update("users", new JsonObject().put("_id", id), new JsonObject().put("$set", json), { v ->
                if (v.failed()) {
                    routingContext.response().setStatusCode(404).end()
                } else {
//                    User user = new User(id, json.getString("email"), json.getString("password"), json.getString("firstname"), json.getString("lastname"), json.getLong("age"), json.getString("location"), json.getString("gender"))
                    User user = null
                    routingContext.response()
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .end(Json.encodePrettily(user))
                }
            })
        }
    }

    Boolean saveInCollection(String collectionName, JsonObject jsonObject, RoutingContext routingContext) {
        boolean success = true
        mongoClient.insert(collectionName, jsonObject, { lookup ->
            if (lookup.failed()) {
                routingContext.fail(lookup.cause())
                success = false
            }
        })
        success
    }

    void addUser(RoutingContext routingContext) {
        System.out.println("******************** Adding User ********************")

        User user = Json.decodeValue(routingContext.getBodyAsString(), User.class)

        mongoClient.insert("user", user.jsonObject(), { lookup ->
            if (lookup.failed()) {
                routingContext.fail(lookup.cause())
                return
            }

            user.id = lookup.result()

            routingContext.response()
                    .setStatusCode(201)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(user))
        })

        Notification notification = new Notification(type: Enums.NotificationType.EMAIL.toString(),
                event: Enums.NotificationEvent.USER_SIGNUP.toString(),
                message: "",
                emailSubject: "SignUp Email ${user.fullName}",
                sendTo: user.email,
                status: Enums.NotificationStatus.PENDING.toString())

        saveInCollection("notification", notification.jsonObject(), routingContext)

        vertx.eventBus().publish("emailShooter", notification.jsonObject())
    }

    void deleteUser(RoutingContext routingContext) {
        mongoClient.removeOne("user", new JsonObject().put("uuid", routingContext.request().getParam("id")), { lookup ->
            if (lookup.failed()) {
                routingContext.fail(lookup.cause())
                return
            }

            routingContext.response().setStatusCode(204)
            routingContext.response().end()
        })
    }

    void fetchUsersCount(RoutingContext routingContext) {
        mongoClient.count("users", new JsonObject(), { res ->
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

    void fetchSingleUser(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id")
        if (!id) {
            routingContext.response().setStatusCode(400).end()
        } else {
            mongoClient.findOne("users", new JsonObject().put("_id", id), null, { ar ->
                if (ar.succeeded()) {
                    if (ar.result() == null) {
                        routingContext.response().setStatusCode(404).end()
                        return
                    }
                    User user = new User(ar.result())
                    routingContext.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .end(Json.encodePrettily(user))
                } else {
                    routingContext.response().setStatusCode(404).end()
                }
            })
        }
    }

    void fetchAllUsers(RoutingContext routingContext) {
        println("(((((((())))))))))))))))))))))))))))))))))))")
        mongoClient.find("users", new JsonObject(), { lookup ->
            if (lookup.failed()) {
                routingContext.fail(lookup.cause())
                return
            }

            List<JsonObject> jsonObjectList = lookup.result()
            List<User> users = []

            jsonObjectList.each {
                users.add(new User(it))
            }

            println(users)
            routingContext.response()
                    .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                    .end(Json.encodePrettily(users))
        })
    }

    public void stop() throws Exception {
        mongoClient.close()

    }
}