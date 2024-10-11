package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class ReceiverReply extends AbstractVerticle
{
    public void start()
    {
        EventBus eventBus = vertx.eventBus();
        MessageConsumer<String> consumer = eventBus.localConsumer("message.address");
        consumer.handler(message->{
            System.out.println("R : Received the message"+message.body());
            vertx.setTimer(2000,id->{
                message.reply("R : Good");
            });
        });
    }
}
