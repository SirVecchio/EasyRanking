package me.kaotich00.easyranking.storage;

import me.kaotich00.easyranking.api.storage.StorageFactory;
import me.kaotich00.easyranking.storage.sql.ERMySQLStorage;

public class ERStorageFactory implements StorageFactory {

    @Override
    public static StorageFactory getDefaultStorage() {
        return new ERMySQLStorage();
    }

    @Override
    public static StorageFactory getConfiguredStorage() {
        return null;
    }

}
