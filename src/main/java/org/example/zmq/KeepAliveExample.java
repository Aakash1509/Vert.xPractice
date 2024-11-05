package org.example.zmq;

import org.zeromq.ZMQ;

public class KeepAliveExample {
    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);

        // Create and configure the REQ (request) socket with keep-alive
        ZMQ.Socket reqSocket = context.socket(ZMQ.REQ);
        reqSocket.setTCPKeepAlive(1);           // Enable keep-alive
        reqSocket.setTCPKeepAliveIdle(10);      // Set idle time before probing to 10 seconds
        reqSocket.setTCPKeepAliveInterval(5);   // Set interval between probes to 5 seconds
        reqSocket.setTCPKeepAliveCount(3);      // Set count of probes before assuming connection is dead

        // Connect to the REP (reply) socket
        reqSocket.connect("tcp://localhost:5555");

        int count = 0;
        while (true) {
            try {
                String message = "Hello " + count++;
                System.out.println("Sending: " + message);

                // Send a request
                reqSocket.send(message);

                // Receive a reply (blocks until a reply is received or keep-alive triggers a disconnect)
                String reply = reqSocket.recvStr();
                System.out.println("Received: " + reply);

                // Wait a bit before sending the next request
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Connection issue detected. Retrying...");

                // Optional reconnection logic
//                reqSocket.close();
//                reqSocket = context.socket(ZMQ.REQ);
//                reqSocket.setTCPKeepAlive(1);
//                reqSocket.setTCPKeepAliveIdle(10);
//                reqSocket.setTCPKeepAliveInterval(5);
//                reqSocket.setTCPKeepAliveCount(3);
//                reqSocket.connect("tcp://localhost:5555");
            }
        }

        // Clean up (this will never reach due to the infinite loop, but for structure)
        // reqSocket.close();
        // context.term();
    }
}
