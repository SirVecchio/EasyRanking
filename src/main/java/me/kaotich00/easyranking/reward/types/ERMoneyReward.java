package me.kaotich00.easyranking.reward.types;

import me.kaotich00.easyranking.reward.ERReward;

public class ERMoneyReward extends ERReward {

    private Double reward;

    public ERMoneyReward(Double reward, int position) {
        this.rankingPosition = position;
        this.reward = reward;
    }

    @Override
    public Double getReward() {
        return reward;
    }
}
