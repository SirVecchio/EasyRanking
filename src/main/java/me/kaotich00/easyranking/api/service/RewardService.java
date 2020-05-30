package me.kaotich00.easyranking.api.service;

import me.kaotich00.easyranking.reward.ERReward;
import org.bukkit.inventory.ItemStack;

public interface RewardService {

    ERReward newReward(ItemStack itemStack);

    ERReward newReward(Double money);

    ERReward newReward(String title);

}
