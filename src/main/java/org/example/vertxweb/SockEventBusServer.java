package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class SockEventBusServer extends AbstractVerticle {

    @Override
    public void start() {
        // Create an HTTP server
        Router router = Router.router(vertx);

        // Create a SockJS handler for bridging event bus messages
        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);

        // Define permissions for inbound and outbound messages
        SockJSBridgeOptions options = new SockJSBridgeOptions()
                .addInboundPermitted(new PermittedOptions().setAddress("chat.to.server"))
                .addOutboundPermitted(new PermittedOptions().setAddress("chat.to.client"));

        // Mount the SockJS bridge on the router at the "/eventbus/*" endpoint
        router.route("/eventbus/*").subRouter(sockJSHandler.bridge(options));

        // Start the HTTP server on port 8080
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        System.out.println("Server is now listening on port 8080");
                    } else {
                        System.out.println("Failed to start server: " + result.cause());
                    }
                });

        // Example of sending a message to the client
//        vertx.setPeriodic(5000, timerId -> {
//            System.out.println("Sending message to clients");
//            vertx.eventBus().publish("chat.to.client", "Message from server to client!");
//        });

        // Register a consumer to handle messages from the client
        vertx.eventBus().consumer("chat.to.server", message -> {
            System.out.println("Received message from client: " + message.body());
            vertx.eventBus().publish("chat.to.client", message.body());
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SockEventBusServer());
    }
}
