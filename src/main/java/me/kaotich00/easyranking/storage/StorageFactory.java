package me.kaotich00.easyranking.storage;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.storage.sql.mysql.ERMySQLStorage;
import org.bukkit.configuration.file.FileConfiguration;

public class StorageFactory {

    public static StorageFactory getDefaultStorage() {
        FileConfiguration defaultConfig = Easyranking.getDefaultConfig();
        String host = defaultConfig.getString("address");
        String database = defaultConfig.getString("database");
        String username = defaultConfig.getString("username");
        String password = defaultConfig.getString("password");

        return new ERMySQLStorage(host, database, username, password);
    }

    public static StorageFactory getConfiguredStorage() {
        return null;
    }

    public void initDatabase() {}

}
