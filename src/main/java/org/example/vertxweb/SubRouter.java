package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class SubRouter extends AbstractVerticle
{
    public void start()
    {
        Router mainRouter = Router.router(vertx);
        Router userRouter = Router.router(vertx);

        userRouter.get("/create").handler(ctx->{
           ctx.response().end("User created");
        });

        userRouter.get("/delete").handler(ctx->{
           ctx.response().end("User deleted");
        });

        mainRouter.route("/user/*").subRouter(userRouter); //We can now mount the sub router on the main router, against a mount point,

        mainRouter.get("/").handler(ctx->{
           ctx.response().end("Welcome to Homepage!");
        });


        vertx.createHttpServer().requestHandler(mainRouter)
                .listen(8080,http->{
                   if(http.succeeded())
                   {
                       System.out.println("Server started on port 8080");
                   }
                   else
                   {
                       System.out.println("Error in starting port");
                   }
                });
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SubRouter());
    }
}
