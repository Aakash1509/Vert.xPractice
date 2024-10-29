package org.example.zmq;

import org.zeromq.ZMQ;

public class PublisherHWM
{
    public static void main(String[] args) throws InterruptedException
    {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);

        publisher.setHWM(5000);
        publisher.bind("tcp://*:5556");

        Thread.sleep(5000);
        int messageCount = 0;

        for(int i=0;i<10000;i++)
        {
            String message = "Message - "+messageCount++;
            System.out.println("Publishing: "+message);
            publisher.send(message.getBytes(ZMQ.CHARSET),0);
            Thread.sleep(1);
        }

        Thread.sleep(5000);
        publisher.close();
        context.close();
    }
}
