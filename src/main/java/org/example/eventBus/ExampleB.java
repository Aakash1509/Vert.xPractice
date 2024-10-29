package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

//Sender
public class ExampleB extends AbstractVerticle
{
    public void start()
    {
        EventBus eb = vertx.eventBus();
//        String message = "Hello";
//        eb.send("message.address",message);

        vertx.setPeriodic(1000,id -> {

            String message1 = "Hello1";
            eb.send("message.address",message1);

        });
//        while(true)
//        }
//        {
    }
}
