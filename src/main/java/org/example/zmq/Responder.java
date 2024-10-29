package org.example.zmq;
import org.zeromq.ZMQ;
public class Responder
{
    public static void main(String[] args)
    {
        ZMQ.Context context = ZMQ.context(1);


        ZMQ.Socket responder = context.socket(ZMQ.REP);
        responder.bind("tcp://*:5555");

        System.out.println("System is ready to receive messages...");

        while (!Thread.currentThread().isInterrupted())
        {
            byte[] request = responder.recv(0);
            System.out.println("Received request: "+new String(request,ZMQ.CHARSET));

            String response = "Hello from server..";
            responder.send(response.getBytes(ZMQ.CHARSET),0);
        }
        responder.close();
        context.close();
    }
}
