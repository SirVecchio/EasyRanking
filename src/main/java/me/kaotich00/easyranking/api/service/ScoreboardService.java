package me.kaotich00.easyranking.api.service;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

public interface ScoreboardService {

    void addPlayerToScoreboard(UUID playerUUID);

    boolean isPlayerInScoreboard(UUID playerUUID);

    void removePlayerFromScoreboard(UUID playerUUID);

    void updateScoreBoard(UUID playerUUID);

    void newScoreboard(UUID playerUUID);

}
