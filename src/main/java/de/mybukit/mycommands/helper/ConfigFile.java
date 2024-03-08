package de.mybukkit.mycommands.helper;

import java.util.HashMap;

public class ConfigFile
    extends SaveFile {

    public HashMap<String, String> map = new HashMap<>();


    public ConfigFile(String name, String path) {
        super(name, path);
    }


    public void load() {
        super.load();

        this.map.clear();
        for (String line : this.data) {

            String[] part = line.split("=");

            if (!this.map.containsKey(part[0])) {
                this.map.put(part[0], part[1]);
            }
        }
    }

    public void save() {
        this.data.clear();
        for (String key : this.map.keySet()) {
            this.data.add(key + "=" + this.map.get(key));
        }
        save(false);
    }


    public String get(String key, String defaultValue) {
        if (this.map.containsKey(key)) {
            return this.map.get(key);
        }

        this.map.put(key, defaultValue);
        return defaultValue;
    }


    public String get(String key) {
        if (this.map.containsKey(key)) {
            return this.map.get(key);
        }
        return null;
    }


    public boolean set(String key, String value) {
        if (this.map.containsKey(key)) {

            this.map.put(key, value);
            return true;
        }

        return false;
    }


    public boolean containsKey(String key) {
        return this.map.containsKey(key);
    }
}
