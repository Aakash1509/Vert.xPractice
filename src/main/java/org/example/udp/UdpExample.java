package org.example.udp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;

public class UdpExample extends AbstractVerticle
{
    public void start()
    {
        DatagramSocket socket = vertx.createDatagramSocket(new DatagramSocketOptions());

        //Handler for receiving data
        socket.handler(packet->{
            System.out.println("Sender : "+packet.sender());
            System.out.println("Data : "+packet.data());
        });

        //Bind the socket to port to start listening for incoming data
        socket.listen(9999,"localhost").onComplete(res->{
            if(res.succeeded())
            {
                System.out.println("Socket is now listening on port 9999");

                System.out.println("Local address of socket : "+socket.localAddress());

                //Send data after socket is bound
                Buffer buffer = Buffer.buffer("content,hello UDP");

                socket.send(buffer,9999,"localhost").onComplete(result->{
                    if(result.succeeded())
                    {
                        System.out.println("Packet send successfully");
                    }
                    else
                    {
                        System.out.println("Failed to send the packet"+result.cause().getMessage());
                    }
                });
            }
            else
            {
                System.out.println("Failed to bind the socket");
            }
        });

    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new UdpExample());
    }
}
