package me.kaotich00.easyranking.api.service;

import java.util.UUID;

public interface ScoreboardService {

    void addPlayerToScoreboard(UUID playerUUID);

    boolean isPlayerInScoreboard(UUID playerUUID);

    void removePlayerFromScoreboard(UUID playerUUID);

    void updateScoreBoard(UUID playerUUID);

    void newScoreboard(UUID playerUUID);

}
