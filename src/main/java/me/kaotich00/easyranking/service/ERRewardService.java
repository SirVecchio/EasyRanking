package me.kaotich00.easyranking.service;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.reward.Reward;
import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.reward.ERReward;
import me.kaotich00.easyranking.reward.types.ERItemReward;
import me.kaotich00.easyranking.reward.types.ERMoneyReward;
import me.kaotich00.easyranking.reward.types.ERTitleReward;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ERRewardService implements RewardService {

    private Map<Board, Set<Reward>> rewardData;
    private Map<UUID, Board> isModyifingBoard;

    public ERRewardService() {
        this.rewardData = new HashMap<>();
        this.isModyifingBoard = new HashMap<>();
    }

    @Override
    public void registerBoard(Board board) {
        if( !rewardData.containsKey(board) ) {
            rewardData.put(board, new HashSet<>());
        }
    }

    @Override
    public void newItemReward(ItemStack itemStack, Board board, int position) {
        ERReward reward = new ERItemReward(itemStack, position);
        rewardData.get(board).add(reward);
    }

    @Override
    public void newMoneyReward(Double money, Board board, int position) {
        ERReward reward = new ERMoneyReward(money, position);
        rewardData.get(board).add(reward);
    }

    @Override
    public void newTitleReward(String title, Board board, int position) {
        ERReward reward = new ERTitleReward(title, position);
        rewardData.get(board).add(reward);

    }

    @Override
    public Set<Reward> getRewardsByPosition(Board board, int position) {
        return rewardData.get(board);
    }

    @Override
    public void addModifyingPlayer(UUID player, Board board) {
        isModyifingBoard.put(player,board);
    }

    @Override
    public void removeModifyingPlayer(UUID player, Board board) {
        isModyifingBoard.remove(player);
    }

    @Override
    public Board getBoardFromModifyingPlayer(UUID playerUniqueId) {
        return isModyifingBoard.containsKey(playerUniqueId) ? isModyifingBoard.get(playerUniqueId) : null;
    }


}
