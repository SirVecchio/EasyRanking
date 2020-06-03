package me.kaotich00.easyranking.storage;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.storage.sql.mysql.MySQLStorageFactory;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.SQLException;

public class StorageFactory {

    public static MySQLStorageFactory storage;

    public static MySQLStorageFactory getStorage() {
        if( storage != null ) {
            return storage;
        }
        FileConfiguration defaultConfig = Easyranking.getDefaultConfig();
        String host = defaultConfig.getString("address");
        String database = defaultConfig.getString("database");
        String username = defaultConfig.getString("username");
        String password = defaultConfig.getString("password");

        storage = new MySQLStorageFactory(host, database, username, password);
        return storage;
    }

    public void initDatabase() {}

    public void saveBoards() throws SQLException {}

}
