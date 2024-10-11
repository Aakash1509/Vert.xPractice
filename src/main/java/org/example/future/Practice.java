package org.example.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;

public class Practice extends AbstractVerticle
{
    public void start()
    {
        //Reading data from file

        Future<Buffer> readFuture = vertx.fileSystem().readFile("my_file.txt");

        //Processing on received data
        Future<Integer> processedFuture = readFuture.compose(buffer -> {
           String fileContent = buffer.toString();
           int lineCount = fileContent.split("\n").length;
            System.out.println("Number of lines: "+lineCount);
            return Future.succeededFuture(lineCount);
        });

        //Wrting result in another file
        Future<Void> writeFuture = processedFuture.compose(lineCount->{
            String output = "The file contains "+lineCount+" lines";
            return vertx.fileSystem().writeFile("output.txt",Buffer.buffer(output));
        });

        writeFuture.onComplete(res->{
           if(res.succeeded())
           {
               System.out.println("Successfully generated output");
           }
           else
           {
               System.out.println("Error "+res.cause().getMessage());
           }
           vertx.close();
        });

    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Practice());
    }
}
