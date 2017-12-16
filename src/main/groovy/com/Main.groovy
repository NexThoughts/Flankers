package com

import com.verticle.ApiVerticle
import com.verticle.EmailVerticle
import com.verticle.BootstrapVerticle
import io.vertx.core.Vertx

class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new ApiVerticle())
        Thread.sleep(2000)
        vertx.deployVerticle(new BootstrapVerticle())
//        vertx.deployVerticle(new EmailVerticle())
    }
}