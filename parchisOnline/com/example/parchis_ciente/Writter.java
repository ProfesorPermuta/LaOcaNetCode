package com.example.parchis_ciente;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Writter implements Runnable {

    Socket socket;
    Oca oca;
    Log log;

    public Writter(Socket socket, Oca oca, Log log) {
        this.socket = socket;
        this.oca = oca;
        this.log = log;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        // Envio de mensajes al servidor

        try  {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            crearJFrame(oca, log, out);
           
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            log.addLog("Error al crear el socket de salida");
            e1.printStackTrace();
        }

    }

    private static void crearJFrame(Oca oca, Log log, PrintWriter out) {
        // Crear interfaz grafica simple para poder iniciar el juego y tirar el dado

        // Crear un JFrame
        javax.swing.JFrame frame = new javax.swing.JFrame("Parchis");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new java.awt.GridLayout(2, 2)); // Replace TableLayout with GridLayout
        // Crear un JPanel para el tablero
        javax.swing.JPanel panel = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
            }
        };
        frame.add(panel, java.awt.BorderLayout.CENTER);

        javax.swing.JPanel panel2 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
            }
        };
        frame.add(panel2, java.awt.BorderLayout.CENTER);
        // Crear un JButton para tirar el dado
        javax.swing.JButton button = new javax.swing.JButton("Tirar dado");
        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Enviar el mensaje al servidor para tirar el dado

                out.println("play");
                out.flush();
            }
        });
        frame.add(button, java.awt.BorderLayout.SOUTH);
        // Crear un JButton para iniciar el juego
        javax.swing.JButton button2 = new javax.swing.JButton("Iniciar juego");
        button2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Enviar el mensaje al servidor para iniciar el juego
                out.println("ini");
                out.flush();

            }
        });

        frame.add(button2, button);
        frame.setVisible(true);
    }

}
