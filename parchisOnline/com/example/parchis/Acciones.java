package com.example.parchis;

public enum Acciones {

    // Enum constants with associated messages
    INICIO_JUEGO("ini"), // Inicia el juego con el número de jugadores actual
    JUGAR_TURNO("play"), // Jugar un turno del jugador que manda el mensaje
    CANCELAR_JUEGO("cancel"); // Acaba la partida de forma forzada

    private final String mensaje; // Mensaje que manda el cliente

    Acciones(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

}
