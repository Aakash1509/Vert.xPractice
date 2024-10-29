package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class Basic extends AbstractVerticle
{
    public void start()
    {
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(ctx->{
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type","text/plain");

            response.end("Hello World from Vertx-Web!");
        });

        server.requestHandler(router).listen(8080,http->{
            if(http.succeeded())
            {
                System.out.println("Server started on port 8080");
            }
            else
            {
                System.out.println("Failed to start the server");
            }
        });
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Basic());
    }
}
