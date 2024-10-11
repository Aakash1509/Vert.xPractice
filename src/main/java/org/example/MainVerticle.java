package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import sun.security.provider.certpath.Vertex;

public class MainVerticle extends AbstractVerticle
{

    public void start() //This method is present in abstract class Abstract Verticle and we can override it
    {
        vertx.createHttpServer().requestHandler(req ->
        {
            req.response().end("Hello from Vert.x");
        }).listen(9999, result ->     //The result is a callback that tells you whether the server was successfully started or if there was an error.
        {
            if (result.succeeded())
            {
                System.out.println("HTTP server started on port 9999");
            } else
            {
                System.out.println("Failed to launch server: " + result.cause());
            }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle()); //The MainVerticle class overrides the start() method where you define the actions to take when the verticle is deployed

        //Deploy verticle biji rite joi leje
    }
}
