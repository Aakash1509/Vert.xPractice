package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class ClusteredSender extends AbstractVerticle
{
    public void start()
    {
        vertx.eventBus().send("clustered.message.address","Hello from node1!");
        System.out.println("Message sent from Node1");
    }
    public static void main(String[] args)
    {
        Vertx.clusteredVertx(new VertxOptions(), res->{
            if(res.succeeded())
            {
                Vertx vertx = res.result();
                vertx.deployVerticle(new ClusteredSender());
            }
            else
            {
                System.out.println("Failed to form cluster : "+res.cause().getMessage());
            }
        });
    }
}
