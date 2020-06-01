package me.kaotich00.easyranking.listener.gui.reward;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.gui.reward.RewardGUI;
import me.kaotich00.easyranking.utils.GUIUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIRewardListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        dispatchClickEvent(event);
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
            case GUIUtil.REWARD_SELECT_ITEMS_TITLE:
                if( event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) ) {
                    handleItemRewardSelection((Player) event.getWhoClicked(), event.getCurrentItem());
                } else {
                    event.setCancelled(true);
                }
                break;
            default:
                return;
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
        switch( clickedMenu ) {
            /* Back arrow */
            case BARRIER:
                RewardGUI prevGui = new RewardGUI(player, rewardService.getBoardFromModifyingPlayer(player.getUniqueId()));
                prevGui.openGUI(GUIUtil.REWARD_PS_STEP);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_FRAME_REMOVE_ITEM, 10, 1);
                break;
            /* Item reward */
            case DIAMOND_SWORD:
                RewardGUI selectItemReward = new RewardGUI(player, rewardService.getBoardFromModifyingPlayer(player.getUniqueId()));
                selectItemReward.openGUI(GUIUtil.REWARD_SELECT_ITEMS_STEP);
                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1);
                break;
            /* Title reward */
            case NAME_TAG:

                break;
            /* Money reward */
            case GOLD_NUGGET:

                break;
        }
    }

    private void handleItemRewardSelection(Player player, ItemStack itemReward) {
        RewardService rewardService = Easyranking.getRewardService();
        Board board = rewardService.getBoardFromModifyingPlayer(player.getUniqueId());
        rewardService.newItemReward(itemReward.clone(),board,GUIUtil.FIRST_PLACE);
    }

}
