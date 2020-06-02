package me.kaotich00.easyranking.storage.sql.mysql;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.storage.sql.MySQLStorage;
import me.kaotich00.easyranking.storage.ConnectionFactory;
import me.kaotich00.easyranking.storage.StorageCredentials;
import me.kaotich00.easyranking.storage.StorageFactory;

import me.kaotich00.easyranking.storage.hikari.HikariConnectionFactory;
import me.kaotich00.easyranking.storage.sql.reader.SchemaReader;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class ERMySQLStorage extends StorageFactory implements MySQLStorage {

    private static final String BOARD_INSERT_OR_UPDATE = "INSERT INTO easyranking_board(id,name,description,max_players,user_score_name,is_visible,is_deleted) VALUES (?,?,?,?,?,true,false) ON DUPLICATE KEY UPDATE name = ?, description = ?, max_players = ?, user_score_name =?";

    private StorageCredentials credentials;
    private ConnectionFactory connectionFactory;
    Easyranking plugin = Easyranking.getPlugin(Easyranking.class);

    public ERMySQLStorage(String host, String database, String username, String password) {
        credentials = new StorageCredentials(host, database, username, password);
        this.connectionFactory = new HikariConnectionFactory(credentials);
    }

    @Override
    public void initDatabase() {
        this.connectionFactory.init(Easyranking.getPlugin(Easyranking.class));
        executeSchema();
        /*try {
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
        }*/
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

                try (Connection connection = connectionFactory.getConnection()) {
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



    @Override
    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.connectionFactory.getConnection();
    }

    @Override
    public void saveBoards() {

    }

}
