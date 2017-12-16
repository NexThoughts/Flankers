import com.model.Notification

import com.utils.Enums
import groovy.json.JsonBuilder
import io.vertx.core.Vertx
import com.verticle.EmailVerticle
import io.vertx.core.json.JsonObject

class Main {
    static void main(String[] args) {

        EmailVerticle emailVerticle = new EmailVerticle()
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new EmailVerticle())
        Thread.sleep(300)
        Notification notification = new Notification(type: Enums.NotificationType.EMAIL.toString(), message: "", emailSubject: Enums.EmailCategory.REGISTRATION.toString(), sendTo: "puneetmungali93@gmail.com", status: Enums.NotificationStatus.PENDING.toString())
        JsonObject emailJsonObject = new JsonObject(new JsonBuilder(notification).toPrettyString())
        vertx.eventBus().send("emailAddress", emailJsonObject)
    }
}