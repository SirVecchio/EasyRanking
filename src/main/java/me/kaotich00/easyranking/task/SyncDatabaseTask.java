package me.kaotich00.easyranking.task;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class SyncDatabaseTask {

    public static void scheduleDataPersistence() {
        FileConfiguration defaultConfig = Easyranking.getDefaultConfig();
        Long period = defaultConfig.getLong("sync_frequency") * 1200;

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Easyranking.getPlugin(Easyranking.class), () -> {
           BoardService boardService = ERBoardService.getInstance();
           boardService.saveBoardsToDatabase();
        }, period, period );
    }

}
