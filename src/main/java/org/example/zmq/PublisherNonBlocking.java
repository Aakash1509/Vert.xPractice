package org.example.zmq;

import org.zeromq.ZMQ;

public class PublisherNonBlocking {
    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://*:5555");

        int messageCount = 0;

        while (true) {
            String message = "Message #" + (++messageCount);
            System.out.println("Sending: " + message);

            // Non-blocking send with ZMQ.DONTWAIT
            boolean sent = publisher.send(message, ZMQ.DONTWAIT);

            if (!sent) {
                System.out.println("Queue full, message dropped: " + message);
            }
            Thread.sleep(100); // Reduce delay to increase chance of full queue
        }
    }
}
