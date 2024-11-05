package org.example.zmq;

import org.zeromq.ZMQ;

public class PubHWM {
    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);

        // Set HWM to 10 messages
        publisher.setHWM(10);
        publisher.bind("tcp://*:5556");

        int count = 0;
//        while (true) {
//            String message = "Message " + count++;
//            System.out.println("Publishing: " + message);
//            publisher.send(message.getBytes(ZMQ.CHARSET));
//
//            System.out.println("sent");
//            // Send messages rapidly to overflow the subscriber’s queue
//            Thread.sleep(5);
//        }
        for(int i=0;i<500;i++)
        {
            String message = "Message " + count++;
            System.out.println("Publishing: " + message);
            publisher.send(message.getBytes(ZMQ.CHARSET));

            System.out.println("sent");
            // Send messages rapidly to overflow the subscriber’s queue
            Thread.sleep(5);
        }
    }

}
