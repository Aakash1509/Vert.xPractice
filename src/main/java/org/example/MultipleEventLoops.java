package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class MultipleEventLoops extends AbstractVerticle
{
    public void start()
    {
        vertx.setPeriodic(1000,id->{
            System.out.println("Task running on thread : "+Thread.currentThread().getName());
            vertx.cancelTimer(id);
        });
    }

    public void stop()
    {
        System.out.println("My verticle is stopping");
    }
    public static void main(String[] args)
    {
        VertxOptions options = new VertxOptions().setEventLoopPoolSize(4);
        Vertx vertx = Vertx.vertx(options);

        //This loop deploys four instances of the MultipleEventLoops verticle into the already created vertx instance.

        for(int i=0;i<4;i++)
        {
            vertx.deployVerticle(new MultipleEventLoops());
        }

        vertx.setPeriodic(2000,id->{
            System.out.println("Main thread : "+Thread.currentThread().getName());
            vertx.cancelTimer(id);
            vertx.close();
        });

    }
}
