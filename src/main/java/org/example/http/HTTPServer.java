package org.example.http;

import io.vertx.core.AbstractVerticle;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;

public class HTTPServer extends AbstractVerticle
{
    public void start()
    {
        HttpServer server = vertx.createHttpServer();

        //When a request arrives, the request handler is called passing in an instance of
        //HttpServerRequest
        //. This object represents the server side HTTP request.


        //The server request object allows you to retrieve the url,path,params and headers , amongst other things.Each server request object is associated with one server response object. You use response to get a reference to the HttpServerResponse object.

        server.requestHandler(request->{
//            String httpVersion = request.version().name();
//            System.out.println("HTTP version: "+httpVersion);
//            String uri = request.uri();
            MultiMap headers = request.headers();

            headers.forEach(header->{
                System.out.println("Header : "+header.getKey()+" = "+header.getValue());
            });

            String userAgent = headers.get(HttpHeaders.USER_AGENT);

            System.out.println("User-Agent: "+userAgent);

            //To send headers in response , need to explicitly add them

            //By default, the Vert.x server will send the basic HTTP response headers like content-length
            request.response().end("Hello world headers received");
        });

        server.listen(8080,"localhost").onComplete(res->{
           if(res.succeeded())
           {
               System.out.println("Server is now listening");
           }
           else
           {
               System.out.println("Failed to bind");
           }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new HTTPServer());
    }
}
