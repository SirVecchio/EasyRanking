package me.kaotich00.easyranking;

import me.kaotich00.easyranking.storage.StorageFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public final class Easyranking extends JavaPlugin {

    public StorageFactory storage;
    static FileConfiguration defaultConfig;
    private Connection connection;

    @Override
    public void onEnable() {
        loadConfiguration();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Successfully loaded configuration");

        defaultConfig = getConfig();

        storage = StorageFactory.getDefaultStorage();
        storage.initDatabase();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void disablePlugin() {
        this.getPluginLoader().disablePlugin(this);
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    public static FileConfiguration getDefaultConfig() {
        return defaultConfig;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}