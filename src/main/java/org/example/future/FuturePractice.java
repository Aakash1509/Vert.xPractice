package org.example.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import io.vertx.core.Future;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;

public class FuturePractice extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        //We create a Future object. The Future represents an asynchronous operation that will complete in the future.

        //We first create a promise object
        Promise<String> promise = Promise.promise();

        //Getting future from promise
        Future<String> future = promise.future();

//      The Promise is for producing the result, and the Future is for consuming it.

        /*You typically use a Promise when you want to create custom asynchronous operations or control how and when the result of an asynchronous task is provided to the Future.
        If you're writing your own asynchronous logic (like an API call, database operation, etc.), and you want to notify Vert.x when it's done, you create a Promise to control the result.*/

        vertx.setTimer(2000,id->{
            promise.complete("Operation completed successfully");
        });

//        Factory method on Future that implicitly creates a Promise.
//        Future<String> future = Future.future(promise->{
//            vertx.setTimer(1000,id->{
//               promise.complete("Operation completed successfully");
//            });
//        });

        //Here we attach a handler to the Future that will be called when it completes (either successfully or with a failure).

//      In Vert.x, the onComplete method is similar to a callback

        future.onComplete(ar->{
            if(ar.succeeded()){
                System.out.println("Result : "+ar.result());
            }
            else{
                System.out.println("Operation failed : "+ar.cause().getMessage());
            }
            vertx.close();
        });
        /*
        When Completion Handlers are Necessary:
        If you need to do something with the result of the Future.
        If you need to handle potential errors.
        If you have subsequent operations that depend on the Future's result.
        */

        /*
        fs.createFile("my_file.txt",res->{
           if(res.succeeded()){
               System.out.println("File created Successfully");
           }
           else{
               System.out.println("Error in creating file : "+res.cause().getMessage());
           }
        });

         */
        Promise<String> promise1 = Promise.promise();

        Future<String> future1 = promise1.future();

        vertx.setTimer(1000,id->{
            promise1.complete("Operation completed of promise1");
        });

        future1.onComplete(xyz->{
           if(xyz.succeeded())
           {
               System.out.println("Result of promise1 : "+xyz.result());
           }
           else
           {
               System.out.println("Operation failed : "+xyz.cause().getMessage());
           }
        });

//      FileSystem is part of Vert.x and provides asynchronous methods for interacting with the filesystem (e.g., reading/writing files, getting file properties).

        FileSystem fs = vertx.fileSystem();

        Future<FileProps> future2 = fs.props("my_file.txt");

        future2.onComplete(ar->{
            if(ar.succeeded()){
                FileProps props = ar.result();
                System.out.println("File size : "+props.size());
            }
            else{
                System.out.println("Failure : "+ar.cause().getMessage());
            }
        });

//        vertx.close();

    }
}
