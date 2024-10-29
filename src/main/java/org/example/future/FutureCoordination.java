package org.example.future;

import io.vertx.core.*;

import java.util.Arrays;

public class FutureCoordination extends AbstractVerticle
{
    public void start()
    {
        Future<Integer> future1 = getNumber1();
        Future<Integer> future2 = getNumber2();
        Future<Integer> future3 = getNumber2();
        Future<Integer> future4 = getNumber2();
        Future<Integer> future5 = getNumber2();
        Future<Integer> future6 = getNumber2();
        Future<Integer> future7 = getNumber2();
        Future<Integer> future8 = getNumber2();
        Future<Integer> future9 = getNumber2();

        /*Normal future

        future1.onComplete(ar->{
            if(ar.succeeded())
            {
                System.out.println(ar.result());
            }
        });

        future2.onComplete(ar->{
            if(ar.succeeded())
            {
                System.out.println(ar.result());
            }
            vertx.close();
        });

         */

        //FUTURE ALL
        Future.all(future1,future2).onComplete(ar->{
           if(ar.succeeded())
           {
               System.out.println("All future "+ar.result());
           }
           else
           {
               System.out.println(ar.cause().getMessage());
           }
        });

        /*  FUTURE ANY
        Future.any(future1,future2).onComplete(ar->{
            if(ar.succeeded())
            {
                System.out.println(ar.result());
            }
            else
            {
                System.out.println(ar.cause().getMessage());
            }
        });

         */

        //FUTURE JOIN

        Future.join(Arrays.asList(future1,future2,future3,future4,future5,future6,future7,future8,future9)).onComplete(ar->{
            if(ar.succeeded())
            {
                System.out.println("Join future "+ar.result());
            }
            else
            {
                System.out.println(ar.cause().getMessage());
            }
        });
    }
    private Future<Integer> getNumber1()
    {
        Promise<Integer> promise = Promise.promise();
        vertx.setTimer(1000,id->{
            promise.complete(8);
        });
        return promise.future();
    }
    private Future<Integer> getNumber2()
    {
        Promise<Integer> promise = Promise.promise();
        vertx.setTimer(2000,id->{
            promise.complete(5);
        });
        return promise.future();
    }


    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        DeploymentOptions options = new DeploymentOptions();

        vertx.deployVerticle(FutureCoordination.class.getName(),new DeploymentOptions().setInstances(4));
    }
}
