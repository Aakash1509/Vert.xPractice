package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class ClusteredReceiver extends AbstractVerticle
{
    public void start()
    {
        vertx.eventBus().consumer("clustered.message.address",message->{
            System.out.println("Received message on node 2: "+message.body());

//            message.reply("Hello from Node2");
        });
    }
    public static void main(String[] args)
    {
        Vertx.clusteredVertx(new VertxOptions(),res->{
           if(res.succeeded())
           {
               Vertx vertx = res.result();
               vertx.deployVerticle(new ClusteredReceiver());
           }
           else
           {
               System.out.println("Failed to form cluster : "+res.cause().getMessage());
           }
        });
    }
}
