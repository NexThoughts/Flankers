import com.utils.Enums
import io.vertx.core.Vertx
import com.verticle.EmailVerticle

class Main {
    static void main(String[] args) {

        EmailVerticle emailVerticle = new EmailVerticle()
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new EmailVerticle())
        Thread.sleep(300)
        Map mailMap = ["to": "puneetmungali93@gmail.com", "subject": Enums.EmailCategory.REGISTRATION.toString()]
        vertx.eventBus().send("emailAddress", mailMap)
    }
}