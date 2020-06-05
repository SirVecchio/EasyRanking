package me.kaotich00.easyranking.api.board;

public interface Board {

    String getId();

    String getName();

    String getDescription();

    int getMaxShownPlayers();

    String getUserScoreName();

    void setName(String name);

    void setDescription(String description);

    void setMaxShownPlayers(int maxShownplayers);

    void setUserScoreName(String userScoreName);

}
