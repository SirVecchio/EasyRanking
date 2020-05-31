package me.kaotich00.easyranking.api.reward;

public interface Reward {

    int getRankingPosition();

    void setRankingPosition(int rankingPosition);

    Object getReward();

}
