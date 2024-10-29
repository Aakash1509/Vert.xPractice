package org.example.zmq;

import org.zeromq.ZMQ;

public class Linger {
    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);

        // Set linger to 20000 milliseconds (20 seconds)
        publisher.setLinger(20000);
        publisher.getTCPKeepAlive();
        publisher.bind("tcp://*:5556");

        // Send some messages
        for (int i = 0; i < 10; i++) {
            String message = "Message " + i;
            System.out.println("Sending: " + message);
            publisher.send(message);
            Thread.sleep(1000); // Pause to simulate time between messages
        }

        // Close the socket (will linger for 20 seconds to try and send remaining messages)
        System.out.println("Closing publisher socket...");
        publisher.close(); // Here, the lingering starts
        context.close();
    }
}
