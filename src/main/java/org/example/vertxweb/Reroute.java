package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class Reroute extends AbstractVerticle
{
    public void start()
    {
        Router router = Router.router(vertx);

        router.get("/some/path/").handler(ctx->{
           ctx.put("foo","bar");
           ctx.next();
        });

        router.get("/some/path/B").handler(ctx->{
            //throw new RuntimeException("Something went wrong"); //This will call failureHandler
            ctx.response().end("In path B");
        });

        router.get("/some/path").handler(ctx->{
           ctx.reroute("/some/path/B");
        });

        router.get("/notfound-handler").handler(ctx->{
            ctx.response().setStatusCode(500).end("Not found page");
        });

        router.get().failureHandler(ctx->{
           if(ctx.statusCode()==500)
           {
               ctx.reroute("/notfound-handler");
           }
           else
           {
               ctx.next();
           }
        });

        vertx.createHttpServer().requestHandler(router)
                .listen(8080,http->{
                   if(http.succeeded())
                   {
                       System.out.println("HTTP server started on port 8080");
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
        vertx.deployVerticle(new Reroute());
    }
}
