package me.kaotich00.easyranking.api.service;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.reward.Reward;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface RewardService {

    void registerBoard(Board board);

    void newItemReward(ItemStack itemStack, Board board, int position);

    void newMoneyReward(Double money, Board board, int position);

    void newTitleReward(String title, Board board, int position);

    void clearItemReward(Board board, int position);

    void clearMoneyReward(Board board, int position);

    void clearTitleReward(Board board, int position);

    void collectRewards();

    void deleteBoardRewards(Board board);

    List<Reward> getRewardsByPosition(Board board, int position);

    List<Reward> getItemRewardsByPosition(Board board, int position);

    Optional<Reward> getMoneyRewardByPosition(Board board, int position);

    Optional<Reward> getTitleRewardByPosition(Board board, int position);

    List<ItemStack> getUncollectedRewardForUser(UUID uuid);

    Map<Board, List<Reward>> getRewardsList();

    Map<UUID, List<ItemStack>> getUncollectedRewards();

    void removeUncollectedItemsForPlayer(UUID uuid);

    void addUncollectedItem(UUID uuid, ItemStack itemStack);

    void addModifyingPlayer(UUID player, Board board);

    void removeModifyingPlayer(UUID player);

    void addItemSelectionRank(UUID player, int rankPlace);

    void removeItemSelectionRank(UUID player);

    Board getBoardFromModifyingPlayer(UUID playerUniqueId);

    int getItemSelectionRankFromModifyingPlayer(UUID playerUniqueId);

    Optional<String> getUserTitleIfActive(UUID player);

    void setUserTitle(UUID player, String title);

    void removeUserTitle(UUID player);

}
