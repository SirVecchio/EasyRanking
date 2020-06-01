package me.kaotich00.easyranking.listener.gui.reward;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.gui.reward.RewardGUI;
import me.kaotich00.easyranking.reward.conversation.prompt.MoneyAmountPrompt;
import me.kaotich00.easyranking.reward.conversation.prompt.TitleRewardPrompt;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.GUIUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class GUIRewardListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        dispatchClickEvent(event);
    }

    @EventHandler
    public void onItemSelectInventoryClose(InventoryCloseEvent event) {
        dispatchCloseEvent(event);
    }

    private void dispatchClickEvent(InventoryClickEvent event ) {
        switch(event.getView().getTitle()) {
            case GUIUtil.REWARD_TS_INVENTORY_TITLE:
                handleTypeSelectionGUI((Player)event.getWhoClicked(), event.getCurrentItem().getType());
                event.setCancelled(true);
                break;
            case GUIUtil.REWARD_PS_INVENTORY_TITLE:
                handlePositionSelectionGUI((Player)event.getWhoClicked(), event.getCurrentItem().getType());
                event.setCancelled(true);
                break;
            default:
                return;
        }
    }

    private void dispatchCloseEvent(InventoryCloseEvent event) {
        switch(event.getView().getTitle()) {
            case GUIUtil.REWARD_SELECT_ITEMS_TITLE:
                handleItemRewardSelection((Player) event.getPlayer(), event.getInventory());
                break;
        }
    }

    private void handlePositionSelectionGUI(Player player, Material clickedMenu) {
        RewardService rewardService = Easyranking.getRewardService();
        switch( clickedMenu ) {
            /* Close menu */
            case BARRIER:
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_FRAME_REMOVE_ITEM, 10, 1);
                break;
            /* First position */
            case DIAMOND_BLOCK:
                RewardGUI guiFirstPosition = new RewardGUI(player, rewardService.getBoardFromModifyingPlayer(player.getUniqueId()), GUIUtil.FIRST_PLACE);
                guiFirstPosition.openGUI(GUIUtil.REWARD_TS_STEP);
                player.playSound( player.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1 );
                break;
            /* Second position */
            case GOLD_BLOCK:
                RewardGUI guiSecondPosition = new RewardGUI(player, rewardService.getBoardFromModifyingPlayer(player.getUniqueId()), GUIUtil.SECOND_PLACE);
                guiSecondPosition.openGUI(GUIUtil.REWARD_TS_STEP);
                player.playSound( player.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1 );
                break;
            /* Third position */
            case IRON_BLOCK:
                RewardGUI guiThirdPosition = new RewardGUI(player, rewardService.getBoardFromModifyingPlayer(player.getUniqueId()), GUIUtil.THIRD_PLACE);
                guiThirdPosition.openGUI(GUIUtil.REWARD_TS_STEP);
                player.playSound( player.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1 );
                break;
        }
    }

    private void handleTypeSelectionGUI(Player player, Material clickedMenu) {
        RewardService rewardService = Easyranking.getRewardService();
        Board board = rewardService.getBoardFromModifyingPlayer(player.getUniqueId());
        int rewardRank = rewardService.getItemSelectionRankFromModifyingPlayer(player.getUniqueId());

        switch( clickedMenu ) {
            /* Back arrow */
            case BARRIER:
                RewardGUI prevGui = new RewardGUI(player, board);
                prevGui.openGUI(GUIUtil.REWARD_PS_STEP);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_FRAME_REMOVE_ITEM, 10, 1);
                break;
            /* Item reward */
            case DIAMOND_SWORD:
                RewardGUI selectItemReward = new RewardGUI(player, board, rewardRank);
                selectItemReward.openGUI(GUIUtil.REWARD_SELECT_ITEMS_STEP);
                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1);
                break;
            /* Money reward */
            case GOLD_NUGGET:
                ConversationFactory moneyFactory = new ConversationFactory(Easyranking.getPlugin(Easyranking.class))
                        .withModality(true)
                        .withFirstPrompt(new MoneyAmountPrompt(board, rewardRank))
                        .withEscapeSequence("cancel")
                        .withTimeout(10)
                        .thatExcludesNonPlayersWithMessage("This conversation can only be initiated by a player");
                moneyFactory.buildConversation(player).begin();
                player.closeInventory();

                break;
            /* Title reward */
            case NAME_TAG:
                ConversationFactory titleFactory = new ConversationFactory(Easyranking.getPlugin(Easyranking.class))
                        .withModality(true)
                        .withFirstPrompt(new TitleRewardPrompt(board, rewardRank))
                        .withEscapeSequence("cancel")
                        .withTimeout(10)
                        .thatExcludesNonPlayersWithMessage("This conversation can only be initiated by a player");
                titleFactory.buildConversation(player).begin();
                player.closeInventory();
                break;
        }
    }

    private void handleItemRewardSelection(Player player, Inventory inventory) {
        RewardService rewardService = Easyranking.getRewardService();
        Board board = rewardService.getBoardFromModifyingPlayer(player.getUniqueId());
        int rewardRank = rewardService.getItemSelectionRankFromModifyingPlayer(player.getUniqueId());
        rewardService.clearItemReward(board, rewardRank);

        for( ItemStack reward : inventory.getContents() ) {
            if( reward != null ) {
                rewardService.newItemReward(reward.clone(), board, rewardRank);
                player.sendMessage(
                    ChatFormatter.formatSuccessMessage(
                        "Board: " + ChatColor.GOLD + board.getName() +
                        ChatColor.GREEN + " - Successfully added " +
                        ChatColor.GOLD + reward.getItemMeta().getDisplayName() +
                        ChatColor.GREEN + " x " +
                        ChatColor.GOLD + reward.getAmount() +
                        ChatColor.GREEN + " for rank position " + ChatColor.GOLD + rewardRank
                    )
                );
            }
        }

        player.playSound(player.getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,10,1);
        rewardService.removeItemSelectionRank(player.getUniqueId());
        rewardService.removeModifyingPlayer(player.getUniqueId());
    }

}
