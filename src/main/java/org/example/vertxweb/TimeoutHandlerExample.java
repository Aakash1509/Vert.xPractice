package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.TimeoutHandler;

public class TimeoutHandlerExample extends AbstractVerticle {

    @Override
    public void start() {

        // Create a router object
        Router router = Router.router(vertx);

        // Add a timeout handler for all requests to "/foo/*" with a timeout of 5 seconds (5000 ms)
        router.route("/foo/*").handler(TimeoutHandler.create(5000));

        // Application handler that simulates a slow operation by delaying the response
        router.get("/foo/slow").handler(ctx -> {
            vertx.setTimer(6000, id -> {  // Simulate a delay longer than 5 seconds
                if(!ctx.response().ended())
                {
                    ctx.response()
                            .putHeader("content-type", "text/plain")
                            .end("This should timeout after 5 seconds.");
                }
            });
        });

        // Normal handler that responds immediately
        router.get("/foo/fast").handler(ctx -> {
            ctx.response()
                    .putHeader("content-type", "text/plain")
                    .end("This request will complete quickly.");
        });

        router.get("/notfound-handler").handler(ctx->{
            ctx.response().setStatusCode(503).end("Time Out");
        });

        router.get().failureHandler(ctx->{
            if(ctx.statusCode()==503)
            {
                ctx.reroute("/notfound-handler");
            }
            else
            {
                ctx.next();
            }
        });

        // Create an HTTP server and pass the router as request handler
        vertx.createHttpServer().requestHandler(router).listen(8080, http -> {
            if (http.succeeded()) {
                System.out.println("HTTP server started on port 8080");
            } else {
                System.out.println("Failed to start HTTP server");
            }
        });
    }

    public static void main(String[] args) {
        // Deploy the Verticle
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new TimeoutHandlerExample());
    }
}
