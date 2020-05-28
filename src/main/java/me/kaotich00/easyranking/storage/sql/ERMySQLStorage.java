package me.kaotich00.easyranking.storage.sql;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.storage.sql.MySQLStorage;
import me.kaotich00.easyranking.storage.StorageFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ERMySQLStorage extends StorageFactory implements MySQLStorage {

    private String host, database, username, password;
    Easyranking plugin = Easyranking.getPlugin(Easyranking.class);

    public ERMySQLStorage(String host, String database, String username, String password) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    @Override
    public void initConnection() {
        super.initConnection();

        try {
            synchronized (this) {
                if( plugin.getConnection() != null && !plugin.getConnection().isClosed() ) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                plugin.setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database + "?useSSL=false", this.username, this.password));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Successfully connected to MySQL database");
            }
        } catch (SQLException throwables) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[EasyRanking] Critical error encountered while connecting to MySQL database");
            throwables.printStackTrace();
            plugin.disablePlugin();
        } catch (ClassNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[EasyRanking] Critical error encountered while connecting to MySQL database");
            e.printStackTrace();
            plugin.disablePlugin();
        }
    }

}
