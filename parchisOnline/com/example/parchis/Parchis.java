package com.example.parchis;

import com.example.sharedData.ResultadoTurno;

public class Parchis {

    // Casillas especiales
    private static final int OCAS[] = { 5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59, 63 };
    private static final int DADOS[] = { 36, 53 };
    private static final int PUENTES[] = { 6, 12 };
    private static final int CASTIGOS[] = { 19, 31, 48 };
    private static final int MUERTE[] = { 58 };

    // Posicion actual de los jugadores
    private int jugadores[] = { 1, 1, 1, 1 };
    private int castigos[] = { 0, 0, 0, 0 };

    private final int MAX_JUGADORES = 4;
    private int nJugadores = 0;
    private int jugadorActual = 0;

    private GameState estado = GameState.INITIALIZING;

    public Parchis() {

        estado = GameState.JOINING;
    }

    private ResultadoTurno ejecutaTurno() {

        ResultadoTurno res = new ResultadoTurno();
        int posicionInicial = jugadores[jugadorActual];
        int nuevaPosicion = jugadores[jugadorActual];
        int rnd = 0;

        boolean pasarTurno = true;

        if (castigos[jugadorActual] > 0) {
            // Actualizo el castigo del jugador
            castigos[jugadorActual] = castigos[jugadorActual];
        }

        if (castigos[jugadorActual] == 0) {

            // Tirar el dado
            rnd = (int) (Math.random() * 6) + 1;

            

            nuevaPosicion = posicionInicial + rnd;

            // Comprobar si la nueva posicion es una casilla especial
            int especialExtra = -1;
            if (nuevaPosicion == 63) {
                // El jugadore gana

                estado = GameState.FINISHED;
                pasarTurno = false;

            }
            if ((especialExtra = comprobarEspecial(nuevaPosicion, OCAS)) != -1) {
                // Salto a la siguiente oca y repito turno
                nuevaPosicion = OCAS[especialExtra + 1];
                pasarTurno = false;

            } else if ((especialExtra = comprobarEspecial(nuevaPosicion, DADOS)) != -1) {
                // Salto a la siguiente casilla de dados y repito turno
                nuevaPosicion = DADOS[(especialExtra + 1) % DADOS.length];
                pasarTurno = false;

            } else if ((especialExtra = comprobarEspecial(nuevaPosicion, PUENTES)) != -1) {
                // Salto a la siguiente puente y repito turno
                nuevaPosicion = PUENTES[(especialExtra + 1) % PUENTES.length];
                pasarTurno = false;

            } else if ((especialExtra = comprobarEspecial(nuevaPosicion, CASTIGOS)) != -1) {
                // Aniado casitigo al jugador y paso turno
                castigos[jugadorActual] = especialExtra;
            } else if ((especialExtra = comprobarEspecial(nuevaPosicion, MUERTE)) != -1) {
                // El jugador vuelve a la casilla de salida
                nuevaPosicion = 1;
            }
        }


        //Actualizo el estado del juego
        // Actualizo la posicion del jugador
        jugadores[jugadorActual] = nuevaPosicion;

        // Completo el objeto de resultado
        res.tirada = rnd;

        res.posicionJugadores = jugadores;
        res.penalizacionesJugadores = castigos;

        if (pasarTurno)
            // Paso el turno al siguiente jugador
            jugadorActual = (jugadorActual + 1) % nJugadores;

        res.siguienteJugador = jugadorActual;
        res.ultimaTirada = rnd;

        return res;
    }

    // Busca en el array de posiciones especiales si la nueva posicion es una de
    // ellas
    // Devuelve el indice de la posicion especial o -1 si no es una posicion
    // especial
    private int comprobarEspecial(int nuevaPosicion, int[] especiales) {

        for (int i = 0; i < especiales.length; i++) {
            if (nuevaPosicion == especiales[i]) {
                return i;
            }
        }
        return -1;
    }

    // aniade un jugador a la partida
    // Devuelve el id del jugador o -1 si no se puede añadir
    public int addJugador() {
        if (nJugadores < MAX_JUGADORES && estado == GameState.JOINING) {
            jugadores[nJugadores] = 1;
            nJugadores++;
            return nJugadores - 1;
        } else {
            return -1; // Error: no se puede añadir más jugadores
        }
    }

    public void iniciarJuego(int idJugador) {
        if (nJugadores >= 2) {
            System.out.println("El jugador " + idJugador + " ha iniciado el juego con " + nJugadores + " jugadores.");
            estado = GameState.IN_PROGRESS;
        } else {
            throw new IllegalStateException("No hay suficientes jugadores para iniciar el juego.");
        }
    }

    public int getnJugadores() {

        return nJugadores;
    }

    public ResultadoTurno jugarTurno(int idJugador) {

        // Comprueba si el jugador es el turno
        if (idJugador != jugadorActual) {
            throw new IllegalStateException("No es el turno del jugador " + idJugador);
        }
        // Ejecuta el turno y devuelve el resultado
        ResultadoTurno res = ejecutaTurno();

        return res;
    }

    public void cancelarJuego(int idJugador) {

        estado = GameState.CANCELED;
    }

    public int[] getJugadores() {
        return jugadores;
    }

    public int[] getCastigos() {
        return castigos;
    }

}
