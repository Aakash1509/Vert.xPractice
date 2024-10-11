package org.example.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

public class FutureComposition2 extends AbstractVerticle
{
    public void start()
    {
        Future<String> future1 = firstAsyncTask();

        /*
        future1.onComplete(ar->{
           if(ar.succeeded())
           {
               System.out.println(ar.result());
           }
           else
           {
               System.out.println(ar.cause().getMessage());
           }
            vertx.close();
        });

         */
        future1.compose(ar->{
            System.out.println(ar);
            System.out.println("First task result : "+ar);
            return secondAsyncTask(ar);
        }).onComplete(xyz ->{
            if(xyz.succeeded())
            {
                System.out.println("Final result is : "+ xyz.result());
            }
            else
            {
                System.out.println(xyz.cause().getMessage());
            }
        });

        FileSystem fs = vertx.fileSystem();

        Future<Void> future = fs.createFile("foo").compose(v->{return fs.writeFile("foo", Buffer.buffer());}).compose(v->{return fs.move("foo","bar");}).onComplete(ar->{
            if(ar.succeeded())
            {
                System.out.println("Completed");
            }
            else
            {
                System.out.println(ar.cause().getMessage());
            }
        });



    }

    private Future<String> firstAsyncTask()
    {
        Promise<String> promise = Promise.promise();

        vertx.setTimer(1000,id->{
            promise.complete("First task completed");
        });

        return promise.future();
    }

    private Future<String> secondAsyncTask(String result)
    {
        Promise<String> promise = Promise.promise();
        vertx.setTimer(1000,id->{
            promise.complete(result+" Second task completed");
        });

        return promise.future();
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new FutureComposition2());


    }
}
