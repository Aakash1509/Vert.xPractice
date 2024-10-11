package org.example.timer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class Timer extends AbstractVerticle
{
    public void start()
    {
        System.out.println("Timer started");

        long timerID = vertx.setPeriodic(1000,id->{
            System.out.println("Timer fired after 1 second");
        });

        vertx.setTimer(5000,id->{
            boolean result = vertx.cancelTimer(timerID);
            if(result)
            {
                System.out.println("Timer cancelled successfully after 5 seconds");
            }
            else
            {
                System.out.println("Failed to cancel timer");
            }
        });

        System.out.println("Timer set , doing other work");
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Timer());
    }
}
