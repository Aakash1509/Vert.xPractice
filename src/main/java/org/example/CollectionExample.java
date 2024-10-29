package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.ArrayList;
import java.util.List;

public class CollectionExample extends AbstractVerticle
{
    private static List<Integer> numbers = new ArrayList<>();

//    static {
//        numbers.add(0);
//    }

    public void start()
    {
            //        numbers.add(0);
//      numbers.set(0, numbers.get(0) + 1);

        System.out.println("--------------- " + numbers);
            numbers.add(4);
            numbers.add(5);
//      numbers.set(0,33);

            System.out.println(numbers + Thread.currentThread().getName() + " ");
//      System.out.println( Thread.currentThread().getName());

//      vertx.close();

    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        DeploymentOptions options = new DeploymentOptions().setInstances(10);

        vertx.deployVerticle("org.example.CollectionExample",options,res->{
            if(res.succeeded())
            {
                System.out.println(res.result());
                System.out.println("Verticle deployed successfully");
            }
            else
            {
                System.out.println("Failed to deploy verticle"+res.cause().getMessage());
            }
        });

//        vertx.deployVerticle("org.example.CollectionExample",options,res->{
//            if(res.succeeded())
//            {
//                System.out.println(res.result());
//                System.out.println("Verticle deployed successfully");
//            }
//            else
//            {
//                System.out.println("Failed to deploy verticle");
//            }
//        });


    }
}
