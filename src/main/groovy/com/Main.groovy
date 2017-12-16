package com

import com.verticle.ApiVerticle
import com.verticle.EmailVerticle
import com.verticle.SecurityVerticle
import io.vertx.core.Vertx

class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new ApiVerticle())
        vertx.deployVerticle(new SecurityVerticle())
        vertx.deployVerticle(new EmailVerticle())
    }
}