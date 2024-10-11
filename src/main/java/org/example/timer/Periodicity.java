package org.example.timer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class Periodicity extends AbstractVerticle
{
    private int count = 0;

    public void start() {
        //The ID allows you to manage the timer after it's created.
        long timerId = vertx.setPeriodic(1000, id -> {
            System.out.println("Timer is fired, id: " + id);
            count++;
            if (count == 5) {
                try
                {
                    Thread.sleep(100000);
                } catch (InterruptedException e)
                {
                    System.out.println(e.getMessage());
                }
                System.out.println("Canceling the timer after 5 executions.");
                vertx.cancelTimer(id);  // Cancel the timer after 5 iterations
            }
        });
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Periodicity());
    }
}
