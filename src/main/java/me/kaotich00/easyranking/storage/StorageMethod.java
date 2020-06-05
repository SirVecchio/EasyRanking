package me.kaotich00.easyranking.storage;

import me.kaotich00.easyranking.Easyranking;

import java.sql.Connection;
import java.sql.SQLException;

public interface StorageMethod {

    Easyranking getPlugin();

    void init();

    void shutdown();

    void prepareDatabaseAndLoadData();

    Connection getConnection() throws SQLException;

    void loadBoards();

    void loadUserData();

    void saveBoards();

    void saveUserData();

    void saveBoardRewards();

    void loadBoardRewards();

}
