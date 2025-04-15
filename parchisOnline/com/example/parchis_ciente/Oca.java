package com.example.parchis_ciente;

import com.example.sharedData.ResultadoTurno;

public class Oca {

    // Casillas especiales
    private static final int VA = -1; // Vacio
    private static final int OC = -2;
    private static final int DA = -3;
    private static final int PU = -4;
    private static final int CA = -5;
    private static final int MU = -6;

    private static int tablero[][] = {
            { 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20 },
            { 31, 62, 61, 60, 59, 58, 57, 56, 55, 54, 19 },
            { 32, 63, VA, VA, VA, VA, VA, VA, VA, 53, 18 },
            { 33, VA, VA, VA, VA, VA, VA, VA, VA, 52, 17 },
            { 34, VA, VA, VA, VA, VA, VA, VA, VA, 51, 16 },
            { 35, VA, VA, VA, VA, VA, VA, VA, VA, 50, 15 },
            { 36, VA, VA, VA, VA, VA, VA, VA, VA, 49, 14 },
            { 37, VA, VA, VA, VA, VA, VA, VA, VA, 48, 13 },
            { 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 12 },
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 }
    };

    private static int tableroAscii[][] = {
            { 30, 29, 28, OC, 26, 25, 24, OC, 22, 21, 20 },
            { CA, 62, 61, 60, OC, MU, 57, 56, 55, OC, CA },
            { OC, OC, VA, VA, VA, VA, VA, VA, VA, DA, OC },
            { 33, VA, VA, VA, VA, VA, VA, VA, VA, 52, 17 },
            { 34, VA, VA, VA, VA, VA, VA, VA, VA, 51, 16 },
            { 35, VA, VA, VA, VA, VA, VA, VA, VA, OC, 15 },
            { DA, VA, VA, VA, VA, VA, VA, VA, VA, 49, OC },
            { 37, VA, VA, VA, VA, VA, VA, VA, VA, CA, 13 },
            { 38, 39, 40, OC, 42, 43, 44, OC, 46, 47, PU },
            { 1, 2, 3, 4, OC, PU, 7, 8, OC, 10, 11 }
    };

    private int lastPrint = 0;

    private int jugadores[] = { 1, 1, 0, 0 };
    private int castigos[] = { 0, 0, 0, 0 };
    private int siguienteJugador = 0;

    public Oca() {

    }

    public void print() {
        System.out.println("Posición actual parchis: ");
        int _tablero[][];
        if (lastPrint == 0) {
            _tablero = tablero;
        } else {
            _tablero = tableroAscii;
        }
        this.lastPrint = (this.lastPrint + 1) % 2;

        // pintar tablero n casilla
        for (int i = 0; i < _tablero.length; i++) {
            for (int j = 0; j < _tablero[i].length; j++) {

                int nJugador = esJugador(tablero[i][j]);

                if (_tablero[i][j] == VA) {
                    System.out.print("   ");
                } else if (nJugador != -1) {
                    System.out.print("\u001B[31mJ" + nJugador + "\u001B[0m ");
                } else if (_tablero[i][j] == OC) {
                    System.out.print(" O ");
                } else if (_tablero[i][j] == DA) {
                    System.out.print(" D ");
                } else if (_tablero[i][j] == PU) {
                    System.out.print(" P ");
                } else if (_tablero[i][j] == CA) {
                    System.out.print(" C ");
                } else if (_tablero[i][j] == MU) {
                    System.out.print(" M ");
                } else {
                    System.out.printf("%2d ", _tablero[i][j]);
                }
            }
            System.out.println();

           
        }
         System.out.println("-------------------------------------------------");

            printExtraInfo();
    }

    private void printExtraInfo() {
       for(int i = 0; i < jugadores.length; i++) {
            System.out.print("Jugador " + (i + 1) + ": " + jugadores[i] + ", " + castigos[i]);
            System.out.println();
        }
       
        System.out.println("-------------------------------------------------");
    }

    private int esJugador(int i) {
        for (int j = 0; j < jugadores.length; j++) {
            if (i == jugadores[j]) {
                return j + 1;
            }
        }
        return -1;
    }

    public void actualiza(ResultadoTurno resultadoTurno) {
        // Actualiza la posicion de los jugadores
        for (int i = 0; i < resultadoTurno.posicionJugadores.length; i++) {
            this.jugadores[i] = resultadoTurno.posicionJugadores[i];
        }

        // Actualiza las penalizaciones de los jugadores
        for (int i = 0; i < resultadoTurno.penalizacionesJugadores.length; i++) {
            this.castigos[i] = resultadoTurno.penalizacionesJugadores[i];
        }

        // Actualiza el siguiente jugador
        this.siguienteJugador = resultadoTurno.siguienteJugador;
        
    }

}
