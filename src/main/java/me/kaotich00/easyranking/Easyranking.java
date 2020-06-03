package me.kaotich00.easyranking;

import me.kaotich00.easyranking.command.EasyRankingCommand;
import me.kaotich00.easyranking.listener.board.KilledMobsListener;
import me.kaotich00.easyranking.listener.board.KilledPlayersListener;
import me.kaotich00.easyranking.listener.board.OresMinedListener;
import me.kaotich00.easyranking.listener.gui.reward.GUIRewardListener;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.service.ERRewardService;
import me.kaotich00.easyranking.storage.StorageFactory;
import me.kaotich00.easyranking.storage.sql.mysql.MySQLStorageFactory;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public final class Easyranking extends JavaPlugin {

    public StorageFactory storage;
    static FileConfiguration defaultConfig;
    private Connection connection;
    public static Economy economyService;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Loading configuration...");
        loadConfiguration();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Initializing database...");
        initStorage();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Registering commands...");
        registerCommands();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Registering listeners...");
        registerListeners();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Registering services...");
        registerServices();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Registering economy...");
        if (!setupEconomy()) {
            this.getLogger().severe("This plugin needs Vault and an Economy plugin in order to function!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        MySQLStorageFactory storageFactory = StorageFactory.getStorage();
        try {
            storageFactory.saveBoards();
            storageFactory.saveUserData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void disablePlugin() {
        this.getPluginLoader().disablePlugin(this);
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        defaultConfig = getConfig();
    }

    public void registerCommands() {
        getCommand("er").setExecutor(new EasyRankingCommand());
    }

    public void registerServices() {
        ERRewardService.getInstance();
        ERBoardService.getInstance();
    }

    public void initStorage() {
        storage = StorageFactory.getStorage();
        storage.initDatabase();
    }

    public void registerListeners(){
        getServer().getPluginManager().registerEvents(new GUIRewardListener(),this);
        getServer().getPluginManager().registerEvents(new KilledMobsListener(),this);
        getServer().getPluginManager().registerEvents(new KilledPlayersListener(),this);
        getServer().getPluginManager().registerEvents(new OresMinedListener(),this);
    };

    public boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economyService = rsp.getProvider();
        return economyService != null;
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

    public static Economy getEconomy() {
        return economyService;
    }

}