package me.kaotich00.easyranking.service;

import me.kaotich00.easyranking.api.service.TaskService;
import me.kaotich00.easyranking.task.EconomyBoardTask;
import me.kaotich00.easyranking.task.SyncDatabaseTask;
import org.bukkit.Bukkit;

public class ERTaskService implements TaskService {

    private static ERTaskService taskService;
    private int economyTaskId;
    private int syncDatabaseTaskId;

    private ERTaskService() {
        if (taskService != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.economyTaskId = EconomyBoardTask.scheduleEconomy();
        this.syncDatabaseTaskId = SyncDatabaseTask.scheduleDataPersistence();
    }

    public static ERTaskService getInstance() {
        if(taskService == null) {
            taskService = new ERTaskService();
        }
        return taskService;
    }

    @Override
    public void stopEconomyTask() {
        Bukkit.getScheduler().cancelTask(this.economyTaskId);
    }

    @Override
    public void stopDatabaseSyncTask() {
        Bukkit.getScheduler().cancelTask(this.syncDatabaseTaskId);
    }

    @Override
    public void scheduleEconomyTask() {
        this.economyTaskId = EconomyBoardTask.scheduleEconomy();
    }

    @Override
    public void scheduleDatabaseSyncTask() {
        this.syncDatabaseTaskId = SyncDatabaseTask.scheduleDataPersistence();
    }
}
