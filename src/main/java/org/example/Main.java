package org.example;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class Main
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        //Creating a JsonObject

        JsonObject config = new JsonObject()
                .put("name","Aakash")
                .put("age","21");

        DeploymentOptions options = new DeploymentOptions().setConfig(config);

        vertx.deployVerticle("org.example.ConfigVerticle",options,res->{
           if(res.succeeded())
           {
               System.out.println("Successfully deployed verticle with ID : "+res.result());
           }
           else
           {
               System.out.println("Failed : "+res.cause().getMessage());
           }
        });
    }
}
