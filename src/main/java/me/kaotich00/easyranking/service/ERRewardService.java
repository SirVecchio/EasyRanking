package me.kaotich00.easyranking.service;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.reward.Reward;
import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.reward.ERReward;
import me.kaotich00.easyranking.reward.types.ERItemReward;
import me.kaotich00.easyranking.reward.types.ERMoneyReward;
import me.kaotich00.easyranking.reward.types.ERTitleReward;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ERRewardService implements RewardService {

    private Map<Board, Set<Reward>> rewardData;

    public ERRewardService() {
        this.rewardData = new HashMap<>();
    }

    @Override
    public void registerBoard(Board board) {
        if( !rewardData.containsKey(board) ) {
            rewardData.put(board, new HashSet<>());
        }
    }

    public void newItemReward(ItemStack itemStack, Board board, int position) {
        ERReward reward = new ERItemReward(itemStack, position);
        rewardData.get(board).add(reward);
    }

    public void newMoneyReward(Double money, Board board, int position) {
        ERReward reward = new ERMoneyReward(money, position);
        rewardData.get(board).add(reward);
    }

    public void newTitleReward(String title, Board board, int position) {
        ERReward reward = new ERTitleReward(title, position);
        rewardData.get(board).add(reward);

    }

    @Override
    public Set<Reward> getRewardsByPosition(Board board, int position) {
        return rewardData.get(board);
    }


}
