package me.kaotich00.easyranking.api.storage.sql;

import me.kaotich00.easyranking.storage.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public interface MySQLStorage {

    void initDatabase();

    void executeSchema();

    ConnectionFactory getConnectionFactory();

    Connection getConnection() throws SQLException;

    void saveBoards();

}
