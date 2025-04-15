package com.example.parchis;

import com.example.sharedData.ResultadoTurno;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client implements Runnable {

    private final Socket socket;

    private final Parchis partida;

    private int idJugador;

    BroadcastChannel broadcastChannel = BroadcastChannel.getInstance();

    public Client(Socket socket, Parchis partida) {

        this.socket = socket;
        this.partida = partida;
        broadcastChannel.addOutputStream(socket);

        this.idJugador = partida.addJugador();
    }

    @Override
    public void run() {

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) 
            {

            String line;

            while ((line = in.readLine()) != null) {
                System.out.println("Received: " + line);
                if (line.equals(Acciones.INICIO_JUEGO.getMensaje())) {
                    // Inicia el juego con el número de jugadores actual
                    try {
                        partida.iniciarJuego(idJugador);
                    } catch (IllegalStateException e) {
                        System.out.println("No se puede iniciar el juego. Jugadores insuficientes");
                        broadcastChannel.notifyAll("No se puede iniciar el juego. Jugadores insuficientes");
                        continue;
                    }
                    broadcastChannel.notifyAll("INI:" + partida.getnJugadores());
                }

                if (line.equals(Acciones.JUGAR_TURNO.getMensaje())) {
                    // Jugar turno
                    try {
                        ResultadoTurno res = partida.jugarTurno(idJugador);
                        // Convierto el resultado en un JSON para enviarlo al cliente

                        broadcastChannel.notifyAll("TUR:" + ResultadoTurno.toJsonString(res));
                    } catch (IllegalStateException e) {
                        broadcastChannel.notifyAll("No se puede jugar el turno. Jugador no es el turno.");
                        continue;
                    }
                }

                if (line.equals(Acciones.CANCELAR_JUEGO.getMensaje())) {
                    // Cancelar juego

                    partida.cancelarJuego(idJugador);

                    broadcastChannel.notifyAll("CANCELADO:" + idJugador);
                }
            }
        } catch (IOException e) {
            System.out.println("Error en conexi'on cliente");
        }
    }

}
