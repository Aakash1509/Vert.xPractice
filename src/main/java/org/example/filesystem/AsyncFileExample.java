package org.example.filesystem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;

public class AsyncFileExample extends AbstractVerticle
{
    public void start()
    {
        //Random access writes
        /*
        vertx.fileSystem().open("my_file.txt", new OpenOptions()).onComplete(res->{
           if(res.succeeded())
           {
               AsyncFile file = res.result();
               Buffer buff = Buffer.buffer("foo");
               for(int i=0;i<5;i++)
               {
                   file.write(buff, buff.length()*i).onComplete(ar->{
                       if(ar.succeeded())
                       {
                           System.out.println("Written");
                       }
                       else
                       {
                           System.out.println("Failed to write : "+ar.cause().getMessage());
                       }
                   });
               }
           }
           else
           {
               System.out.println("Cannot open file : "+res.cause().getMessage());
           }
        });

         */

        //Random access reads
        /*
        vertx.fileSystem().open("my_file.txt",new OpenOptions()).onComplete(result->{
           if(result.succeeded())
           {
               AsyncFile file = result.result();
               for(int i=0;i<10;i++)
               {
                   Buffer buff = Buffer.buffer(1000);
                   file.read(buff,100,i*100,100).onComplete(res->{
                       if(res.succeeded())
                       {
                           System.out.println(buff);
                           System.out.println("Read ok");
                       }
                       else
                       {
                           System.out.println("Error : "+res.cause().getMessage());
                       }
                   });
               }
           }
           else
           {
               System.out.println("Cannot open file"+result.cause().getMessage());
           }
        });

         */
        final AsyncFile output = vertx.fileSystem().openBlocking("copied.txt",new OpenOptions());

        vertx.fileSystem().open("my_file.txt",new OpenOptions()).compose(file->file.pipeTo(output).eventually(v->file.close())).onComplete(res->{
           if(res.succeeded())
           {
               System.out.println("Copy done");
           }
           else
           {
               System.out.println("Cannot copy file : "+res.cause().getMessage());
           }
        });

    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new AsyncFileExample());
    }
}
