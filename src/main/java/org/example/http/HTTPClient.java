package org.example.http;

import io.vertx.core.AbstractVerticle;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

public class HTTPClient extends AbstractVerticle
{
    public void start()
    {
        HttpClient client = vertx.createHttpClient();

        client.request(HttpMethod.GET,8080,"localhost","/").onComplete(ar->{
            if(ar.succeeded())
            {
                System.out.println(ar.result());
                HttpClientRequest request = ar.result().putHeader("Custom-Header","MyValue").putHeader("User-Agent","My-Client/1.0");
//                System.out.println("Connected to server "+ar.result().body());
                request.send().onComplete(ar1->{
                   if(ar1.succeeded())
                   {
                       ar1.result().headers().forEach(header -> {
                           System.out.println("Response Header: " + header.getKey() + " = " + header.getValue());
                       });
                       HttpClientResponse response = ar1.result();
                       System.out.println("Received response with status code : "+response.statusCode());
                       response.body().onComplete(res->{
                           if(res.succeeded())
                           {
                               System.out.println("Response body : "+response.body());
                           }
                       });
                   }
                });
            }
            else
            {
                System.out.println("Error in connecting with server : "+ar.cause().getMessage());
            }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new HTTPClient());
    }
}
