package me.kaotich00.easyranking.api.service;

import me.kaotich00.easyranking.api.board.Board;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public interface BoardService {

    void initDefaultBoards();

    Board createBoard(String id, String name, String description, int maxShownPlayers, String userScoreName, boolean isDefault);

    void modifyBoardName(Board board, String name);

    void modifyBoardDescription(Board board, String description);

    void modifyBoardMaxShownPlayers(Board board, Integer maxShownPlayers);

    void modifyBoardSuffix(Board board, String suffix);

    void deleteBoard(Board board);

    Set<Board> getBoards();

    Optional<Board> getBoardById(String name);

    List<String> getBoardInfo(Board board);

    boolean isIdAlreadyUsed(String name);

    List<UUID> sortScores(Board board);

    void initUserScore(Board board, Player player);

    void initUserScore(Board board, OfflinePlayer player, Float amount);

    float addScoreToPlayer(Board board, Player player, Float score);

    float subtractScoreFromPlayer(Board board, Player player, Float score);

    float setScoreOfPlayer(Board board, Player player, Float score);

}
