package org.example.tcp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

public class TcpServer extends AbstractVerticle
{
    public void start()
    {
        //Creating TCP server
        NetServer server = vertx.createNetServer();

        //Getting notified of incoming connections
        server.connectHandler(socket->{   //When a connection is made the handler will be called with an instance of NetSocket
            System.out.println("Client connected.");
            socket.pipeTo(socket);
            /*
            socket.handler(buffer -> {    //To read data from the socket you set the handler on the socket.
                System.out.println("I received some bytes : "+buffer.length());
//                socket.pipeTo(socket);

                socket.write("Echo: "+buffer);
                if(socket.writeQueueFull())
                {
                    socket.pause();
                    socket.drainHandler(done->{
                       socket.resume();
                    });
                }
                socket.close();
                System.out.println("Socket closed after handling client message");
            });

             */
//            socket.pipeTo(socket);
//            socket.close();
            System.out.println("Socket closed after handling client message");
        });

        //Start server listening
        server.listen(9999,"localhost").onComplete(res->{
            if(res.succeeded())
            {
                System.out.println("Server is now listening");
            }
            else
            {
                System.out.println("Failed to bind");
            }
        });

        //Closing a TCP server
        /*
        server.close().onComplete(res->{
            if(res.succeeded())
            {
                System.out.println("Server is closed");
            }
            else
            {
                System.out.println("Close failed");
            }
        });

         */

    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new TcpServer(),res->{
            if(res.succeeded())
            {
                System.out.println("TCP server deployed successfully");
            }
            else
            {
                System.out.println("Server failed to deploy : "+res.cause().getMessage());
            }
        });
    }
}
