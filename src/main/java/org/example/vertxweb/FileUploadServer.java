package org.example.vertxweb;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.FileUpload;

import java.util.List;

public class FileUploadServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);

//        BodyHandler bodyHandler = BodyHandler.create().setBodyLimit(1024L *1024*1024*2);

        // Enable body handling for multipart uploads
        router.route().handler(BodyHandler.create());

        // Define a route for file uploads
        router.post("/some/path/uploads").handler(FileUploadServer::handleFileUpload);

        // Start the HTTP server
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        System.out.println("Server is now listening on port 8080");
                    } else {
                        System.out.println("Failed to bind: " + result.cause());
                    }
                });
    }

    // Handler for processing the file upload
    private static void handleFileUpload(RoutingContext ctx) {
        List<FileUpload> uploads = ctx.fileUploads();

        // Check if there are any uploads
        if (uploads.isEmpty()) {
            ctx.response().setStatusCode(400).end("No file uploaded.");
            return;
        }

        // Process each uploaded file
        uploads.forEach(upload -> {
            String uploadedFileName = upload.uploadedFileName();
            String name = upload.name();
            String fileName = upload.fileName();
            long size = upload.size();

            // Log the details of the uploaded file
            System.out.println("Uploaded File Name: " + fileName);
            System.out.println("Field Name: " + name);
            System.out.println("Uploaded File Path: " + uploadedFileName);
            System.out.println("Size: " + size + " bytes");
        });

        ctx.response().setStatusCode(200).end("Files uploaded successfully!");
    }
}
