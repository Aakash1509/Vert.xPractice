package org.example.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

import io.vertx.core.buffer.Buffer;

public class HTTPClient2 extends AbstractVerticle
{
    public void start()
    {
        HttpClient client = vertx.createHttpClient();

        client.request(HttpMethod.GET,8080,"localhost","/").onComplete(ar->{
           if(ar.succeeded())
           {
               HttpClientRequest request = ar.result();

               request.send().onComplete(ar1->{
                   if(ar1.succeeded())
                   {
                       System.out.println("Connected to server");

                       HttpClientResponse response = ar1.result();

                       //Creating empty buffer to receive all chunks

                       Buffer totalBuffer = Buffer.buffer();


                       response.handler(buffer -> {
                           System.out.println("Received a chunk of length: "+buffer.length());
                           totalBuffer.appendBuffer(buffer);
                       });


                       //Once all chunks are received

                       response.endHandler(v->{
                           System.out.println("Full body received, length ="+ totalBuffer.length());
                           System.out.println("Complete body : "+totalBuffer.toString());
                       });

                       response.bodyHandler(body->{
                           System.out.println("Full body received by bodyHandler ="+body.length());
                           System.out.println("Complete body by body handler : "+body.toString());
                       });
                   }
               });
           }
           else
           {
               System.out.println("Failed to connect to server"+ar.cause().getMessage());
           }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new HTTPClient2());
    }
}
