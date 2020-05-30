package me.kaotich00.easyranking.api.service;

import me.kaotich00.easyranking.api.board.Board;

import java.util.Set;

public interface BoardService {

    Board createBoard(String name, String description, int maxShownPlayers, String userScoreName);

    Set<Board> getBoards();

    boolean isNameAlreadyUsed(String name);

}
