/*
* Copyright (c) 20016 - 2017, NG Tech and/or its affiliates. All rights reserved.
* GNI GPL v3 licence . Use is subject to license terms
*/

package com.nekitsgames.starinvaders.API.settingsApi;

import com.nekitsgames.starinvaders.API.SysAPI;
import com.nekitsgames.starinvaders.classes.Exceptions.SettingsAccessException;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.AccessDeniedException;

/**
 * New Settings2API: free and fast access to settings files
 *
 * @author Nikita Serba
 * @version 1.0
 * @since 2.1
 */
public class Settings2API {

    private JSONObject cache;
    private File settingsFile;

    /**
     * Load settings file to cache
     *
     * @since 2.1
     * @param fileName - settings file's name
     * @throws SettingsAccessException if can't create/access settings file
     * @throws AccessDeniedException if can't write to settings file
     * @throws FileNotFoundException if can't find file
     */
    public void load (String fileName) throws SettingsAccessException, AccessDeniedException, FileNotFoundException {
        if (fileName.isEmpty())
            throw new NullPointerException("File name can't be empty");

        String file = fileName;

        try {
            file = SysAPI.getSettingsFolder() + fileName + ".json";
            new File(SysAPI.getSettingsFolder()).mkdirs();
            settingsFile = new File(file);
        } catch (IOException e) {
            throw new SettingsAccessException("Can't access/create settings file: " + file, e.getCause());
        }

        if (settingsFile.exists() && !settingsFile.canWrite())
            throw  new AccessDeniedException(file);

        try {
            if (settingsFile.exists()) {
                String jsonSource = "";

                BufferedReader reader = new BufferedReader(new FileReader(settingsFile));
                String crLine;

                while ((crLine = reader.readLine()) != null) {
                    jsonSource += "\n" + crLine;
                }
                if (!jsonSource.isEmpty())
                    cache = new JSONObject(jsonSource);
                else cache = new JSONObject();
            } else {
                cache = new JSONObject();
            }
        } catch (IOException e) {
            throw new SettingsAccessException("Can't access/create settings file: " + file, e.getCause());
        }

    }

    /**
     * Get value by key
     *
     * @since 2.1
     * @param key - key
     * @param def - default value
     * @return value by key
     */
    public Object get (String key, Object def) {
        if (key.isEmpty())
            throw new NullPointerException("Key can't be null");
        if (cache.isNull(key))
            return def; else return cache.get(key);
    }

    /**
     * Set value by key
     *
     * @since 2.1
     * @param key - key
     * @param value - value to set
     */
    public void set (String key, Object value) {
        if (key.isEmpty())
            throw new NullPointerException("Key can't be null");

        cache.put(key, value);
    }

}
