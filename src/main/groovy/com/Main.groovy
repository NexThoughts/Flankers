package com

import com.verticle.ApiVerticle
import io.vertx.core.Vertx

class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new ApiVerticle())
        vertx.deployVerticle(new EmailVerticle())
    }
}