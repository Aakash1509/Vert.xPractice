package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

public class SenderVerticle extends AbstractVerticle
{
    /*
    public void start()
    {
        vertx.eventBus().request("message.address","Hello from Aakash !").onComplete(ar->{
            if(ar.succeeded())
            {
                System.out.println("Receiver's reply :"+ar.result().body());
            }
        });
    }
     */

    public void start()
    {
        EventBus eb = vertx.eventBus();

        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("some-header","some-value");

        vertx.setPeriodic(1000,id->{
            String message = "Hello "+System.currentTimeMillis();
            eb.send("message.address", message,options);
            System.out.println("Sent : "+message);

//            if(System.currentTimeMillis() % 5000 < 1000) //After 5 second I want to send stop message
//            {
//                eb.send("message.address","stop");
//            }
        });
    }


    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SenderVerticle());
    }
}
