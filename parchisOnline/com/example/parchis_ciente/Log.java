package com.example.parchis_ciente;

import java.util.List;

public class Log {


    private List<String> logList;

    public Log() {
        this.logList = new java.util.ArrayList<>();
    }

    public void addLog(String log) {
        logList.add(log);
    }

    public void clearLog() {
        logList.clear();
    }

    public void printLog(int n) {
    
        if (n > logList.size()) {
            n = logList.size();
        }
        for (int i = logList.size() - 1; i > logList.size()-n - 1; i--) {
            System.out.println(logList.size() - i + ": " + logList.get(i));
        }
    }

}
