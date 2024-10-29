package org.example.vertxweb;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class PathParams
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);

        router.route(HttpMethod.POST,"/catalogue/:productType/:productID/").handler(ctx->{
            // Retrieve path parameters
            String productType = ctx.pathParam("productType");
            String productID = ctx.pathParam("productID");

            // Retrieve query parameters
            String sortBy = ctx.queryParam("sortBy").isEmpty() ? null : ctx.queryParam("sortBy").get(0);
            String filter = ctx.queryParam("filter").isEmpty() ? null : ctx.queryParam("filter").get(0);
            String name = ctx.queryParam("name").isEmpty() ? null : ctx.queryParam("name").get(0);

            //Whole query
            String query = ctx.request().query();

            //URL
            String url = ctx.request().uri();

            //Path
            String path = ctx.request().path();

            //Method
            String method = ctx.request().method().name();

            System.out.println("Product Type: " + productType);
            System.out.println("Product ID: " + productID);
            System.out.println("Sort By: " + sortBy);
            System.out.println("Filter: " + filter);
            System.out.println("Name: " + name);
            System.out.println("Query: "+query);
            System.out.println("URL: "+url);
            System.out.println("Path: "+path);
            System.out.println("Method: "+method);

            ctx.response().end("See in console");
        });

        vertx.createHttpServer().requestHandler(router)
                .listen(8080,http->{
                   if(http.succeeded())
                   {
                       System.out.println("Server listening on port 8080");
                   }
                   else
                   {
                       System.out.println("Failed to start server");
                   }
                });
    }
}
