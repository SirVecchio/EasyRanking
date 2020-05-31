package me.kaotich00.easyranking.listener.gui.reward;

import me.kaotich00.easyranking.utils.GUIUtil;
import org.bukkit.Material;
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
        switch( clickedMenu ) {
            /* Close menu */
            case BARRIER:
                player.closeInventory();
                break;
            /* First position */
            case DIAMOND_BLOCK:
                player.sendMessage("Premio prima posizione");
                break;
            /* Second position */
            case GOLD_BLOCK:
                player.sendMessage("Premio seconda posizione");
                break;
            /* Third position */
            case IRON_BLOCK:
                player.sendMessage("Premio terza posizione");
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
