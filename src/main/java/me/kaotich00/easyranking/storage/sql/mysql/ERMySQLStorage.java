package me.kaotich00.easyranking.storage.sql.mysql;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.storage.sql.MySQLStorage;
import me.kaotich00.easyranking.storage.StorageCredentials;
import me.kaotich00.easyranking.storage.StorageFactory;

import me.kaotich00.easyranking.storage.sql.reader.SchemaReader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class ERMySQLStorage extends StorageFactory implements MySQLStorage {

    private StorageCredentials credentials;
    Easyranking plugin = Easyranking.getPlugin(Easyranking.class);

    public ERMySQLStorage(String host, String database, String username, String password) {
        credentials = new StorageCredentials(host, database, username, password);
    }

    @Override
    public void initDatabase() {
        super.initDatabase();

        try {
            synchronized (this) {
                if( plugin.getConnection() != null && !plugin.getConnection().isClosed() ) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                plugin.setConnection(credentials.toConnection());
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[EasyRanking]" + ChatColor.RESET + " Successfully connected to MySQL database");

                executeSchema();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[EasyRanking] Critical exception encountered while connecting to MySQL database. Make sure the database credentials are configured correctly.");
            throwables.printStackTrace();
            plugin.disablePlugin();
        }
    }

    public void executeSchema() {
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                List<String> statements = null;

                String schemaFileName = "me/kaotich00/easyranking/schema/mysql.schema.sql";
                try (InputStream is = plugin.getResource(schemaFileName)) {
                    if (is == null) {
                        throw new IOException("Couldn't locate schema file for MySQL");
                    }

                    statements = SchemaReader.getStatements(is).stream()
                            .collect(Collectors.toList());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (Connection connection = plugin.getConnection()) {
                    boolean utf8mb4Unsupported = false;

                    try (Statement s = connection.createStatement()) {
                        for (String query : statements) {
                            s.addBatch(query);
                        }

                        try {
                            s.executeBatch();
                        } catch (BatchUpdateException e) {
                            if (e.getMessage().contains("Unknown character set")) {
                                utf8mb4Unsupported = true;
                            } else {
                                throw e;
                            }
                        }
                    }

                    // try again
                    if (utf8mb4Unsupported) {
                        try (Statement s = connection.createStatement()) {
                            for (String query : statements) {
                                s.addBatch(query.replace("utf8mb4", "utf8"));
                            }

                            s.executeBatch();
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        };
        task.runTaskAsynchronously(plugin);
    }

}
