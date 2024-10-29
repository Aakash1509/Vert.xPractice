package org.example.eventBus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SenderVerticle2 extends AbstractVerticle
{
    public void start()
    {
        EventBus eb = vertx.eventBus();

        int[] arr = {1,2,3};

        //Map
        Map<String,Object> map = new HashMap<>();
        map.put("name","aakash");

        JsonObject obj = new JsonObject(map);
        Map<String,Object> map1 = obj.getMap();
        System.out.println("Converted from json to map : "+map1);

        //Arraylist,Linkedlist
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(6);
        JsonArray array = new JsonArray(list);

        //Set
        Set<Integer> set = new LinkedHashSet<>();
        set.add(6);
        set.add(7);

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        queue.add(2);

        //Primitive
        int i = 19;

        //JsonObject

        JsonObject jsonObject = new JsonObject().put("name","Alice").put("age",25);

        //JsonArray

        JsonArray jsonArray = new JsonArray().add("item1").add(5).add(5.9);

        //Data structures in Json Object
        JsonObject jsonObject1 = new JsonObject().put("queue",queue); //Set when put into JsonObject , it is not transferred through event bus
        eb.publish("message.address",arr);



        /*
        AtomicInteger i = new AtomicInteger(0);

        vertx.setPeriodic(1000,id->{
            String message = "Hello "+ i.get() +" "+System.currentTimeMillis();
            eb.publish("message.address",message);
            System.out.println("Sent : "+message);
            i.getAndSet(i.get() + 1);
        });

         */


    }
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
//        vertx.deployVerticle(new SenderVerticle2());
    }
}
