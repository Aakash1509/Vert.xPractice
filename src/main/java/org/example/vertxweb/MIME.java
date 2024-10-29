package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MIME extends AbstractVerticle
{
    public void start()
    {
        Router router = Router.router(vertx);

        // Use the BodyHandler to make sure the request body is fully read
        router.route().handler(BodyHandler.create());

        router.route()
                .consumes("text/html")
                .handler(ctx->{
                    String body = ctx.body().asString();
                   ctx.response()
                           .putHeader("content-type","text/plain")
                           .end("Received HTML contentss: "+body);
                });

        vertx.createHttpServer().requestHandler(router)
                .listen(8080,res->{
                   if(res.succeeded())
                   {
                       System.out.println("Server started on port 8080");
                   }
                   else
                   {
                       System.out.println("Failed to start a server: "+res.cause());
                   }
                });
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MIME());
    }
}
