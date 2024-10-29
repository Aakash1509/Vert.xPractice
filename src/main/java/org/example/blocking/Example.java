package org.example.blocking;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class Example extends AbstractVerticle
{
    public void start()
    {
        vertx.executeBlocking(promise -> {
                try
                {
                    System.out.println(Thread.currentThread().getName());

                    Thread.sleep(2000);

                    promise.fail("Task completed");
                }
                catch (Exception e)
                {
                    promise.fail(e);
                }
            },false,res->{
                if(res.succeeded())
                {
                    System.out.println(res.result() + " on thread : "+Thread.currentThread().getName());
                }
                else
                {
                    System.out.println("Task failed on : "+Thread.currentThread().getName());
                }
            });
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Example());
    }
}
