package org.example.zmq;

import org.zeromq.ZMQ;

public class Timeout {
    public static void main(String[] args) {
        // Create a new ZeroMQ context
        try (ZMQ.Context context = ZMQ.context(1)) {
            // Create a new socket of type REQ (Request)
            ZMQ.Socket requester = context.socket(ZMQ.REQ);

            // Set a timeout of 2 seconds for receiving and 1 second for sending
            requester.setReceiveTimeOut(2000); // 2000 ms receive timeout
            requester.setSendTimeOut(1000);    // 1000 ms send timeout

            // Connect to a server that may or may not be available
            requester.connect("tcp://localhost:5555");

            try {
                // Attempt to send a message
                String request = "Hello";
                System.out.println("Sending: " + request);

                // This send will only wait 1 second before timing out if it cannot send
                if (!requester.send(request)) {
                    System.out.println("Send timed out!");
                }

                // Attempt to receive a reply
                // This receive will only wait 2 seconds before timing out
                String reply = requester.recvStr();
                if (reply == null) {
                    System.out.println("Receive timed out!");
                } else {
                    System.out.println("Received reply: " + reply);
                }
            } finally {
                // Clean up
                requester.close();
            }
        }
    }
}
