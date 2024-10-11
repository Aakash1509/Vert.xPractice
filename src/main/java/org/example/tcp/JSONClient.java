package org.example.tcp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

public class JSONClient extends AbstractVerticle
{
    public void start()
    {
        NetClient client = vertx.createNetClient();

        client.connect(9999,"localhost").onComplete(res->{
           if(res.succeeded())
           {
               System.out.println("Connected");

               //Creating json object to send
               JsonObject json = new JsonObject().put("name","vert.x").put("age",21);

               res.result().write(json.encode());

               //Handle server response
               res.result().handler(buffer->{
                   System.out.println("Received response : "+buffer.toString());
               });
           }
           else
           {
               System.out.println("Failed to connect : "+res.cause().getMessage());
           }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new JSONClient());
    }
}
