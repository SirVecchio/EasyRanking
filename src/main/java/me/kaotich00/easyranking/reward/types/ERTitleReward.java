package me.kaotich00.easyranking.reward.types;

import me.kaotich00.easyranking.reward.ERReward;
import me.kaotich00.easyranking.utils.GUIUtil;

public class ERTitleReward extends ERReward {

    private String reward;

    public ERTitleReward(String reward, int position) {
        this.rankingPosition = position;
        this.reward = reward;
        this.rewardType = GUIUtil.TITLE_TYPE;
    }

    @Override
    public String getReward() {
        return reward;
    }
}
