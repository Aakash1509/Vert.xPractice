package org.example.tcp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

public class TcpClient extends AbstractVerticle
{
    public void start()
    {
        //Creating TCP Client
        NetClient client = vertx.createNetClient();

        //To make a connection to a server you use
        //connect
        //, specifying the port and host of the server and a handler that will be called with a result containing the
        //NetSocket
        // when connection is successful or with a failure if connection failed.
        client.connect(9999,"localhost").onComplete(res->{
           if(res.succeeded())
           {
               System.out.println("Connected !");
               NetSocket socket = res.result();

               socket.handler(buffer -> {
                   System.out.println("Received from server : "+buffer.toString());
               });

               String message = "Hello ! Server from Client";
               socket.write(message);
               System.out.println("Sent to server : "+message);

               socket.closeHandler(v->{
                   System.out.println("Connection closed in client");
               });
           }
           else
           {
               System.out.println("Failed to connect : "+res.cause().getMessage());
           }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new TcpClient(),res->{
           if(res.succeeded())
           {
               System.out.println("Client deployed successfully");
           }
           else
           {
               System.out.println("Error in deploying client : "+res.cause().getMessage());
           }
        });
    }
}
