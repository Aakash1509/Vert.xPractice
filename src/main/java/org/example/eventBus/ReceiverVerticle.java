package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.dns.SrvRecord;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class ReceiverVerticle extends AbstractVerticle
{
    /*
    public void start()
    {
        vertx.eventBus().localConsumer("message.address",message -> {
            System.out.println("Received message : "+message.body());
            message.reply("I have received the message");
        });
    }
     */
    public void start()
    {
        EventBus eventBus = vertx.eventBus();

        MessageConsumer<String> consumer = eventBus.localConsumer("message.address");
        /*
        eventBus.consumer("message.address",message -> {
            System.out.println("Received message : "+message.body());

            if(message.body().equals("stop"))
            {
                System.out.println("Receiver going down...");

                vertx.close();
            }
        });
         */
        consumer.handler(message->{
            System.out.println("I have received the message : "+message.body());
        }).completionHandler(res->{
            if(res.succeeded())
            {
                System.out.println("Handler registration is reached");
            }
            else
            {
                System.out.println("Registration failed");
            }
        });
//
//        consumer.unregister().onComplete(res->{
//            if(res.succeeded())
//            {
//                System.out.println("Handler unregistration");
//            }
//            else
//            {
//                System.out.println("Unregistration failed");
//            }
//        });
    }
}
