package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class AsynchronousVerticle2 extends AbstractVerticle
{
    private HttpServer server;

    public void start(Promise<Void> promise) //The Promise<Void> is used to notify Vert.x whether the Verticle was started successfully or not.
    {
        server = vertx.createHttpServer().requestHandler(req->{
           req.response().putHeader("content-type","text/plain").end("Hello from Vert.x! Sky");
        });

        server.listen(9999 ,res->{
            if(res.succeeded())
            {
                promise.complete();  //If the server binds successfully (res.succeeded()), the promise is completed with promise.complete(). This informs Vert.x that the Verticle has been successfully deployed.
            }
            else
            {
                promise.fail(res.cause());
            }
        });
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        //After the start() method of the Verticle either completes or fails (depending on whether the server was successfully started), the callback from the deployVerticle method is triggered in the main() method:

        /*
        vertx.deployVerticle(new AsynchronousVerticle2(),res->{
            if(res.succeeded()){
                System.out.println("Verticle deployment id is : "+res.result());
            }
            else
            {
                System.out.println("Verticle deployment failed : "+res.cause().getMessage());
            }
        });

         */
        vertx.deployVerticle(new AsynchronousVerticle2()).onComplete(res->{
           if(res.succeeded())
           {
               String deploymentID = res.result();
               System.out.println("Verticle deployment id is : "+deploymentID);

               //Undeploying verticle after 5 seconds
               vertx.setTimer(5000,id->{
                   System.out.println("Undeploying the verticle");
                   vertx.undeploy(deploymentID,result->{
                       if(result.succeeded())
                       {
                           System.out.println("Verticle successfully undeployed");
                       }
                       else
                       {
                           System.out.println("Failed to deploy verticle : "+result.cause().getMessage());
                       }
                   });
               });
           }
           else
           {
               System.out.println("Verticle deployment failed : "+res.cause().getMessage());
           }
        });
    }
}
