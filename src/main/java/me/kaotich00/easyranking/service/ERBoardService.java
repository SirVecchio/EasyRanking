package me.kaotich00.easyranking.service;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.data.UserData;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.board.ERBoard;
import me.kaotich00.easyranking.data.ERUserData;
import me.kaotich00.easyranking.storage.Storage;
import me.kaotich00.easyranking.storage.StorageFactory;
import me.kaotich00.easyranking.task.EconomyBoardTask;
import me.kaotich00.easyranking.utils.BoardUtil;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.RankPositionComparator;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class ERBoardService implements BoardService {

    private static ERBoardService boardServiceInstance;
    private Set<Board> boardsList;
    private Map<Board, List<UserData>> boardData;

    private ERBoardService() {
        if (boardServiceInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.boardsList = new HashSet<>();
        this.boardData = new HashMap<>();
        initDefaultBoards();
    }

    public static ERBoardService getInstance() {
        if(boardServiceInstance == null) {
            boardServiceInstance = new ERBoardService();
        }
        return boardServiceInstance;
    }

    @Override
    public void initDefaultBoards() {
        FileConfiguration defaultConfig = Easyranking.getDefaultConfig();
        if( defaultConfig.getBoolean("mobKilled.enabled") ) {
            createBoard(BoardUtil.MOB_KILLED_BOARD_ID, BoardUtil.MOB_KILLED_BOARD_NAME, BoardUtil.MOB_KILLED_BOARD_DESCRIPTION, 100, "kills", true);
        }
        if( defaultConfig.getBoolean("playerKilled.enabled") ) {
            createBoard(BoardUtil.PLAYER_KILLED_BOARD_ID, BoardUtil.PLAYER_KILLED_BOARD_NAME, BoardUtil.PLAYER_KILLED_BOARD_DESCRIPTION, 100, "kills", true);
        }
        if( defaultConfig.getBoolean("economy.enabled") ) {
            createBoard(BoardUtil.ECONOMY_BOARD_SERVICE_ID, BoardUtil.ECONOMY_BOARD_SERVICE_NAME, BoardUtil.ECONOMY_BOARD_SERVICE_DESCRIPTION, 100, "$", true);
        }
        if( defaultConfig.getBoolean("oresMined.enabled") ) {
            createBoard(BoardUtil.ORES_MINED_BOARD_ID, BoardUtil.ORES_MINED_BOARD_NAME, BoardUtil.ORES_MINED_BOARD_DESCRIPTION, 100, "ores", true);
        }
        EconomyBoardTask.scheduleEconomy();
    }

    @Override
    public Board createBoard(String id, String name, String description, int maxShownPlayers, String userScoreName, boolean isDefault) {
        ERBoard board = new ERBoard(id, name, description, maxShownPlayers, userScoreName, isDefault);
        boardsList.add(board);
        boardData.put(board, new ArrayList<>());
        ERRewardService.getInstance().registerBoard(board);
        return board;
    }

    @Override
    public void modifyBoardName(Board board, String name) {
        board.setName(name);
    }

    @Override
    public void modifyBoardDescription(Board board, String description) {
        board.setDescription(description);
    }

    @Override
    public void modifyBoardMaxShownPlayers(Board board, Integer maxShownPlayers) {
        board.setMaxShownPlayers(maxShownPlayers);
    }

    @Override
    public void modifyBoardSuffix(Board board, String suffix) {
        board.setUserScoreName(suffix);
    }

    @Override
    public void deleteBoard(Board board) {
        /* Removes the board from the database */
        Storage storage = StorageFactory.getInstance();
        storage.getStorageMethod().deleteBoard(board.getId());
        /* Removes all rewards from the board */
        RewardService rewardService = ERRewardService.getInstance();
        rewardService.deleteBoardRewards(board);
        /* Removes the board */
        boardsList.remove(board);
    }

    @Override
    public Set<Board> getBoards() {
        return this.boardsList;
    }

    @Override
    public Optional<Board> getBoardById(String id) {
        return boardsList.stream().filter(board -> board.getId().equals(id)).findFirst();
    }

    @Override
    public List<String> getBoardInfo(Board board) {
        List<String> info = new ArrayList<>();
        info.add(ChatColor.GREEN + "ID" + ": " + ChatColor.RESET + board.getId());
        info.add(ChatColor.GREEN + "Name" +  ": " + ChatColor.RESET + board.getName());
        info.add(ChatColor.GREEN + "Description" + ": " + ChatColor.RESET + board.getDescription());
        info.add(ChatColor.GREEN + "Max shown players" + ": " + ChatColor.RESET + board.getMaxShownPlayers());
        info.add(ChatColor.GREEN + "Score suffix" + ": " + ChatColor.RESET + board.getUserScoreName());
        return info;
    }

    @Override
    public boolean isIdAlreadyUsed(String id) {
        return boardsList.stream().filter(board -> board.getId().equals(id)).findFirst().isPresent();
    }

    @Override
    public Optional<UserData> getUserData(Board board, Player player) {
        return boardData.get(board).stream().filter(userData -> userData.getUniqueId().equals(player.getUniqueId())).findFirst();
    }

    @Override
    public Map<Board, List<UserData>> getBoardData() {
        return this.boardData;
    }

    @Override
    public Optional<UserData> getPlayerByRankPosition(Board board, int rankPosition) {
        List<UserData> userList = this.boardData.get(board);
        userList.sort(new RankPositionComparator());
        if( rankPosition > userList.size() )
            return Optional.empty();
        return Optional.of(userList.get(rankPosition-1));
    }

    @Override
    public void createUserData(Board board, Player player) {
        UserData userData = new ERUserData(player);
        boardData.get(board).add(userData);
    }

    @Override
    public void createUserData(Board board, OfflinePlayer player, Float amount) {
        UserData userData = new ERUserData(player, amount);
        boardData.get(board).add(userData);
    }

    @Override
    public float addScoreToPlayer(Board board, Player player, Float score) {
        UserData userData = getUserData(board,player).get();
        userData.addScore(score);

        player.sendMessage(
                (ChatFormatter.formatSuccessMessage(
                        ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + board.getName() + ChatColor.DARK_GRAY + "] " +
                        ChatColor.GRAY + "(" + ChatColor.GREEN + "+" + score.intValue() + " " + board.getUserScoreName() + ChatColor.GRAY + ")" +
                        ChatColor.DARK_GRAY + " |" +
                        ChatColor.GRAY + " New score: " +
                        ChatColor.GOLD + (int) userData.getScore() + " " + board.getUserScoreName()
                ))
        );

        return userData.getScore();
    }

    @Override
    public float subtractScoreFromPlayer(Board board, Player player, Float score) {
        UserData userData = getUserData(board,player).get();
        userData.subtractScore(score);

        player.sendMessage(
                (ChatFormatter.formatSuccessMessage(
                        ChatColor.DARK_GRAY +
                                "[" + ChatColor.DARK_AQUA + board.getName() + ChatColor.DARK_GRAY + "] " +
                                ChatColor.GRAY + "(" + ChatColor.RED + "-" + score.intValue() + " " + board.getUserScoreName() + ChatColor.GRAY + ")" +
                                ChatColor.DARK_GRAY + " |" +
                                ChatColor.GRAY + " New score: " +
                                ChatColor.GOLD + (int) userData.getScore() + " " + board.getUserScoreName()
                ))
        );

        return userData.getScore();
    }

    @Override
    public float setScoreOfPlayer(Board board, Player player, Float score) {
        UserData userData = getUserData(board,player).get();
        userData.setScore(score);

        player.sendMessage(
                (ChatFormatter.formatSuccessMessage(
                        ChatColor.DARK_GRAY +
                                "[" + ChatColor.DARK_AQUA + board.getName() + ChatColor.DARK_GRAY + "] " +
                                ChatColor.GRAY + "(" + ChatColor.GREEN + "=" + score.intValue() + " " + board.getUserScoreName() + ChatColor.GRAY + ")" +
                                ChatColor.DARK_GRAY + " |" +
                                ChatColor.GRAY + " New score: " +
                                ChatColor.GOLD + (int) userData.getScore() + " " + board.getUserScoreName()
                ))
        );

        return userData.getScore();
    }
}
