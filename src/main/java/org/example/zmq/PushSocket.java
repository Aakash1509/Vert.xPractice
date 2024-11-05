package org.example.zmq;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class PushSocket extends AbstractVerticle {
    @Override
    public void start() {
        vertx.executeBlocking(promise -> {
            // Create a ZeroMQ context with 1 I/O thread
            ZContext context = new ZContext();

            // Create a PUSH socket
            ZMQ.Socket pushSocket = context.createSocket(SocketType.PUSH);
            pushSocket.setHWM(100);
            pushSocket.connect("tcp://*:5557"); // Bind to port 5557

            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                // Send a message
//                String message = "Task-" + count++;
//                System.out.println("Sending: " + message);
//                pushSocket.send(message.getBytes(ZMQ.CHARSET),ZMQ.DONTWAIT);
                String receivedMessage = pushSocket.recvStr(0);
                System.out.println("Worker 1 received: " + receivedMessage);

                // Sleep for a second between messages
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Close the socket and context when done
            pushSocket.close();
            context.close();
            promise.complete();
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new PushSocket());
    }
}
