package me.kaotich00.easyranking.service;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.data.UserData;
import me.kaotich00.easyranking.api.reward.Reward;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.board.ERBoard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ERBoardService implements BoardService {

    private Set<Board> boardsList;
    private Map<Board, Set<UserData>> boardData;

    public ERBoardService() {
        this.boardsList = new HashSet<>();
        this.boardData = new HashMap<>();
    }

    @Override
    public Board createBoard(String name, String description, int maxShownPlayers, String userScoreName) {
        ERBoard board = new ERBoard(name, description, maxShownPlayers, userScoreName);
        boardsList.add(board);
        boardData.put(board, null);
        Easyranking.getRewardService().registerBoard(board);
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
}
