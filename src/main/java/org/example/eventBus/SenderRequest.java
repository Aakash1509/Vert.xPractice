package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

public class SenderRequest extends AbstractVerticle
{
    public void start()
    {
        EventBus eb = vertx.eventBus();

        DeliveryOptions options = new DeliveryOptions().setSendTimeout(5000);

        eb.request("message.address"," S : I am sending message",options)
                .onComplete(ar->{
                    if(ar.succeeded())
                    {
                        System.out.println("S : Received reply is "+ar.result().body());
                    }
                    else
                    {
                        System.out.println("S : Failed to receive a reply within specific time : "+ar.cause().getMessage());
                    }
                });
    }
}
