package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class InstanceExample extends AbstractVerticle
{
    public void start()
    {
        System.out.println("Verticle instance started with ID : "+this.hashCode());
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        DeploymentOptions options = new DeploymentOptions().setInstances(4);

        //If your start() method is synchronous, you don't need to use a promise; Vert.x will know the verticle has started once the start() method exits.

        vertx.deployVerticle("org.example.InstanceExample",options ,res ->{
           if(res.succeeded())
           {
               System.out.println("Verticle deployed with ID : "+res.result());
           }
           else
           {
               System.out.println(res.cause().getMessage());
           }
        });
    }
}
