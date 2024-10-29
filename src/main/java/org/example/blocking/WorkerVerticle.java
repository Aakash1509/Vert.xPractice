package org.example.blocking;

import io.vertx.core.*;

import java.util.concurrent.TimeUnit;

public class WorkerVerticle extends AbstractVerticle
{
    public void start(Promise<Void> startPromise)
    {
        System.out.println(Thread.currentThread().getName());
        vertx.executeBlocking(promise->{
                try{
                    System.out.println("Starting a long-running task");
                    Thread.sleep(61000);
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
        VertxOptions options = new VertxOptions().setMaxWorkerExecuteTime(20).setMaxWorkerExecuteTimeUnit(TimeUnit.SECONDS);
//        DeploymentOptions options1 = new DeploymentOptions().setThreadingModel(ThreadingModel.WORKER);
        Vertx vertx = Vertx.vertx(options);
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
