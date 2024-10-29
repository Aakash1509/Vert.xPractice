package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.bridge.BridgeOptions;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;

public class SockJSServer extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);

        SockJSHandlerOptions options = new SockJSHandlerOptions().setHeartbeatInterval(2000);
        SockJSHandler sockJSHandler = SockJSHandler.create(vertx,options);

        router.route("/eventbus/*")
                .subRouter(sockJSHandler.socketHandler(sockJSSocket ->{
                    //Just echo the data back
//                    sockJSSocket.handler(sockJSSocket::write);
                    sockJSSocket.handler(buffer -> {
                        String message = buffer.toString();
                        System.out.println("Received message: " + message);

                        // Echo the message back to the client
                        sockJSSocket.write(message);

                        vertx.setPeriodic(100,id -> {
                            sockJSSocket.write("heelo");
                        });
                    });
                }));

        vertx.createHttpServer().requestHandler(router)
                .listen(8080,http->{
                   if(http.succeeded())
                   {
                       System.out.println("Server started on port 8080");
                   }
                   else
                   {
                       System.out.println("Failed to start the server");
                   }
                });
    }
}
