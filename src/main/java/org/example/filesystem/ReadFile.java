package org.example.filesystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class ReadFile extends AbstractVerticle
{
    public void start()
    {
        vertx.fileSystem().readFile("my_file.txt").onComplete(res->{
            if(res.succeeded())
            {
                System.out.println("File content length : "+res.result().length());
                System.out.println("File content : "+res.result().toString());
            }
            else
            {
                System.out.println("Error reading file : "+res.cause().getMessage());
            }
        });
        System.out.println("Non-blocking");
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ReadFile());
    }
}
