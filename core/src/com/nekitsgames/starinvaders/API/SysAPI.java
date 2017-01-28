package com.nekitsgames.starinvaders.API;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.regex.Pattern;

public class SysAPI {

    public static final int WINDOWS = 0;
    public static final int UNIX = 1;
    public static final int SOLARIS = 2;
    public static final int OTHER = 3;

    public static int getOs () {
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

    public static String getSettingsFolder () throws IOException {
        String pathStr;

        switch (getOs()) {
            case WINDOWS:
                pathStr = System.getenv("AppData") + "/NG Tech/Star Invaders/2.0/";
                break;
            default:
                pathStr = System.getProperty("user.home");
        }

        Path path = Paths.get(pathStr);
        Files.createDirectories(path);

        return pathStr;
    }

}
