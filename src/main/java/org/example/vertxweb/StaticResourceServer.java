package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class StaticResourceServer extends AbstractVerticle {

    @Override
    public void start() {

        // Create a router object
        Router router = Router.router(vertx);

        // Serve static resources from the directory 'webroot'
        // Any request starting with "/static/" will look for files in the webroot directory
        router.route("/staticchange/*").handler(StaticHandler.create("public"));

        // Start the HTTP server and pass the router as request handler
        vertx.createHttpServer().requestHandler(router).listen(8080, http -> {
            if (http.succeeded()) {
                System.out.println("HTTP server started on port 8080");
            } else {
                System.out.println("Failed to start HTTP server: " + http.cause());
            }
        });
    }

    public static void main(String[] args) {
        // Deploy the Verticle
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new StaticResourceServer());
    }
}
