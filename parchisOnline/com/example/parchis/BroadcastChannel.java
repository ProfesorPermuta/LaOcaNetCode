package com.example.parchis;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BroadcastChannel {

    List<Socket> outputStreams = new ArrayList();

    private BroadcastChannel() {
        // Constructor
    }

    private static BroadcastChannel instance = null;

    public static BroadcastChannel getInstance() {
        if (instance == null) {
            instance = new BroadcastChannel();
        }
        return instance;
    }

    public void addOutputStream(Socket out) {
        outputStreams.add(out);
    }

    public void notifyAll(String message) {
        for (Socket socket : outputStreams) {
            try{
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.write(message + "\n");
                out.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           

        }
    }

}
