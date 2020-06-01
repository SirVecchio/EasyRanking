package me.kaotich00.easyranking.api.service;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.data.UserData;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

public interface BoardService {

    Board createBoard(String name, String description, int maxShownPlayers, String userScoreName);

    Set<Board> getBoards();

    Board getBoardByName(String name);

    boolean isNameAlreadyUsed(String name);

    Optional<UserData> getUserData(Board board, Player player);

    void createUserData(Board board, Player player);

    float addScoreToPlayer(Board board, Player player, float score);

    float subtractScoreFromPlayer(Board board, Player player, float score);

    float setScoreOfPlayer(Board board, Player player, float score);

}
