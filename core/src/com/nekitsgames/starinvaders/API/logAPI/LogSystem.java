/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/
package com.nekitsgames.starinvaders.API.logAPI;

import com.nekitsgames.starinvaders.API.SysAPI;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Log API
 *
 * @author Nikita Serba
 * @version 2.0
 * @since 1.3
 */
public class LogSystem {

    private Properties prop;
    private File logFile;
    private String log = "";

    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERR";
    public static final String FATAL = "FATAL";

    /**
     * Loading properties and creating file
     *
     * @since 1.3
     * @throws IOException if can't access properies files
     */
    public LogSystem() throws IOException {
        prop = new Properties();
        prop.load(new FileInputStream("properties/log.properties"));

        DateFormat dateFormat = new SimpleDateFormat((String) prop.get("log.save_to"));
        logFile = new File(SysAPI.getSettingsFolder() + "\\logs\\" + dateFormat.format(new Date()) + ".log");

        new File(SysAPI.getSettingsFolder() + "\\logs\\").mkdirs();

        if (logFile.exists())
            logFile.delete();


        Log("Initializing LogAPI", INFO);
    }


    /**
     * Logging
     *
     * @since 1.3
     * @param msg - message to log
     * @param type - log type (INFO, WARN, ERR, FATAL)
     */
    public void Log(String msg, String type) {
        DateFormat dateFormat = new SimpleDateFormat((String) prop.get("data.format"));
        Date date = new Date();
        String logStr = "{" + dateFormat.format(date) + "}[" + type + "] " + msg;
        if (type.equals(ERROR) || type.equals(FATAL))
            System.err.println(logStr);
        else
            System.out.println(logStr);

        if (type.equals(FATAL)) {
            try {
                save();
            } catch (IOException e) {

            }
            System.exit(-1);
        }

        log += logStr + "\n";

        dateFormat = null;
        date = null;
    }

    /**
     * Save logs to file
     *
     * @since 2.1
     * @throws IOException if can't access log file
     */
    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
        writer.write(log);
        writer.close();
    }

    /**
     * Cleaning
     *
     * @since 1.3
     */
    public void dispose() {
        Log("Disposing LogAPI", INFO);
        prop = null;
    }

}
