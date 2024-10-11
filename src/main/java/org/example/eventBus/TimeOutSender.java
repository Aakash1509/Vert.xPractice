package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

public class TimeOutSender extends AbstractVerticle
{
    public void start()
    {
        EventBus eb = vertx.eventBus();
        DeliveryOptions options = new DeliveryOptions().setSendTimeout(40000);

        String message = "Hello , I am time out sender";
        eb.request("message.address",message,options,reply->{
            if(reply.succeeded())
            {
                System.out.println("Reply received : "+reply.result().body());
            }
            else
            {
                System.out.println("Timeout occurred : "+reply.cause().getMessage());
            }
        });

        System.out.println("Sent : "+message);
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new TimeOutSender());
    }
}
