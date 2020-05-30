package me.kaotich00.easyranking.reward.types;

import me.kaotich00.easyranking.reward.ERReward;
import org.bukkit.inventory.ItemStack;

public class ERItemReward extends ERReward {

    private ItemStack reward;

    public ERItemReward(ItemStack reward) {
        this.reward = reward;
    }

}
