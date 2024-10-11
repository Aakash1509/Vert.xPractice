package org.example.udp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.datagram.DatagramSocket;

public class MulticastUDP extends AbstractVerticle
{
    public void start()
    {
        DatagramSocket receiveSocket = vertx.createDatagramSocket(new DatagramSocketOptions());

        receiveSocket.handler(packet->{
            System.out.println("Received packet : "+packet.data());
            System.out.println("From : "+packet.sender());
        });

        receiveSocket.listen(9999,"127.0.0.1").compose(v->receiveSocket.listenMulticastGroup("230.0.0.1")).onComplete(res->{
            if(res.succeeded())
            {
                System.out.println("Receiver is listening for multicast");
            }
            else
            {
                System.out.println("Failed to listen: "+res.cause());
            }
        });


        //Creating DatagramSocket for sending multicast packets
        DatagramSocket sendSocket = vertx.createDatagramSocket(new DatagramSocketOptions());

        Buffer buffer = Buffer.buffer("Hello Multicast group!");

        sendSocket.send(buffer,9999,"230.0.0.1").onComplete(res->{
            if(res.succeeded())
            {
                System.out.println("Multicast packet send successfully");
            }
            else
            {
                System.out.println("Failed to send multicast packet : "+res.cause().getMessage());
            }
        });
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MulticastUDP());
    }
}
