package me.kaotich00.easyranking.reward;

import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.reward.types.ERItemReward;
import me.kaotich00.easyranking.reward.types.ERMoneyReward;
import me.kaotich00.easyranking.reward.types.ERTitleReward;
import org.bukkit.inventory.ItemStack;

public class ERRewardService implements RewardService {

    /* New Item Reward */
    public ERReward newReward(ItemStack itemStack) {
        return new ERItemReward(itemStack);
    }

    /* New Money Reward */
    public ERReward newReward(Double money) {
        return new ERMoneyReward(money);
    }

    /* New Title Reward */
    public ERReward newReward(String title) {
        return new ERTitleReward(title);
    }

}
