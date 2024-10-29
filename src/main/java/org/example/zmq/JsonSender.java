package org.example.zmq;

import io.vertx.core.json.JsonObject;
import org.zeromq.ZMQ;

public class JsonSender {
    public static void main(String[] args) {
        // Create a new ZeroMQ context and a PUSH socket
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket pushSocket = context.socket(ZMQ.PUSH);

        // Bind to a TCP endpoint
        pushSocket.bind("tcp://*:5555");

        // Create a sample JSON object
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("name", "John Doe");
        jsonObject.put("age", 30);
        jsonObject.put("email", "john.doe@example.com");

        // Convert JSON object to a string
        String jsonString = jsonObject.toString();

        // Send the JSON string as a message
        System.out.println("Sending JSON: " + jsonString);
        pushSocket.send(jsonString.getBytes(ZMQ.CHARSET), 0);

        // Clean up ZeroMQ resources
        pushSocket.close();
        context.close();
    }
}
