package me.kaotich00.easyranking.service;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.data.UserData;
import me.kaotich00.easyranking.api.reward.Reward;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.board.ERBoard;

import java.util.Map;

public class ERBoardService implements BoardService {

    private Map<Board, UserData> boardData;
    private Map<Board, Reward> rewardData;

    @Override
    public Board createBoard(String name, String description, int maxShownPlayers, String userScoreName) {
        return new ERBoard(name, description, maxShownPlayers, userScoreName);
    }
}
