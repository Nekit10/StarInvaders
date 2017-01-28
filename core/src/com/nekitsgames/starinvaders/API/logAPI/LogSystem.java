package com.nekitsgames.starinvaders.API.logAPI;

import com.nekitsgames.starinvaders.API.SysAPI;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogSystem {

    private PrintWriter printer;

    public static final String INFO = "INFO";
    public static final String WARBN = "WARN";
    public static final String ERROR = "ERR";

    public LogSystem() throws IOException {
        printer = new PrintWriter(SysAPI.getSettingsFolder() + "Latest.log");
    }

    public void dispose () {
        printer.close();
        printer = null;
    }

    public void Log (String msg, String type) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String logStr = "{" + dateFormat.format(date) + "}[" + type + "] " + msg;
        if (type == ERROR)
            System.err.println(logStr); else
            System.out.println(logStr);
        printer.println(logStr);
    }

}
