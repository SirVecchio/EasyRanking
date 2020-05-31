package me.kaotich00.easyranking.api.service;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.reward.Reward;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public interface RewardService {

    void registerBoard(Board board);

    void newItemReward(ItemStack itemStack, Board board, int position);

    void newMoneyReward(Double money, Board board, int position);

    void newTitleReward(String title, Board board, int position);

    Set<Reward> getRewardsByPosition(Board board, int position);

    void addModifyingPlayer(UUID player, Board board);

    void removeModifyingPlayer(UUID player, Board board);

    Board getBoardFromModifyingPlayer(UUID playerUniqueId);

}
