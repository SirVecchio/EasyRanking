package me.kaotich00.easyranking.board;

import me.kaotich00.easyranking.api.board.Board;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ERBoard erBoard = (ERBoard) o;
        return getMaxShownPlayers() == erBoard.getMaxShownPlayers() &&
                getName().equals(erBoard.getName()) &&
                getDescription().equals(erBoard.getDescription()) &&
                getUserScoreName().equals(erBoard.getUserScoreName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getMaxShownPlayers(), getUserScoreName());
    }
}
