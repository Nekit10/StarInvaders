/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/
package com.nekitsgames.starinvaders.API.logAPI;

import com.nekitsgames.starinvaders.API.SysAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Log API
 *
 * @author Nikita Serba
 * @version 1.0
 * @since 1.3
 */
public class LogSystem {

    private PrintWriter printer;
    private Properties prop;

    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERR";

    /**
     * Loading properties and creating class
     *
     * @since 1.3
     * @throws IOException if can't access properies files
     */
    public LogSystem() throws IOException {
        prop = new Properties();
        prop.load(new FileInputStream("properties/log.properties"));
        printer = new PrintWriter(SysAPI.getSettingsFolder() + prop.get("log.save_to"));
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
        DateFormat dateFormat = new SimpleDateFormat(prop.getProperty("data.format"));
        Date date = new Date();
        String logStr = "{" + dateFormat.format(date) + "}[" + type + "] " + msg;
        if (type == ERROR)
            System.err.println(logStr);
        else
            System.out.println(logStr);
        printer.println(logStr);

        dateFormat = null;
        date = null;
    }


    /**
     * Cleaning
     *
     * @since 1.3
     */
    public void dispose() {
        Log("Disposing LogAPI", INFO);
        printer.close();
        printer = null;
        prop = null;
    }

}
