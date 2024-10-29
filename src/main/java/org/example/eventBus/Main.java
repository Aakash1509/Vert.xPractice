package org.example.eventBus;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Main
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        DeploymentOptions options = new DeploymentOptions().setInstances(1);

        vertx.deployVerticle(ExampleA.class.getName(),options,res->{
            try
            {
                if(res.succeeded())
                {
                    System.out.println("Receiver verticle deployed successfully");

                    vertx.deployVerticle(ExampleB.class.getName());
                }
                else
                {
                    System.out.println("Failed to deploy verticle : "+res.cause().getMessage());
                }
            } catch (Exception e)
            {
                System.out.println(e);
            }

        });


        /*
        vertx.deployVerticle(new ReceiverVerticle(),res->{
            if(res.succeeded())
            {
                System.out.println("Receiver verticle deployed successfully");

                vertx.deployVerticle(new SenderVerticle());
            }
            else
            {
                System.out.println("Failed to deploy verticle : "+res.cause().getMessage());
            }
        });

         */



        /*
        vertx.deployVerticle(new ReceiverVerticle2(),res->{
            try
            {
                if(res.succeeded())
                {
                    System.out.println("Receiver verticle deployed successfully");

                    vertx.deployVerticle(new SenderVerticle2());
                }
                else
                {
                    System.out.println("Failed to deploy verticle : "+res.cause().getMessage());
                }
            } catch (Exception e)
            {
                System.out.println(e);
            }

        });

         */

        /*
        vertx.deployVerticle(new ReceiverReply(),res->{
            if(res.succeeded())
            {
                System.out.println("Receiver verticle deployed successfully");

                vertx.deployVerticle(new SenderRequest());
            }
            else
            {
                System.out.println("Failed to deploy verticle : "+res.cause().getMessage());
            }
        });

         */
        /*
        vertx.deployVerticle(new TimeOutReceiver(),res->{
           if(res.succeeded())
           {
               System.out.println("TimeOutReceiver verticle deployed successfully");

               vertx.deployVerticle(new TimeOutSender());
           }
           else
           {
               System.out.println("Failed to deploy verticle : "+res.cause().getMessage());
           }
        });

         */
    }
}
