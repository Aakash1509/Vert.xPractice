package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class ReceiverVerticle2 extends AbstractVerticle
{
    public void start()
    {
        try
        {
            EventBus eventBus = vertx.eventBus();

            eventBus.localConsumer("message.address",message -> {
                System.out.println("Hello");
                System.out.println("Handler 1 received message : "+message.body());
            });

            eventBus.localConsumer("message.address",message -> {
                System.out.println("Handler 2 received message : "+message.body());
            });

            eventBus.localConsumer("message.address",message -> {
                System.out.println("Handler 3 received message : "+message.body());
            });
        } catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
