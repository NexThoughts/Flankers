package com.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.ext.mail.MailClient
import io.vertx.ext.mail.MailConfig
import io.vertx.ext.mail.MailMessage
import io.vertx.ext.mail.MailResult
import io.vertx.ext.mail.StartTLSOptions

class EmailVerticle extends AbstractVerticle {

    @Override
    void start(Future<Void> startFuture) throws Exception {

        vertx.eventBus().consumer("emailAddress", { message ->
            Map emailMap = message.body() as Map
            MailConfig config = new MailConfig()
            config.hostname = "smtp.gmail.com"
            config.port = 587
            config.starttls = StartTLSOptions.REQUIRED
            config.username = "puneet@nexthoughts.com"
            config.password = "S@nd@ta*4231"
            MailClient mailClient = MailClient.createNonShared(vertx, config)

            MailMessage mailMessage = new MailMessage()
            mailMessage.subject = emailMap.get("emailSubject")
            mailMessage.from = "user@nexthoughts.com"
            mailMessage.to = emailMap.get("sendTo")
            mailMessage.html = "this is html text <a href=\"http://vertx.io\">vertx.io</a>"

            mailClient.sendMail(mailMessage, new Handler<AsyncResult<MailResult>>() {
                @Override
                void handle(AsyncResult<MailResult> mailEvent) {
                    if (mailEvent.succeeded()) {
                        System.out.println(mailEvent.result())
                        //save data to mongodb
                        vertx.eventBus().send("mongoAddress", message.body().toString())
                    } else if (mailEvent.failed()) {
                        mailEvent.cause().printStackTrace()
                    }
                }
            })
        })
    }

    @Override
    void stop(Future<Void> stopFuture) throws Exception {
        super.stop(stopFuture)
    }
}
