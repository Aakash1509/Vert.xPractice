package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class Redirect extends AbstractVerticle
{
    public void start()
    {
        Router router = Router.router(vertx);

        router.get("/secure").handler(ctx->{
           ctx.redirect("https://secursite.com/");
        });

        router.get("/back").handler(ctx->{
           ctx.redirect("back");
        });

        router.get("/").handler(ctx->{
            ctx.response().end("Welcome to home page.");
        });

        vertx.createHttpServer().requestHandler(router)
                .listen(8080,http->{
                   if(http.succeeded())
                   {
                       System.out.println("Server started on port 8080");
                   }
                   else
                   {
                       System.out.println("Failed to start server");
                   }
                });
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Redirect());
    }
}
