package me.kaotich00.easyranking.storage;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.storage.sql.SqlStorage;
import me.kaotich00.easyranking.storage.sql.hikari.MySQLConnectionFactory;
import me.kaotich00.easyranking.storage.util.StorageCredentials;
import org.bukkit.configuration.file.FileConfiguration;

public class StorageFactory {

    public static Storage storage;

    public static Storage getInstance() {
        if( storage != null ) {
            return storage;
        }
        storage = new Storage(Easyranking.getPlugin(Easyranking.class), getStorageFromConfig());
        return storage;
    }

    private static StorageMethod getStorageFromConfig() {
        FileConfiguration defaultConfig = Easyranking.getDefaultConfig();
        String host = defaultConfig.getString("address");
        String database = defaultConfig.getString("database");
        String username = defaultConfig.getString("username");
        String password = defaultConfig.getString("password");
        StorageCredentials credentials = new StorageCredentials(host,database,username,password);

        return new SqlStorage(Easyranking.getPlugin(Easyranking.class), new MySQLConnectionFactory(credentials));

        /*switch(defaultConfig.getString("storage")) {
            case StorageTypes.MYSQL:
                return new SqlStorage(Easyranking.getPlugin(Easyranking.class), new MySQLConnectionFactory(credentials));
            default:
                return new SqlStorage(Easyranking.getPlugin(Easyranking.class), new MySQLConnectionFactory(credentials));
        }*/
    }

}
