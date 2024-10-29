package org.example.zmq;

import org.zeromq.ZMQ;

public class PublisherBlocking {
    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://*:5555");

        int messageCount = 0;

        while (true) {
            String message = "Message #" + (++messageCount);
            System.out.println("Sending: " + message);

            // Blocking send
            publisher.send(message);
            Thread.sleep(500); // Slow down to simulate some processing time
        }
    }
}
