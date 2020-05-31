package me.kaotich00.easyranking.reward.types;

import me.kaotich00.easyranking.reward.ERReward;
import org.bukkit.inventory.ItemStack;

public class ERItemReward extends ERReward {

    private ItemStack reward;

    public ERItemReward(ItemStack reward, int position) {
        this.rankingPosition = position;
        this.reward = reward;
    }

    @Override
    public ItemStack getReward() {
        return reward;
    }
}
