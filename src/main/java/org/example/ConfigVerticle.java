package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class ConfigVerticle extends AbstractVerticle
{
    public void start()
    {
        JsonObject config = config();

        String name = config.getString("name");

        String age = config.getString("age");

        System.out.println("Age is : "+age);

        System.out.println("Name is : "+name);
    }
}
