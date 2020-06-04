package me.kaotich00.easyranking.storage.sql.mysql;

import com.google.gson.Gson;
import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.data.UserData;
import me.kaotich00.easyranking.api.reward.Reward;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.reward.types.ERItemReward;
import me.kaotich00.easyranking.reward.types.ERMoneyReward;
import me.kaotich00.easyranking.reward.types.ERTitleReward;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.service.ERRewardService;
import me.kaotich00.easyranking.storage.ConnectionFactory;
import me.kaotich00.easyranking.storage.StorageCredentials;
import me.kaotich00.easyranking.storage.StorageFactory;

import me.kaotich00.easyranking.storage.hikari.HikariConnectionFactory;
import me.kaotich00.easyranking.storage.sql.reader.SchemaReader;
import me.kaotich00.easyranking.utils.JsonUtil;
import me.kaotich00.easyranking.utils.SerializationUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class MySQLStorageFactory extends StorageFactory {

    private static final String BOARD_INSERT_OR_UPDATE = "INSERT INTO easyranking_board(`id`,`name`,`description`,`max_players`,`user_score_name`,`is_visible`,`is_deleted`) VALUES (?,?,?,?,?,true,false) ON DUPLICATE KEY UPDATE `id`=`id`";
    private static final String BOARD_SELECT = "SELECT * FROM easyranking_board WHERE is_deleted = false";
    private static final String BOARD_ITEM_REWARD_DELETE_PREVIOUS = "DELETE FROM easyranking_item_reward WHERE id_board = ?";
    private static final String BOARD_ITEM_REWARD_INSERT_OR_UPDATE = "INSERT INTO easyranking_item_reward(`id_board`,`rank_position`,`item_type`) VALUES (?,?,?)";
    private static final String BOARD_ITEM_REWARD_SELECT = "SELECT * FROM easyranking_item_reward";
    private static final String BOARD_MONEY_REWARD_INSERT_OR_UPDATE = "INSERT INTO easyranking_money_reward(`id_board`,`rank_position`,`amount`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `amount` = ?";
    private static final String BOARD_MONEY_REWARD_SELECT = "SELECT * FROM easyranking_money_reward";
    private static final String BOARD_TITLE_REWARD_INSERT_OR_UPDATE = "INSERT INTO easyranking_title_reward(`id_board`,`rank_position`,`title`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `title` = ?";
    private static final String BOARD_TITLE_REWARD_SELECT = "SELECT * FROM easyranking_title_reward";

    private static final String USER_INSERT_OR_UPDATE = "INSERT INTO easyranking_user(`uuid`,`nickname`) VALUES (?,?) ON DUPLICATE KEY UPDATE `uuid`=`uuid`";

    private static final String USER_SCORE_INSERT_OR_UPDATE = "INSERT INTO easyranking_user_score(`id_user`,`id_board`,`amount`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `amount` = ?";
    private static final String USER_DATA_SELECT = "SELECT * FROM easyranking_user_score";

    private StorageCredentials credentials;
    private ConnectionFactory connectionFactory;
    Easyranking plugin = Easyranking.getPlugin(Easyranking.class);

    public MySQLStorageFactory(String host, String database, String username, String password) {
        credentials = new StorageCredentials(host, database, username, password);
        this.connectionFactory = new HikariConnectionFactory(credentials);
    }

    @Override
    public void initDatabase() {
        this.connectionFactory.init(Easyranking.getPlugin(Easyranking.class));
        executeSchema();
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

    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    public Connection getConnection() throws SQLException {
        return this.connectionFactory.getConnection();
    }

    public void loadBoards() throws SQLException {
        try (Connection c = getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(BOARD_SELECT)) {
                BoardService boardService = ERBoardService.getInstance();
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String id = rs.getString("id");
                        String name = rs.getString("name");
                        String description = rs.getString("description");
                        int maxShownPlayers = rs.getInt("max_players");
                        String userScoreName = rs.getString("user_score_name");

                        Optional<Board> board = boardService.getBoardByName(id);
                        if( !board.isPresent() ) {
                            boardService.createBoard(id,description,maxShownPlayers,userScoreName);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadUserData() throws SQLException {
        try (Connection c = getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(USER_DATA_SELECT)) {
                BoardService boardService = ERBoardService.getInstance();
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        UUID uuid = UUID.fromString(rs.getString("id_user"));
                        String boardId = rs.getString("id_board");
                        Float amount = rs.getFloat("amount");

                        Optional<Board> boardOptional = boardService.getBoardByName(boardId);
                        if( boardOptional.isPresent() ) {
                            boardService.createUserData(boardOptional.get(), Bukkit.getOfflinePlayer(uuid), amount);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveBoards() throws SQLException {
        BoardService boardService = ERBoardService.getInstance();

        PreparedStatement preparedStatement = getConnection().prepareStatement(BOARD_INSERT_OR_UPDATE);
        for( Board b : boardService.getBoards() ) {
            preparedStatement.setString(1, b.getName());
            preparedStatement.setString(2, b.getName());
            preparedStatement.setString(3, b.getDescription());
            preparedStatement.setInt(4, b.getMaxShownPlayers());
            preparedStatement.setString(5, b.getUserScoreName());
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
    }

    public void saveUserData() throws SQLException {
        BoardService boardService = ERBoardService.getInstance();

        PreparedStatement userInsert = getConnection().prepareStatement(USER_INSERT_OR_UPDATE);
        PreparedStatement userScoreInsert = getConnection().prepareStatement(USER_SCORE_INSERT_OR_UPDATE);
        for(Map.Entry<Board, Set<UserData>> data : boardService.getBoardData().entrySet() ){
            Board board = data.getKey();
            Set<UserData> userData = data.getValue();
            for (UserData ud : userData) {
                String nickname = ud.getNickname();
                UUID uuid = ud.getUniqueId();
                float amount = ud.getScore();

                userInsert.setString(1, uuid.toString());
                userInsert.setString(2, nickname);
                userInsert.executeUpdate();

                userScoreInsert.setString(1, uuid.toString());
                userScoreInsert.setString(2, board.getName());
                userScoreInsert.setFloat(3,amount);
                userScoreInsert.setFloat(4,amount);
                userScoreInsert.executeUpdate();
            }
        }
        userInsert.close();
        userScoreInsert.close();
    }

    public void saveBoardRewards() {
        RewardService rewardService = ERRewardService.getInstance();

        try (Connection c = getConnection()) {
            PreparedStatement deletePreviousItemsReward = c.prepareStatement(BOARD_ITEM_REWARD_DELETE_PREVIOUS);
            PreparedStatement insertItemReward = c.prepareStatement(BOARD_ITEM_REWARD_INSERT_OR_UPDATE);

            PreparedStatement insertMoneyReward = c.prepareStatement(BOARD_MONEY_REWARD_INSERT_OR_UPDATE);

            PreparedStatement insertTitleReward = c.prepareStatement(BOARD_TITLE_REWARD_INSERT_OR_UPDATE);
            for (Map.Entry<Board, List<Reward>> data : rewardService.getRewardsList().entrySet()) {
                Board board = data.getKey();
                List<Reward> rewards = data.getValue();

                deletePreviousItemsReward.setString(1, board.getName());
                deletePreviousItemsReward.executeUpdate();

                for (Reward reward : rewards) {
                    Integer rankingPosition = reward.getRankingPosition();
                    if (reward instanceof ERItemReward) {
                        String itemType = new Gson().toJson(((ERItemReward)reward).getReward().serialize());

                        insertItemReward.setString(1, board.getName());
                        insertItemReward.setInt(2, rankingPosition);
                        insertItemReward.setString(3, itemType);
                        insertItemReward.executeUpdate();
                    }
                    if (reward instanceof ERMoneyReward) {
                        Double amount = ((ERMoneyReward)reward).getReward();

                        insertMoneyReward.setString(1, board.getName());
                        insertMoneyReward.setInt(2, rankingPosition);
                        insertMoneyReward.setFloat(3, amount.floatValue());
                        insertMoneyReward.setFloat(4, amount.floatValue());
                        insertMoneyReward.executeUpdate();
                    }
                    if (reward instanceof ERTitleReward) {
                        String title = ((ERTitleReward)reward).getReward();

                        insertTitleReward.setString(1, board.getName());
                        insertTitleReward.setInt(2, rankingPosition);
                        insertTitleReward.setString(3, title);
                        insertTitleReward.setString(4, title);
                        insertTitleReward.executeUpdate();
                    }
                }
            }
            deletePreviousItemsReward.close();
            insertItemReward.close();
            insertMoneyReward.close();
            insertTitleReward.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadBoardRewards() throws ParseException {
        try (Connection c = getConnection()) {
            RewardService rewardService = ERRewardService.getInstance();
            BoardService boardService = ERBoardService.getInstance();

            try (PreparedStatement ps = c.prepareStatement(BOARD_ITEM_REWARD_SELECT)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String boardId = rs.getString("id_board");
                        Integer rankPosition = rs.getInt("rank_position");
                        String itemType = rs.getString("item_type");

                        /* Deserialize ItemStack */
                        Map<String,Object> itemStack = new Gson().fromJson(itemType,Map.class);

                        Optional<Board> boardOptional = boardService.getBoardByName(boardId);
                        if( boardOptional.isPresent() ) {
                            rewardService.newItemReward(ItemStack.deserialize(itemStack), boardOptional.get(), rankPosition);
                        }
                    }
                }
            }

            try (PreparedStatement ps = c.prepareStatement(BOARD_MONEY_REWARD_SELECT)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String boardId = rs.getString("id_board");
                        Integer rankPosition = rs.getInt("rank_position");
                        Double amount = rs.getDouble("amount");

                        Optional<Board> boardOptional = boardService.getBoardByName(boardId);
                        if( boardOptional.isPresent() ) {
                            rewardService.newMoneyReward(amount, boardOptional.get(), rankPosition);
                        }
                    }
                }
            }

            try (PreparedStatement ps = c.prepareStatement(BOARD_TITLE_REWARD_SELECT)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String boardId = rs.getString("id_board");
                        Integer rankPosition = rs.getInt("rank_position");
                        String title = rs.getString("title");

                        Optional<Board> boardOptional = boardService.getBoardByName(boardId);
                        if( boardOptional.isPresent() ) {
                            rewardService.newTitleReward(title, boardOptional.get(), rankPosition);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
