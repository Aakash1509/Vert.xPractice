package org.example.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class HTTPServer2 extends AbstractVerticle
{
    public void start()
    {
        HttpServer server = vertx.createHttpServer();

        server.requestHandler(request->{
            request.response().setChunked(true);
            request.response().write("Chunk1\n");
            vertx.setTimer(1000,id->{
                request.response().write("Chunk2\n");
            });
            vertx.setTimer(2000,id->{
               request.response().end("Last chunk\n");
            });
        });

        server.listen(8080,"localhost").onComplete(res->{
           if(res.succeeded())
           {
               System.out.println("Server is now listening");
           }
           else
           {
               System.out.println("Failed to bind");
           }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new HTTPServer2());
    }
}
