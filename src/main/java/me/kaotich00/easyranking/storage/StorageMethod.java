package me.kaotich00.easyranking.storage;

import me.kaotich00.easyranking.Easyranking;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

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

    void deleteBoard(String boardId);

    void deleteUserScores(UUID player);

    void clearBoardsData();

}
