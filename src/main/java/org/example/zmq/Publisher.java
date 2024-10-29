package org.example.zmq;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.zeromq.ZMQ;

public class Publisher extends AbstractVerticle
{
    public void start()
    {
        vertx.executeBlocking(promise->{
            try (ZMQ.Context context = ZMQ.context(1))
            {
                ZMQ.Socket publisher = context.socket(ZMQ.PUB);
                publisher.setSndHWM(10);
                publisher.bind("tcp://*:5556");

                int count = 0;
                while(!Thread.currentThread().isInterrupted())
                {
                    byte[] arr = {1,2,3,4};
                    String message = "Hello from Vert.x "+count++;
                    System.out.println("Publishing: "+message);
//                    publisher.send(message.getBytes(ZMQ.CHARSET));
                    publisher.send(arr);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                promise.complete();
                publisher.close();
            }
        });

    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Publisher());
    }
}