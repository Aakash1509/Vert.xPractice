package org.example.zmq;

import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class NoDropPublisher {

    public static void main(String[] args) throws InterruptedException {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket xpub = context.createSocket(ZMQ.XPUB);
            xpub.setXpubNoDrop(true);  // Enable XPUB_NO_DROP
            xpub.setHWM(10);
            xpub.bind("tcp://*:5555");

            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                String message = "Message " + count++;
                System.out.println("Publishing: " + message);
                boolean sent = xpub.send(message.getBytes(ZMQ.CHARSET), ZMQ.DONTWAIT);
                if (!sent) {
                    System.err.println("Could not send message: " + message + ", EAGAIN error encountered.");
                } else {
                    System.out.println("Sent: " + message);
                }
                Thread.sleep(10); // Fast publish rate to simulate message saturation
            }
        }
    }
}
