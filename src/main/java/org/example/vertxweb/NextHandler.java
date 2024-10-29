package org.example.vertxweb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class NextHandler extends AbstractVerticle
{
    public void start()
    {
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        Route route = router.route("/some/path/");

        route.handler(ctx->{
            HttpServerResponse response = ctx.response();

            // enable chunked responses because we will be adding data as
            // we execute over other handlers. This is only required once and
            // only if several handlers do output.

            response.setChunked(true);

            response.write("route1\n");

            ctx.next();

//            ctx.vertx().setTimer(5000,tid->ctx.next());
        });

        route.handler(ctx->{
            HttpServerResponse response = ctx.response();

            response.write("route2\n");

            ctx.next();

//            ctx.vertx().setTimer(5000,tid->ctx.next());
        });

        route.handler(ctx->{
            HttpServerResponse response = ctx.response();

            response.write("route3");

            ctx.response().end();
        });

        server.requestHandler(router).listen(8080,http->{
            if(http.succeeded())
            {
                System.out.println("HTTP server started on port 8080");
            }
            else
            {
                System.out.println("HTTP server failed to start : "+http.cause().getMessage());
            }
        });
    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new NextHandler());
    }
}
