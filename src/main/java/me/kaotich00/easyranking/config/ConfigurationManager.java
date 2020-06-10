package me.kaotich00.easyranking.config;

import me.kaotich00.easyranking.Easyranking;

public class ConfigurationManager {

    private static ConfigurationManager configInstance;
    private Easyranking plugin;

    private ConfigurationManager(Easyranking plugin) {
        if (configInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.plugin = plugin;
    }

    public static ConfigurationManager getInstance() {
        if(configInstance == null) {
            configInstance = new ConfigurationManager(Easyranking.getPlugin(Easyranking.class));
        }
        return configInstance;
    }

    public void reloadDefaultConfig() {
        plugin.reloadDefaultConfig();
    }

}
