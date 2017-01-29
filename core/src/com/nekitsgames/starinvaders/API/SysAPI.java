package com.nekitsgames.starinvaders.API;

import com.nekitsgames.starinvaders.API.logAPI.LogSystem;
import sun.rmi.runtime.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Pattern;

public class SysAPI {

    public static final int WINDOWS = 0;
    public static final int UNIX = 1;
    public static final int SOLARIS = 2;
    public static final int OTHER = 3;

    public static int getOs (LogSystem log) {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if (OS.indexOf("win") >= 0) {
            return WINDOWS;
        } else if (OS.indexOf("sunos") >= 0) {
            return SOLARIS;
        }  else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
            return UNIX;
        } else {
            log.Log("Can't detect OS!, OS name: " + OS, LogSystem.WARN);
            return OTHER;
        }
    }

    private static int getOs () {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if (OS.indexOf("win") >= 0) {
            return WINDOWS;
        } else if (OS.indexOf("sunos") >= 0) {
            return SOLARIS;
        }  else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
            return UNIX;
        } else {
            return OTHER;
        }
    }

    public static String getSettingsFolder (LogSystem log) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("properties/main.properties"));
        String path = prop.getProperty("dir.data");

        String pathStr;

        switch (getOs(log)) {
            case WINDOWS:
                pathStr = System.getenv("AppData") + path;
                break;
            default:
                pathStr = System.getProperty("user.home") + path;
        }

        Path pathP = Paths.get(pathStr);
        Files.createDirectories(pathP);

        prop = null;
        pathP = null;

        return pathStr;
    }



    public static String getSettingsFolder () throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("properties/main.properties"));
        String path = prop.getProperty("dir.data");

        String pathStr;

        switch (getOs()) {
            case WINDOWS:
                pathStr = System.getenv("AppData") + path;
                break;
            default:
                pathStr = System.getProperty("user.home") + path;
        }

        Path pathP = Paths.get(pathStr);
        Files.createDirectories(pathP);

        prop = null;
        pathP = null;

        return pathStr;
    }

}
