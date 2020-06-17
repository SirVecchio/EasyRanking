package me.kaotich00.easyranking.api.service;

public interface TaskService {

    void stopEconomyTask();

    void stopDatabaseSyncTask();

    void scheduleEconomyTask();

    void scheduleDatabaseSyncTask();

}
