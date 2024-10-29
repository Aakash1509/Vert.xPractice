package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class SimpleResponse extends AbstractVerticle
{
    public void start()
    {
        Router router = Router.router(vertx);


        router.get("/some/path")
                .respond(ctx-> Future.succeededFuture(new JsonObject().put("hello","world")));

        router.get("/some/path")
                .respond(ctx-> Future.succeededFuture(new JsonObject().put("hello","Aakash")));

        /* Non-JSON responses
        router.get("/some/path")
                        .respond(ctx->ctx.response().putHeader("Content-Type","text/plain").end("Hello World!"));

        router.get("/some/path")
                .respond(ctx->ctx.response().setChunked(true).write("Write some text..."));
        */

        router.get("/some/pojo")
                .respond(ctx->Future.succeededFuture(new Pojo("John","Doe",25)));

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080,result->{
                   if(result.succeeded())
                   {
                       System.out.println("Server is running");
                   }
                   else
                   {
                       System.out.println("Failed to start the server "+result.cause().getMessage());
                   }
                });
    }

    public static class Pojo
    {
        private String firstName;
        private String lastName;
        private int age;

        public Pojo(String firstName, String lastName, int age)
        {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        // Getters and Setters (required for serialization)


        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public int getAge()
        {
            return age;
        }

        public void setAge(int age)
        {
            this.age = age;
        }
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SimpleResponse());
    }
}
