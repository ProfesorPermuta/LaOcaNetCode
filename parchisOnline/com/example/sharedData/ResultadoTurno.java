package com.example.sharedData;

import com.google.gson.Gson;

public class ResultadoTurno {

    public int tirada;

    public int posicionJugadores[];
    public int penalizacionesJugadores[];
    public int siguienteJugador;

    public int ultimaTirada;


    static public final String toJsonString(ResultadoTurno resultadoTurno) {
        try {
            return new Gson().toJson(resultadoTurno);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    static public final ResultadoTurno fromJsonString(String json) {
        try {
            return new Gson().fromJson(json, ResultadoTurno.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}


