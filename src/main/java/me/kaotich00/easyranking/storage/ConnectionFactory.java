package me.kaotich00.easyranking.storage;

import me.kaotich00.easyranking.Easyranking;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {

    void init(Easyranking plugin);

    void shutdown() throws Exception;

    Connection getConnection() throws SQLException;

}
