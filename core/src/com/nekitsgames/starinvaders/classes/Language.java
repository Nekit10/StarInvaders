/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/

package com.nekitsgames.starinvaders.classes;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Language class
 *
 * @author Nikita Serba
 * @version 1.0
 * @since 1.3
 */
public class Language {

    private String name;
    private String sym;
    private Properties prop;

    /**
     * Init language
     *
     * @since 1.3
     * @param sym - language symbol
     * @throws IOException if can't acess language file
     */
    public Language(String sym) throws IOException {
        this.sym = sym;

        prop = new Properties();
        prop.load(new FileInputStream("properties/strings." + sym + ".properties"));

        this.name = prop.getProperty("lang.name");
    }

    /**
     * Get language name
     *
     * @since 1.3
     * @return language name
     */
    public String getName() {
        return name;
    }

    /**
     * Get language symbol
     *
     * @since 1.3
     * @return language symbol
     */
    public String getSym() {
        return sym;
    }

    /**
     * Clean
     *
     * @since 1.3
     */
    public void dispose() {
        prop = null;
    }
}