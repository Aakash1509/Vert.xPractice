package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class AsynchronousVerticle extends AbstractVerticle {

    // Verticle start method
    public void start(Promise<Void> promise) {
        System.out.println("Starting verticle...");

        // Start server and deploy Verticle only if server starts successfully
        startServer().onComplete(result -> {
            if (result.succeeded()) {
                System.out.println("Server started successfully.");
                vertx.setTimer(1000, id -> {
                    System.out.println("Verticle started successfully.");
                    promise.complete(); // Signal Verticle is fully started
                });
            } else {
                System.out.println("Server failed to start: " + result.cause().getMessage());
                promise.fail(result.cause());
            }
        });
    }

    // Asynchronous server start simulation
    private Future<Void> startServer() {
        Promise<Void> promise = Promise.promise();

        // Simulate async server start with a timer
        vertx.setTimer(2000, id -> {
            System.out.println("Server startup process completed.");
            promise.complete(); // Complete the promise after server starts
        });

        return promise.future();
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // Deploy the AsynchronousVerticle instance
        vertx.deployVerticle(new AsynchronousVerticle(), res -> {
            if (res.succeeded()) {
                System.out.println("Verticle deployment id is: " + res.result());
            } else {
                System.out.println("Verticle deployment failed: " + res.cause().getMessage());
            }
        });
    }
}
