/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/

package com.nekitsgames.starinvaders.API.settingsApi;

import com.nekitsgames.starinvaders.API.SysAPI;
import com.nekitsgames.starinvaders.API.logAPI.LogSystem;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Setting API
 *
 * @deprecated since 2.1, use Settings2API instead
 * @author Nikita Serba
 * @version 1.0
 * @since 1.3
 */
@Deprecated
public class SettingsSystem {

    private String name;
    private LogSystem log;

    /**
     * Create settings file and init class
     *
     * @deprecated since 2.1, use Settings2API.Settings2API & Settings2API.load instead
     * @param name - settings file name
     * @since 1.3
     * @param log - log class
     * @throws IOException if can't access log file
     */
    @Deprecated
    public SettingsSystem(String name, LogSystem log) throws IOException {
        this.name = SysAPI.getSettingsFolder() + name + ".json";
        this.log = log;

        log.Log("Initializing SettingsAPI, file name: " + name, LogSystem.INFO);
    }

    /**
     * Set property
     *
     * @deprecated since 2.1, use Settings2API.set instead
     * @param key - key
     * @param value - value to set
     * @since 1.3
     * @throws IOException if can't write to file
     */
    @Deprecated
    public void setProperty(String key, Object value) throws IOException {
        JSONObject jsonObject = readJSONObject();
        jsonObject.put(key, value);
        saveJsonObject(jsonObject);
    }

    /**
     * Get value by key
     *
     * @deprecated since 2.1, use Settings2API.get instead
     * @since 1.3
     * @param key - key
     * @param def - default value
     * @return value
     */
    @Deprecated
    public Object get(String key, Object def) {
        JSONObject jsonObject = readJSONObject();
        if (!jsonObject.isNull(key))
            return jsonObject.get(key);
        else
            return def;
    }

    /**
     * Reads JSON file
     *
     * @deprecated since 2.1
     * @since 1.3
     * @return JSONObject of settings file
     */
    @Deprecated
    private JSONObject readJSONObject() {
        try {
            return new JSONObject(new Scanner(new File(name)).useDelimiter("\\Z").next());
        } catch (FileNotFoundException e) {
            log.Log("No settings file! Creating new...", LogSystem.INFO);
            return new JSONObject();
        }
    }

    /**
     * Saves settings to file
     *
     * @deprecated since 2.1, use Settings2API.save instead
     * @since 1.3
     * @param obj - JSONObject to save
     * @throws IOException if can't access file
     */
    @Deprecated
    private void saveJsonObject(JSONObject obj) throws IOException {
        File file = new File(name);
        file.delete();
        file.createNewFile();

        PrintWriter printer = new PrintWriter(name);
        printer.println(obj.toString());
        printer.close();

        printer = null;
        file = null;
    }

    /**
     * Clean
     *
     * @since 1.3
     * @deprecated since 2.1
     */
    @Deprecated
    public void dispose() {
        log.Log("Disposing SettingsSystem", LogSystem.INFO);
        log = null;
    }

}
