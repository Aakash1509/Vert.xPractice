package org.example.tcp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;

public class JSONServer extends AbstractVerticle
{
    public void start()
    {
        NetServer server = vertx.createNetServer();

        server.connectHandler(socket->{
            System.out.println("Client connected");
            socket.handler(buffer->{
                String receivedData = buffer.toString();
                JsonObject json = new JsonObject(receivedData);

                System.out.println("Received JSON: "+json.encodePrettily());

                //Responding back to client
                JsonObject response = new JsonObject().put("status","received");
                socket.write(response.encode());

            });
        }).listen(9999,"localhost",res->{
            if(res.succeeded())
            {
                System.out.println("Server is listening on port 9999");
            }
            else
            {
                System.out.println("Failed to start the server "+res.cause().getMessage());
            }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new JSONServer());
    }
}
