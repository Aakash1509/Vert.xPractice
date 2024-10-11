package org.example.filesystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

public class FileSystem1 extends AbstractVerticle
{
    public void start()
    {
        //Copying a file
        vertx.fileSystem().copy("my_file.txt","foo.txt").onComplete(res->{
            if(res.succeeded())
            {
                System.out.println("Successfully copied");
            }
            else
            {
                System.out.println("Error : "+res.cause().getMessage());
            }
        });

        vertx.fileSystem().readFile("my_file.txt").onComplete(res->{
            if(res.succeeded())
            {
                System.out.println("Reading : "+res.result());
            }
            else
            {
                System.out.println("Error : "+res.cause().getMessage());
            }
        });

        vertx.fileSystem().writeFile("my_file.txt", Buffer.buffer("Writing in a file")).onComplete(result->{
           if(result.succeeded())
           {
               System.out.println("Written in a file");
           }
           else
           {
               System.out.println("Error : "+ result.cause().getMessage());
           }
        });

        vertx.fileSystem().exists("foo.txt").compose(exist->{
           if(exist)
           {
                return vertx.fileSystem().delete("foo.txt");
           }
           else
           {
                return Future.failedFuture("File does not exist");
           }
        }).onComplete(res->{
            if(res.succeeded())
            {
                System.out.println("File deleted");
            }
            else
            {
                System.out.println("Error : "+res.cause().getMessage());
            }
        });

        System.out.println("Hello");
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new FileSystem1());
    }
}
