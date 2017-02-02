package com.nekitsgames.starinvaders.classes;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Language {

    private String name;
    private String sym;
    private Properties prop;

    public Language(String sym) throws IOException {
        this.sym = sym;

        prop = new Properties();
        prop.load(new FileInputStream("properties/strings." + sym + ".properties"));

        this.name = prop.getProperty("lang.name");
    }

    public String getName() {
        return name;
    }

    public String getSym() {
        return sym;
    }

    public void dispose() {
        prop = null;
    }
}