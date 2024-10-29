package org.example.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class FutureComposition extends AbstractVerticle
{
//  Future composition allows you to chain multiple asynchronous operations together in Vert.x. It enables you to handle dependent tasks that rely on the outcome of previous operations in a clean and structured way.
    public void start()
    {
        /*
        FileSystem fs = vertx.fileSystem();

        Future<Void> future = fs.createFile("foo").compose(v->{return fs.writeFile("foo", Buffer.buffer());}).compose(v-> {
            System.out.println("I am here");return fs.move("foo","bar");});
        vertx.close();

         */



        /*mapper: A function that takes the result of the current Future (T) and returns another Future<U>.
        T: The type of the result from the current Future.
            U: The type of the result for the next Future.
            The function passed to compose() takes the result of the current Future and returns another Future (e.g., the result of the second task).*/



        //Using andThen approach

        Future<Integer> future = getNumber().
                compose(number-> doubleNumber(number))
                .onComplete(ar->{
                    if(ar.succeeded()){
                        System.out.println("Final result: "+ar.result());
                    }
                    else {
                        System.out.println("Operation failed : "+ar.cause().getMessage());
                    }
                    vertx.close();
                });

        Future<String> future1 = firstAsyncTask();

        future1.compose(result1->{
            System.out.println("First task result: "+result1);
            // result1 is the result of future1, now we call another async task
            return secondAsyncTask(result1);
        }).onComplete(ar->{
            if(ar.succeeded()){
                System.out.println("Result is : "+ar.result());
            }
            else{
                System.out.println("Error " + ar.cause().getMessage());
            }
        });
    }

    private Future<String> firstAsyncTask()
    {
        Promise<String> promise = Promise.promise();
        vertx.setTimer(1000,id->{
            promise.complete("First task completed"); //ar.result() will print that is mentioned in complete
        });
        return promise.future();
    }

    private Future<String> secondAsyncTask(String previousResult)
    {
        Promise<String> promise = Promise.promise();
        vertx.setTimer(1000,id->{
            promise.complete(previousResult + " and second task completed");
        });
        return promise.future();

    }

    private Future<Integer> getNumber(){
        Promise<Integer> promise = Promise.promise();
        vertx.setTimer(1000,id->{
            promise.complete(5);
        });
        return promise.future();
    }

    private Future<Integer> doubleNumber(Integer number){
        Promise<Integer>promise = Promise.promise();
        vertx.setTimer(2000,id->{
           promise .complete(number*2);
        });
        return promise.future();
    }
//  Future composition allows you to chain multiple asynchronous operations together in Vert.x. It enables you to handle dependent tasks that rely on the outcome of previous operations in a clean and structured way.
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new FutureComposition());
    }
}
