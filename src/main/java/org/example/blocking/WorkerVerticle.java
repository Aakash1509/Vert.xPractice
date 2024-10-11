package org.example.blocking;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class WorkerVerticle extends AbstractVerticle
{
    public void start(Promise<Void> startPromise)
    {
        vertx.executeBlocking(promise->{
                try{
                    System.out.println("Starting a long-running task");
                    Thread.sleep(5000);
                    promise.complete("Task completed successfully");
                }
                catch (Exception e)
                {
                    promise.fail("Task interrupted");
                }
        },res->{
            if(res.succeeded())
            {
                System.out.println(res.result());
            }
            else
            {
                System.out.println("Failed to complete task : "+res.cause().getMessage());
            }
        });
        startPromise.complete();
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new WorkerVerticle(),res->{
           if(res.succeeded())
           {
               System.out.println("Worker successfully deployed");
           }
           else
           {
               System.out.println("Error in deploying verticle");
           }
        });
    }
}
