package me.kaotich00.easyranking.service;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.reward.Reward;
import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.reward.types.ERItemReward;
import me.kaotich00.easyranking.reward.types.ERMoneyReward;
import me.kaotich00.easyranking.reward.types.ERTitleReward;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ERRewardService implements RewardService {

    private Map<Board, List<Reward>> rewardData;
    private Map<UUID, Board> isModifyingBoard;

    public ERRewardService() {
        this.rewardData = new HashMap<>();
        this.isModifyingBoard = new HashMap<>();
    }

    @Override
    public void registerBoard(Board board) {
        if( !rewardData.containsKey(board) ) {
            rewardData.put(board, new ArrayList<>());
        }
    }

    @Override
    public void newItemReward(ItemStack itemStack, Board board, int position) {
        Reward reward = new ERItemReward(itemStack, position);
        rewardData.get(board).add(reward);
    }

    @Override
    public void newMoneyReward(Double money, Board board, int position) {
        Reward reward = new ERMoneyReward(money, position);
        rewardData.get(board).add(reward);
    }

    @Override
    public void newTitleReward(String title, Board board, int position) {
        Reward reward = new ERTitleReward(title, position);
        rewardData.get(board).add(reward);

    }

    @Override
    public List<Reward> getRewardsByPosition(Board board, int position) {
        return rewardData.get(board);
    }

    @Override
    public void addModifyingPlayer(UUID player, Board board) {
        isModifyingBoard.put(player,board);
    }

    @Override
    public void removeModifyingPlayer(UUID player, Board board) {
        isModifyingBoard.remove(player);
    }

    @Override
    public Board getBoardFromModifyingPlayer(UUID playerUniqueId) {
        return isModifyingBoard.containsKey(playerUniqueId) ? isModifyingBoard.get(playerUniqueId) : null;
    }


}
