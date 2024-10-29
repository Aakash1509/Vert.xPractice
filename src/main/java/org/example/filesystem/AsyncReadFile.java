package org.example.filesystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;

public class AsyncReadFile extends AbstractVerticle
{
    public void start()
    {
        vertx.fileSystem().open("1GB_file.txt",new OpenOptions().setRead(true),result->{
           if(result.succeeded())
           {
               AsyncFile file = result.result();

               file.setReadBufferSize(16*1024);

               file.handler(buffer->{
                   System.out.println("Chunk size : "+buffer.length());
                   System.out.println("Chunk data : "+buffer);
               });

               file.endHandler(v->{
                   System.out.println("File reading completed");
                   file.close();
               });
           }
           else
           {
               System.out.println("Failed to open file :"+result.cause().getMessage());
           }
        });
        System.out.println("Non-blocking");
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new AsyncReadFile());
    }
}
