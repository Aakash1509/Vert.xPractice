package org.example.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class HTTPServer2 extends AbstractVerticle {

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();

        server.requestHandler(request -> {
            request.response().setChunked(true);

            // Write the first chunk
            request.response().write("Chunk1\n");

            // Write the second chunk after 1 second
            vertx.setTimer(1000, id -> {
                request.response().write("Chunk2\n");
            });

            // After 2 seconds, send the file
            vertx.setTimer(2000, id -> {
                try {
                    // Send file (headers are already sent due to chunked responses)
                    request.response().sendFile("copied.txt", res -> {
                        if (res.succeeded()) {
                            // After sending the file, finish the response
                            request.response().end("File sent successfully");
                        } else {
                            request.response().end("Failed to send a file");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        server.listen(8080, "localhost").onComplete(res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening");
            } else {
                System.out.println("Failed to bind");
            }
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HTTPServer2());
    }
}
