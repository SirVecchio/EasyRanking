package me.kaotich00.easyranking.listener.gui.reward;

import me.kaotich00.easyranking.utils.GUIUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIRewardListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if( !event.getView().getTitle().equalsIgnoreCase(GUIUtil.REWARD_TS_INVENTORY_TITLE) ) {
            return;
        }

        event.setCancelled(true);

        switch( event.getCurrentItem().getType() ) {
            /* Item reward */
            case DIAMOND_SWORD:
                event.getWhoClicked().sendMessage( "Item reward" );
                break;
            /* Title reward */
            case NAME_TAG:
                event.getWhoClicked().sendMessage( "Title reward" );
                break;
            /* Money reward */
            case GOLD_NUGGET:
                event.getWhoClicked().sendMessage( "Money reward" );
                break;
        }
    }

    private void handleGUI() {

    }

}
