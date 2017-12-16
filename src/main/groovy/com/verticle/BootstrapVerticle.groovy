package com.verticle

import io.vertx.core.AbstractVerticle
import com.services.BootstrapService
import io.vertx.ext.mongo.MongoClient


class BootstrapVerticle extends AbstractVerticle {

//    MongoClient mongoClient = ApiVerticle.mongoClient


    @Override
    public void start() throws Exception {
        System.out.println("############### Starting Bootstrap ###################");
        System.out.println("############### Starting Bootstrap ###################");

        BootstrapService bootstrapService = new BootstrapService()
        bootstrapService.startBootstrap()

    }

    @Override
    public void stop() throws Exception {
        System.out.println("########## Bootstrap Completed ###########");
        System.out.println("########## Bootstrap Completed ###########");
    }


}
