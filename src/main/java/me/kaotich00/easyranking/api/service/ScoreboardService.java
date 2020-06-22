package me.kaotich00.easyranking.api.service;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

public interface ScoreboardService {

    void addPlayerToScoreboard(UUID player);

    boolean isPlayerInScoreboard(UUID player);

    void removePlayerFromScoreboard(UUID player);

    void updateScoreBoard(UUID player);

    void newScoreboard(Player player);

}
