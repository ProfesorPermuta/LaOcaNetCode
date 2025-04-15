package com.example.parchis_ciente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain {

    static Socket socket;

    public static void main(String[] args) throws InterruptedException {
        Oca oca = new Oca();
        Log log = new Log();
        // Crear tcp client
        try {

            socket = new Socket("localhost", 5000);
            Thread readThread = new Thread(new Reader(socket, oca, log));
            Thread printThread = new Thread(new Writter(socket, oca, log));

            printThread.start();
            readThread.start();


            while (true) {
                // Crear una instancia de la clase Oca

                System.out.print("\033[H\033[2J");
                System.out.flush();
                // Imprimir el tablero
                oca.print();

                log.printLog(5);

                // Esperar un segundo antes de volver a imprimir
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block

            log.addLog("Error de conexión");
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log.addLog("Error al cerrar el socket");
                e.printStackTrace();
            }
        }

        System.out.println("Fin de la partida");
    }

}
