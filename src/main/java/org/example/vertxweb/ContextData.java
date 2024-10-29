package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.Map;

public class ContextData extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);

        router.get("/some/path/*").handler(ctx->{
            ctx.put("foo","bar");
            ctx.next();
        });

        router.get("/some/path/other").handler(ctx->{
            ctx.response().setChunked(true);
           String bar = ctx.get("foo");
            System.out.println("here: "+bar);
            Map<String, Object> map = ctx.data();
            boolean isHtml = ctx.is("html");
            System.out.println("Is html ? "+isHtml);
            JsonObject json = new JsonObject(map);
//            ctx.response().write(json.encodePrettily());
            ctx.json(json); //This will end the response

//           ctx.response().end("\nThe value of foo is: "+bar);
        });

        vertx.createHttpServer().requestHandler(router)
                .listen(8080,http->{
                   if(http.succeeded())
                   {
                       System.out.println("HTTP server started on port 8080");
                   }
                   else
                   {
                       System.out.println("Server failed to start");
                   }
                });
    }
}
