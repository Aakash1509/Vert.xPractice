package org.example.blocking;

import io.vertx.core.*;

public class ExecuteBlocking extends AbstractVerticle
{
    public void start()
    {
//        vertx.executeBlocking(() ->{
//           return Future.succeededFuture();
//        });
//        The worker executor can be shared across multiple verticles. This means that the pool of threads created for this worker executor can be reused by any verticle that uses the same named worker pool.
//        WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-worker-pool");
//        executor.executeBlocking()
        for(int i=0;i<=25;i++)
        {
            final int taskNumber = i;

            vertx.executeBlocking(promise -> {
                try
                {
                    System.out.println("Task "+taskNumber+" started on thread : "+Thread.currentThread().getName());

                    Thread.sleep(2000);

                    promise.complete("Task "+taskNumber+" completed");
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

        System.out.println("Non blocking tasks continue on thread : "+Thread.currentThread().getName());
    }
    public static void main(String[] args)
    {
        VertxOptions options = new VertxOptions().setWorkerPoolSize(10);
        Vertx vertx = Vertx.vertx(options);

//        DeploymentOptions options = new DeploymentOptions().setWorkerPoolSize(10);
        vertx.deployVerticle(new ExecuteBlocking());
    }
}
