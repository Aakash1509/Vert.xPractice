package org.example.buffer;

import io.vertx.core.AbstractVerticle;

import io.vertx.core.buffer.Buffer;

public class Example extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Buffer buff = Buffer.buffer("Aakash");

        System.out.println(buff.length());

//        buff.appendString("Hello");
//        buff.appendInt(10);
        buff.appendInt(123).appendString("hello\n");

        int value = buff.getInt(6);

        String message = buff.getString(4,5);

        System.out.println("Buffer content : "+buff.toString());
        System.out.println("Integer : "+value);
        System.out.println("String : "+message);
    }
}
