/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/

package com.nekitsgames.starinvaders.API;

import com.nekitsgames.starinvaders.API.logAPI.LogSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

/**
 * System API.
 *
 * Contains cross-platform methods
 *
 * @author Nikita Serba
 * @version 1.1
 * @since 1.3
 */
public class SysAPI {

    public static final int WINDOWS = 0;
    public static final int UNIX = 1;
    public static final int SOLARIS = 2;
    public static final int OTHER = 3;


    /**
     * Get os name
     *
     * @deprecated since 2.1, use getOs() instead
     * @since 1.3
     * @param log - log class
     * @return os code
     */
    @Deprecated
    public static int getOs(LogSystem log) {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if (OS.indexOf("win") >= 0) {
            return WINDOWS;
        } else if (OS.indexOf("sunos") >= 0) {
            return SOLARIS;
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
            return UNIX;
        } else {
            log.Log("Can't detect OS!, OS name: " + OS, LogSystem.WARN);
            return OTHER;
        }
    }

    /**
     * Get os name
     *
     * @since 1.3
     * @return os code
     */
    private static int getOs() {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if (OS.indexOf("win") >= 0) {
            return WINDOWS;
        } else if (OS.indexOf("sunos") >= 0) {
            return SOLARIS;
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
            return UNIX;
        } else {
            return OTHER;
        }
    }

    /**
     * Get settings folder by OS
     *
     * @deprecated since 2.1
     * @since 1.3
     * @param log - log class
     * @return full folder path
     * @throws IOException if can't access files on drive
     */
    @Deprecated
    public static String getSettingsFolder(LogSystem log) throws IOException {
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

    /**
     * Get settings folder by OS
     *
     * @since 1.3
     * @return full folder path
     * @throws IOException if can't access files on drive
     */
    public static String getSettingsFolder() throws IOException {
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
