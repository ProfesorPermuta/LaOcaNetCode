package com.example.parchis_ciente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.example.sharedData.ResultadoTurno;
import com.google.gson.Gson;

public class Reader implements Runnable {

    Socket socket;

    Oca oca;
    Log log;

    public Reader(Socket socket, Oca oca, Log log) {
        this.socket = socket;
        this.oca = oca;
        this.log = log;
    }

    @Override
    public void run() {
        String line;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            log.addLog("Esperando mensajes del servidor...");
            while ((line = in.readLine()) != null) {
                // Compruebo el tipo de mensaje
                log.addLog(line);

                String tipoMensaje = line.split(":")[0];
                if (!tipoMensaje.equals("TUR")) {
                    log.addLog("Received: " + line);
                    continue;
                }

                // Parseo jsonString a ResultadoTurno
                String objectInfo = line.substring(4);
                ResultadoTurno resultadoTurno = ResultadoTurno.fromJsonString(objectInfo);

                oca.actualiza(resultadoTurno);
            }
            log.addLog("Fin de la partida");
        } catch (IOException e) {
            log.addLog("Error hilo de escucha");
            e.printStackTrace();
            
        } catch (Exception e) {
            log.addLog("Error en el cliente");
            e.printStackTrace();
        }
    }

}
