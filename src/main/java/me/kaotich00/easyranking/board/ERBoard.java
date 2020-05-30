package me.kaotich00.easyranking.board;

import me.kaotich00.easyranking.api.board.Board;

public class ERBoard implements Board {

    private String name;
    private String description;
    private int maxShownPlayers;
    private String userScoreName;

    public ERBoard(String name, String description, int maxShownPlayers, String userScoreName) {
        this.name = name;
        this.description = description;
        this.maxShownPlayers = maxShownPlayers;
        this.userScoreName = userScoreName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getMaxShownPlayers() {
        return this.maxShownPlayers;
    }

    @Override
    public String getUserScoreName() {
        return this.userScoreName;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setMaxShownPlayers(int maxShownPlayers) {
        this.maxShownPlayers = maxShownPlayers;
    }

    @Override
    public void setUserScoreName(String userScoreName) {
        this.userScoreName = userScoreName;
    }
}
