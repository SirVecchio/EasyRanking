package me.kaotich00.easyranking.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StorageCredentials {

    private String host, database, username, password;

    public StorageCredentials(String host, String database, String username, String password) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return this.host;
    }

    public String getDatabase() {
        return this.database;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Connection toConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database + "?useSSL=false", this.username, this.password);
    }

}
