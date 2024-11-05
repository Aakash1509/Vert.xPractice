package org.example.zmq;

import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class MultiPartPublisher {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket publisher = context.createSocket(ZMQ.PUB);
            publisher.bind("tcp://*:5556");

            // Give some time for subscribers to connect
            Thread.sleep(1000);

            String topic = "weather";
            String location = "NYC";
            String temperature = "25C";

            System.out.println("Publishing multi-part message...");

            // Send each part with SNDMORE except for the last part
            publisher.send(topic.getBytes(ZMQ.CHARSET), ZMQ.SNDMORE);
            publisher.send(location.getBytes(ZMQ.CHARSET), ZMQ.SNDMORE);
            publisher.send(temperature.getBytes(ZMQ.CHARSET)); // No SNDMORE on the last part
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
