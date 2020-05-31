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
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIRewardListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        dispatchGUI(event);
    }

    private void dispatchGUI( InventoryClickEvent event ) {
        switch(event.getView().getTitle()) {
            case GUIUtil.REWARD_TS_INVENTORY_TITLE:
                handleTypeSelectionGUI((Player)event.getWhoClicked(), event.getCurrentItem().getType());
                break;
            case GUIUtil.REWARD_PS_INVENTORY_TITLE:
                handlePositionSelectionGUI((Player)event.getWhoClicked(), event.getCurrentItem().getType());
                break;
            default:
                return;
        }
        event.setCancelled(true);
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
                RewardGUI guiFirstPosition = new RewardGUI(player, rewardService.getBoardFromModifyingPlayer(player.getUniqueId()));
                guiFirstPosition.openGUI(GUIUtil.REWARD_TS_STEP);
                player.playSound( player.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1 );
                break;
            /* Second position */
            case GOLD_BLOCK:
                RewardGUI guiSecondPosition = new RewardGUI(player, rewardService.getBoardFromModifyingPlayer(player.getUniqueId()));
                guiSecondPosition.openGUI(GUIUtil.REWARD_TS_STEP);
                player.playSound( player.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1 );
                break;
            /* Third position */
            case IRON_BLOCK:
                RewardGUI guiThirdPosition = new RewardGUI(player, rewardService.getBoardFromModifyingPlayer(player.getUniqueId()));
                guiThirdPosition.openGUI(GUIUtil.REWARD_TS_STEP);
                player.playSound( player.getLocation(), Sound.BLOCK_LEVER_CLICK, 10, 1 );
                break;
        }
    }

    private void handleTypeSelectionGUI(Player player, Material clickedMenu) {
        switch( clickedMenu ) {
            /* Item reward */
            case DIAMOND_SWORD:

                break;
            /* Title reward */
            case NAME_TAG:

                break;
            /* Money reward */
            case GOLD_NUGGET:

                break;
        }
    }

}
