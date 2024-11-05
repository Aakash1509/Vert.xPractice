
package org.example.zmq;

import org.zeromq.ZMQ;

public class HWMExample {
    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket pushSocket = context.socket(ZMQ.PUSH);

        // Set the high water mark for sending messages (queue size)
        pushSocket.setSndHWM(100);
//        pushSocket.setRcvHWM(100);

        pushSocket.bind("tcp://*:5555");

        // Send some messages
        for (int i = 0; i < 1000; i++) {
            boolean sent = pushSocket.send("Message " + i,ZMQ.DONTWAIT);
            if (sent) {
                System.out.println("Sent message: " + i);
            } else {
                System.out.println("Message could not be sent: High Water Mark reached. " + i);
            }
        }
        // Clean up
        pushSocket.close();
        context.close();
    }
}