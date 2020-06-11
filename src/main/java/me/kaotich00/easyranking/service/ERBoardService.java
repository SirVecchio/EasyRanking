package me.kaotich00.easyranking.service;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.board.ERBoard;
import me.kaotich00.easyranking.storage.Storage;
import me.kaotich00.easyranking.storage.StorageFactory;
import me.kaotich00.easyranking.task.EconomyBoardTask;
import me.kaotich00.easyranking.utils.BoardUtil;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.SortUtil;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class ERBoardService implements BoardService {

    private static ERBoardService boardServiceInstance;
    private Set<Board> boardsList;

    private ERBoardService() {
        if (boardServiceInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.boardsList = new HashSet<>();
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
    public List<UUID> sortScores(Board board) {
        Map<UUID,Float> sortedMap = SortUtil.sortByValue(board.getAllScores(),SortUtil.DESC);
        return new ArrayList<UUID>(sortedMap.keySet());
    }

    @Override
    public void initUserScore(Board board, Player player) {
        board.addUser(player.getUniqueId());
    }

    @Override
    public void initUserScore(Board board, OfflinePlayer player, Float amount) {
        board.addUser(player.getUniqueId(),amount);
    }

    @Override
    public float addScoreToPlayer(Board board, Player player, Float score) {

        if(!board.getUserScore(player.getUniqueId()).isPresent()) {
            board.addUser(player.getUniqueId());
        }

        Float newScore = board.getUserScore(player.getUniqueId()).get() + score;
        board.setUserScore(player.getUniqueId(), newScore);

        player.sendMessage(
                (ChatFormatter.formatSuccessMessage(
                        ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + board.getName() + ChatColor.DARK_GRAY + "] " +
                        ChatColor.GRAY + "(" + ChatColor.GREEN + "+" + score.intValue() + " " + board.getUserScoreName() + ChatColor.GRAY + ")" +
                        ChatColor.DARK_GRAY + " |" +
                        ChatColor.GRAY + " New score: " +
                        ChatColor.GOLD + newScore.intValue() + " " + board.getUserScoreName()
                ))
        );

        return newScore;
    }

    @Override
    public float subtractScoreFromPlayer(Board board, Player player, Float score) {

        if(!board.getUserScore(player.getUniqueId()).isPresent()) {
            board.addUser(player.getUniqueId());
        }

        Float newScore = (board.getUserScore(player.getUniqueId()).get() - score) >= 0 ? (board.getUserScore(player.getUniqueId()).get() - score) : 0;
        board.setUserScore(player.getUniqueId(), newScore);

        player.sendMessage(
                (ChatFormatter.formatSuccessMessage(
                        ChatColor.DARK_GRAY +
                                "[" + ChatColor.DARK_AQUA + board.getName() + ChatColor.DARK_GRAY + "] " +
                                ChatColor.GRAY + "(" + ChatColor.RED + "-" + score.intValue() + " " + board.getUserScoreName() + ChatColor.GRAY + ")" +
                                ChatColor.DARK_GRAY + " |" +
                                ChatColor.GRAY + " New score: " +
                                ChatColor.GOLD + newScore.intValue() + " " + board.getUserScoreName()
                ))
        );

        return newScore;
    }

    @Override
    public float setScoreOfPlayer(Board board, Player player, Float score) {
        if(!board.getUserScore(player.getUniqueId()).isPresent()) {
            board.addUser(player.getUniqueId());
        }

        board.setUserScore(player.getUniqueId(), score);

        player.sendMessage(
                (ChatFormatter.formatSuccessMessage(
                        ChatColor.DARK_GRAY +
                                "[" + ChatColor.DARK_AQUA + board.getName() + ChatColor.DARK_GRAY + "] " +
                                ChatColor.GRAY + "(" + ChatColor.GREEN + "=" + score.intValue() + " " + board.getUserScoreName() + ChatColor.GRAY + ")" +
                                ChatColor.DARK_GRAY + " |" +
                                ChatColor.GRAY + " New score: " +
                                ChatColor.GOLD + score.intValue() + " " + board.getUserScoreName()
                ))
        );

        return score;
    }
}
