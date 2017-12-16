import com.verticle.SecurityVerticle
import io.vertx.core.Vertx

class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new SecurityVerticle())
    }
}