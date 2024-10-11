package org.example.dns;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.dns.DnsClient;
import io.vertx.core.dns.MxRecord;
import io.vertx.core.dns.SrvRecord;

import java.util.List;

public class lookup extends AbstractVerticle
{
    public void start()
    {
        DnsClient client = vertx.createDnsClient(53,"8.8.8.8");
        client.lookup4("vertx.io").onComplete(ar->{
            if(ar.succeeded())
            {
                System.out.println("Resolved IP address "+ar.result());
            }
            else
            {
                System.out.println(ar.cause().getMessage());
            }
        });
        client.resolveSRV("google.com").onComplete(ar->{
            if(ar.succeeded())
            {
                List<SrvRecord> records = ar.result();
                for (SrvRecord record : records) {
                    System.out.println(record);
                }
//                System.out.println("Resolved IP address "+ar.result());
            }
            else
            {
                System.out.println(ar.cause().getMessage());
            }
        });
        client
                .reverseLookup("9.9.9.9")
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        String record = ar.result();
                        System.out.println(record);
                    } else {
                        System.out.println("Failed to resolve entry" + ar.cause());
                    }
                });
        System.out.println("Hello");
    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new lookup());
    }
}
