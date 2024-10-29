package org.example.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class FormServer extends AbstractVerticle
{
    public void start()
    {
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

//        BodyHandler bodyHandler = BodyHandler.create().setBodyLimit(1024*1024);
//
//        router.route().handler(BodyHandler.create());

        router.route("/submit").handler(res->{
           //Indicating server expects multipart form
            res.request().setExpectMultipart(true);

            //Handling file uploads
            res.request().uploadHandler(upload->{

                System.out.println("Uploaded file name : "+upload.filename());
                upload.streamToFileSystem(upload.filename()).onComplete(result -> {
                    try
                    {
                        if(result.succeeded())
                        {
                            System.out.println("file size: " + upload.size() + " bytes");
                            System.out.println("file saved.");
                            //Sending response back to client
                            res.response().end("Form submission with file received");
                        }
                        else
                        {

                        }
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                });
//                System.out.println("Uploaded file size : "+upload.file().size());
            });

            //Handle form data once entire body is received
            /*
            res.request().endHandler(v->{
                MultiMap formAttributes = res.request().formAttributes();

                formAttributes.forEach(attr->{
                    System.out.println("Form field: "+attr.getKey()+" = "+attr.getValue());
                });

                //Sending response back to client
                res.response().end("Form submission with file received");
            });

             */

        });

        server.requestHandler(router).listen(8080,"localhost").onComplete(res->{
            if(res.succeeded())
            {
                System.out.println("Server is now listening on http://localhost:8080");
            }
            else
            {
                System.out.println("Failed to bind the server");
            }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new FormServer());
    }
}
