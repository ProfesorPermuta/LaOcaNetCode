package com.example.parchis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int MAX_PLAYERS = 4;

    private static final int PORT = 5000;
    private static final ExecutorService threadHandler = Executors.newFixedThreadPool(MAX_PLAYERS);

    private ServerSocket sockets;

    private Parchis partida = new Parchis();

    public void run() throws IOException {

        sockets = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT);
        while (true) {
            Socket clientSocket = sockets.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
            threadHandler.execute(new Client(clientSocket, partida));
        }

    }

}
