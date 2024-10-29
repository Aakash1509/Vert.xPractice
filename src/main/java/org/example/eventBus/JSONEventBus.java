package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

public class JSONEventBus extends AbstractVerticle
{
    public void start()
    {
        EventBus eb = vertx.eventBus();
        FileSystem fileSystem = vertx.fileSystem();

        fileSystem.open("user_data.json",new OpenOptions(),result->{
            if(result.succeeded())
            {
                AsyncFile file = result.result();

                file.handler(buffer->{
                    System.out.println("Read chunk: "+buffer.toString());
                    eb.send("file.chunk",buffer);
                });

                file.endHandler(v->{
                    System.out.println("File reading completed");
                    file.close();
                });
            }
            else
            {
                System.out.println("Failed to open file: "+result.cause());
            }
        });

        fileSystem.open("output_user_data.json",new OpenOptions().setCreate(true).setTruncateExisting(true).setWrite(true),writeResult->{
            if(writeResult.succeeded())
            {
                AsyncFile opfile = writeResult.result();

                eb.consumer("file.chunk",message->{
                    Buffer chunk = (Buffer)message.body();
                    opfile.write(chunk,res->{
                        if(res.succeeded())
                        {
                            System.out.println("Written chunk to op file");
                        }
                        else
                        {
                            System.out.println("Failed to write chunk: "+res.cause().getMessage());
                        }
                    });
                });
            }
            else
            {
                System.out.println("Failed to open file"+writeResult.cause().getMessage()   );
            }
        });

    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new JSONEventBus());
    }
}
