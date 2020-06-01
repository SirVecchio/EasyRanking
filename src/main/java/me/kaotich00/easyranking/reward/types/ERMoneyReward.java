package me.kaotich00.easyranking.reward.types;

import me.kaotich00.easyranking.reward.ERReward;
import me.kaotich00.easyranking.utils.GUIUtil;

public class ERMoneyReward extends ERReward {

    private Double reward;

    public ERMoneyReward(Double reward, int position) {
        this.rankingPosition = position;
        this.reward = reward;
        this.rewardType = GUIUtil.MONEY_TYPE;
    }

    @Override
    public Double getReward() {
        return reward;
    }
}
