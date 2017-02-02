package com.nekitsgames.starinvaders.API.settingsApi;

import com.nekitsgames.starinvaders.API.SysAPI;
import com.nekitsgames.starinvaders.API.logAPI.LogSystem;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SettingsSystem {

    private String name;
    private LogSystem log;

    public SettingsSystem(String name, LogSystem log) throws IOException {
        this.name = SysAPI.getSettingsFolder() + name + ".json";
        this.log = log;

        log.Log("Initializing SettingsAPI, file name: " + name, LogSystem.INFO);
    }

    public void setProperty(String key, Object value) throws IOException {
        JSONObject jsonObject = readJSONObject();
        jsonObject.put(key, value);
        saveJsonObject(jsonObject);
    }

    public Object get(String key, Object def) {
        JSONObject jsonObject = readJSONObject();
        if (!jsonObject.isNull(key))
            return jsonObject.get(key);
        else
            return def;
    }

    private JSONObject readJSONObject() {
        try {
            return new JSONObject(new Scanner(new File(name)).useDelimiter("\\Z").next());
        } catch (FileNotFoundException e) {
            log.Log("No settings file! Creating new...", LogSystem.INFO);
            return new JSONObject();
        }
    }

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

    public void dispose() {
        log.Log("Disposing SettingsSystem", LogSystem.INFO);
        log = null;
    }

}
