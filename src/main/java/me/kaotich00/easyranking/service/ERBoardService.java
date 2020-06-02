package me.kaotich00.easyranking.service;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.data.UserData;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.board.ERBoard;
import me.kaotich00.easyranking.data.ERUserData;
import me.kaotich00.easyranking.utils.BoardUtil;
import org.bukkit.entity.Player;

import java.util.*;

public class ERBoardService implements BoardService {

    private static volatile ERBoardService boardServiceInstance;
    private Set<Board> boardsList;
    private Map<Board, Set<UserData>> boardData;

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
        createBoard(BoardUtil.MOB_KILLED_BOARD_NAME, BoardUtil.MOB_KILLED_BOARD_DESCRIPTION, 100, "kills");
        createBoard(BoardUtil.PLAYER_KILLED_BOARD_NAME, BoardUtil.PLAYER_KILLED_BOARD_DESCRIPTION, 100, "kills");
        createBoard(BoardUtil.ORES_MINED_BOARD_NAME, BoardUtil.ORES_MINED_BOARD_DESCRIPTION, 100, "ores");
    }

    @Override
    public Board createBoard(String name, String description, int maxShownPlayers, String userScoreName) {
        ERBoard board = new ERBoard(name, description, maxShownPlayers, userScoreName);
        boardsList.add(board);
        boardData.put(board, new HashSet<>());
        ERRewardService.getInstance().registerBoard(board);
        return board;
    }

    @Override
    public Set<Board> getBoards() {
        return this.boardsList;
    }

    @Override
    public Board getBoardByName(String name) {
        return boardsList.stream().filter(board -> board.getName().equals(name)).findFirst().get();
    }

    @Override
    public boolean isNameAlreadyUsed(String name) {
        return boardsList.stream().filter(board -> board.getName().equals(name)).findFirst().isPresent();
    }

    @Override
    public Optional<UserData> getUserData(Board board, Player player) {
        return boardData.get(board).stream().filter(userData -> userData.getUniqueId().equals(player.getUniqueId())).findFirst();
    }

    @Override
    public void createUserData(Board board, Player player) {
        UserData userData = new ERUserData(player);
        boardData.get(board).add(userData);
    }

    @Override
    public float addScoreToPlayer(Board board, Player player, float score) {
        UserData userData = getUserData(board,player).get();
        userData.addScore(score);
        return userData.getScore();
    }

    @Override
    public float subtractScoreFromPlayer(Board board, Player player, float score) {
        UserData userData = getUserData(board,player).get();
        userData.subtractScore(score);
        return userData.getScore();
    }

    @Override
    public float setScoreOfPlayer(Board board, Player player, float score) {
        UserData userData = getUserData(board,player).get();
        userData.setScore(score);
        return userData.getScore();
    }
}
