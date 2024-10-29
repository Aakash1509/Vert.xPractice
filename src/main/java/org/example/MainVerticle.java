package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import sun.security.provider.certpath.Vertex;

public class MainVerticle extends AbstractVerticle
{

    //Use case of verticle : If I want to scale an independent task or want to set threading model then I can create verticle

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
        VertxOptions options = new VertxOptions();
        DeploymentOptions options1 = new DeploymentOptions();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MainVerticle.class.getName()); //The MainVerticle class overrides the start() method where you define the actions to take when the verticle is deployed

//        vertx.deployVerticle(new MainVerticle());
//        vertx.deployVerticle("org.example.MainVerticle");

        //Deploy verticle biji rite joi leje
    }
}
