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

        vertx.executeBlocking(promise -> {
            try
            {
//                for(int i=0;i<6;i++)
//                {
                    System.out.println("Task" + 1 +" started on thread : "+Thread.currentThread().getName());

                    Thread.sleep(2000);

                    System.out.println("Task "+ 1 +"completed");
//                }

            }
            catch (Exception e)
            {
                promise.fail(e);
            }
        },false,res->{});

        vertx.executeBlocking(promise -> {
            try
            {
//                for(int i=0;i<6;i++)
//                {
                    System.out.println("Task" + 2 +" started on thread : "+Thread.currentThread().getName());

                    Thread.sleep(2000);

                    System.out.println("Task "+ 2 +"completed");
//                }

            }
            catch (Exception e)
            {
                promise.fail(e);
            }
        },false,res->{});

        vertx.executeBlocking(promise -> {
            try
            {
//                for(int i=0;i<6;i++)
//                {
                System.out.println("Task" + 3 +" started on thread : "+Thread.currentThread().getName());

                Thread.sleep(20000);

                System.out.println("Task "+ 3 +"completed");
//                }

            }
            catch (Exception e)
            {
                promise.fail(e);
            }
        },true,res->{});

        vertx.executeBlocking(promise -> {
            try
            {
//                for(int i=0;i<6;i++)
//                {
                System.out.println("Task" + 4 +" started on thread : "+Thread.currentThread().getName());

                Thread.sleep(2000);

                System.out.println("Task "+ 4 +"completed");
//                }

            }
            catch (Exception e)
            {
                promise.fail(e);
            }
        },false,res->{});

        vertx.executeBlocking(promise -> {
            try
            {
//                for(int i=0;i<6;i++)
//                {
                System.out.println("Task" + 5 +" started on thread : "+Thread.currentThread().getName());

                Thread.sleep(2000);

                System.out.println("Task "+ 5 +"completed");
//                }

            }
            catch (Exception e)
            {
                promise.fail(e);
            }
        },true,res->{});

        vertx.executeBlocking(promise -> {
            try
            {
//                for(int i=0;i<6;i++)
//                {
                System.out.println("Task" + 6 +" started on thread : "+Thread.currentThread().getName());

                Thread.sleep(2000);

                System.out.println("Task "+ 6 +"completed");
//                }

            }
            catch (Exception e)
            {
                promise.fail(e);
            }
        },false,res->{});

        vertx.executeBlocking(promise -> {
            try
            {
//                for(int i=0;i<6;i++)
//                {
                System.out.println("Task" + 7 +" started on thread : "+Thread.currentThread().getName());

                Thread.sleep(2000);

                System.out.println("Task "+ 7 +"completed");
//                }

            }
            catch (Exception e)
            {
                promise.fail(e);
            }
        },true,res->{});


//            if(res.succeeded())
//            {
//                System.out.println(res.result() + " on thread : "+Thread.currentThread().getName());
//            }
//            else
//            {
//                System.out.println("Task failed on : "+Thread.currentThread().getName());
//            }
//        });
//        vertx.executeBlocking(promise -> {
//            try
//            {
//                System.out.println("Task 2 started on thread : "+Thread.currentThread().getName());
//
//                Thread.sleep(2000);
//
//                System.out.println("Task 1 completed");
//
//                promise.complete("Task 2 completed");
//            }
//            catch (Exception e)
//            {
//                promise.fail(e);
//            }
//        },false,res->{
//            if(res.succeeded())
//            {
//                System.out.println(res.result() + " on thread : "+Thread.currentThread().getName());
//            }
//            else
//            {
//                System.out.println("Task failed on : "+Thread.currentThread().getName());
//            }
//        });

//        for(int i=0;i<=25;i++)
//        {
//            final int taskNumber = i;
//
//            vertx.executeBlocking(promise -> {
//                try
//                {
//                    System.out.println("Task "+taskNumber+" started on thread : "+Thread.currentThread().getName());
//
//                    Thread.sleep(2000);
//
//                    promise.complete("Task "+taskNumber+" completed");
//                }
//                catch (Exception e)
//                {
//                    promise.fail(e);
//                }
//            },false,res->{
//                if(res.succeeded())
//                {
//                    System.out.println(res.result() + " on thread : "+Thread.currentThread().getName());
//                }
//                else
//                {
//                    System.out.println("Task failed on : "+Thread.currentThread().getName());
//                }
//            });
//        }

//        System.out.println("Non blocking tasks continue on thread : "+Thread.currentThread().getName());
    }
    public static void main(String[] args)
    {
        VertxOptions options = new VertxOptions().setWorkerPoolSize(3);
        Vertx vertx = Vertx.vertx(options);

//        DeploymentOptions options = new DeploymentOptions().setWorkerPoolSize(10);
        vertx.deployVerticle(new ExecuteBlocking());
    }
}
