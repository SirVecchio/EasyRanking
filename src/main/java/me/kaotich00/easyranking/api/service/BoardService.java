package me.kaotich00.easyranking.api.service;

import me.kaotich00.easyranking.api.board.Board;

public interface BoardService {

    Board createBoard(String name, String description, int maxShownPlayers, String userScoreName);

}
