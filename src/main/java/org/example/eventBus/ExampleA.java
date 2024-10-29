package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;


//Receiver
public class ExampleA extends AbstractVerticle
{
    public void start()
    {
        try
        {
            EventBus eventBus = vertx.eventBus();

            eventBus.localConsumer("message.address",message -> {
//                System.out.println("Hello");
                //If array is coming I need to typecast it using Arrays.toString((int[])message.body())
                System.out.println("Handler 1 received message : "+message.body() + " " + Thread.currentThread().getName());
            });

            eventBus.localConsumer("message.address",message -> {
                System.out.println("Handler 2 received message : "+message.body() + " " + Thread.currentThread().getName());
            });

        } catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
