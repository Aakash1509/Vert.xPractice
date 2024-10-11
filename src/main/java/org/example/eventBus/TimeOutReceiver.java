package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class TimeOutReceiver extends AbstractVerticle
{
    public void start()
    {
        EventBus eb = vertx.eventBus();

        eb.consumer("message.address",message->{
            System.out.println("I received from server : "+message.body());

            vertx.setTimer(31000,id->{
                message.reply("Reply from server");
            });
        });
    }
}
