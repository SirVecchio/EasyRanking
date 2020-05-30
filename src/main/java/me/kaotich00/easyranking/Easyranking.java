package me.kaotich00.easyranking;

import me.kaotich00.easyranking.command.EasyRankingCommand;
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
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Loading configuration...");
        loadConfiguration();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Registering commands...");
        registerCommands();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Initializing database...");
        initStorage();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void disablePlugin() {
        this.getPluginLoader().disablePlugin(this);
    }

    public void registerCommands() {
        getCommand("er").setExecutor(new EasyRankingCommand());
    }

    public void initStorage() {
        storage = StorageFactory.getDefaultStorage();
        storage.initDatabase();
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        defaultConfig = getConfig();
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