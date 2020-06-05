package me.kaotich00.easyranking.storage;

import me.kaotich00.easyranking.Easyranking;

public class Storage {

    private final Easyranking plugin;
    private final StorageMethod storageMethod;

    public Storage(Easyranking plugin, StorageMethod storageMethod) {
        this.plugin = plugin;
        this.storageMethod = storageMethod;
    }

    public StorageMethod getStorageMethod() {
        return this.storageMethod;
    }

    public void init() {
        try {
            this.storageMethod.init();
        } catch (Exception e) {
            this.plugin.getLogger().severe("Failed to init storage implementation");
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            this.storageMethod.shutdown();
        } catch (Exception e) {
            this.plugin.getLogger().severe("Failed to shutdown storage implementation");
            e.printStackTrace();
        }
    }

}
