package me.kaotich00.easyranking.reward;

import me.kaotich00.easyranking.api.reward.Reward;

public class ERReward implements Reward{

    protected int rankingPosition;
    protected int rewardType;

    @Override
    public int getRankingPosition() {
        return rankingPosition;
    }

    @Override
    public void setRankingPosition(int rankingPosition) {
        this.rankingPosition = rankingPosition;
    }

    @Override
    public int getRewardType() {
        return rewardType;
    }

    @Override
    public Object getReward() {
        return null;
    }

}
