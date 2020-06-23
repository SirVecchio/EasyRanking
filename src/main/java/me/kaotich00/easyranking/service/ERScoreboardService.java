package me.kaotich00.easyranking.service;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.api.service.ScoreboardService;
import me.kaotich00.easyranking.api.service.TaskService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ERScoreboardService implements ScoreboardService {

    private static ERScoreboardService taskService;
    private Map<UUID, Scoreboard> scoreboards;

    private ERScoreboardService() {
        if (taskService != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.scoreboards = new HashMap<>();
    }

    public static ERScoreboardService getInstance() {
        if(taskService == null) {
            taskService = new ERScoreboardService();
        }
        return taskService;
    }

    @Override
    public void addPlayerToScoreboard(UUID playerUUID) {
        this.scoreboards.put(playerUUID, null);
    }

    @Override
    public boolean isPlayerInScoreboard(UUID playerUUID) {
        return this.scoreboards.containsKey(playerUUID);
    }

    @Override
    public void removePlayerFromScoreboard(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        // Clear the scoreboard
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        this.scoreboards.remove(playerUUID);
    }

    @Override
    public void updateScoreBoard(UUID playerUUID) {

        if(!isPlayerInScoreboard(playerUUID)) {
            return;
        }

        BoardService boardService = ERBoardService.getInstance();
        Scoreboard scoreboard = this.scoreboards.get(playerUUID);
        Objective objective = scoreboard.getObjective("trackBoardsScore");

        for(Board board: boardService.getBoards()) {
            board.getUserScore(playerUUID).ifPresent(amount -> {
                Score score = objective.getScore(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + board.getName() + ChatColor.DARK_GRAY + "]");
                score.setScore(amount.intValue());
            });
        }
    }

    @Override
    public void newScoreboard(UUID playerUUID) {
        BoardService boardService = ERBoardService.getInstance();
        Player player = Bukkit.getPlayer(playerUUID);

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("trackBoardsScore","dummy", ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Easy" + ChatColor.GREEN + ChatColor.BOLD + "Ranking");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for(Board board: boardService.getBoards()) {
            board.getUserScore(playerUUID).ifPresent(amount -> {
                Score score = objective.getScore(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + board.getName() + ChatColor.DARK_GRAY + "]");
                score.setScore(amount.intValue());
            });
        }

        player.setScoreboard(scoreboard);
        this.scoreboards.put(playerUUID, scoreboard);
    }
}
