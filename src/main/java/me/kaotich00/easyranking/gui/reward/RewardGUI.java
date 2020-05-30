package me.kaotich00.easyranking.gui.reward;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RewardGUI implements Listener {

    private final int ITEM_REWARD_SLOT = 11;
    private final int MONEY_REWARD_SLOT = 13;
    private final int TITLE_REWARD_SLOT = 15;

    private final Material ITEM_REWARD_MATERIAL = Material.DIAMOND_SWORD;
    private final Material MONEY_REWARD_MATERIAL = Material.GOLD_NUGGET;
    private final Material TITLE_REWARD_MATERIAL = Material.NAME_TAG;

    private String INVENTORY_TITLE = "Reward selection";

    private Player player;

    public RewardGUI(Player player) {
        this.player = player;
    }

    public void openRewardGUI() {
        Inventory GUI = Bukkit.createInventory(player, 27, INVENTORY_TITLE);

        GUI.setItem(ITEM_REWARD_SLOT, itemRewardMenu());
        GUI.setItem(MONEY_REWARD_SLOT, moneyRewardMenu());
        GUI.setItem(TITLE_REWARD_SLOT, titleRewardMenu());

        player.openInventory(GUI);
    }

    private ItemStack itemRewardMenu() {
        /* Menu point for Item Reward Management */
        ItemStack itemReward = new ItemStack(ITEM_REWARD_MATERIAL);
        ItemMeta itemRewardMeta = itemReward.getItemMeta();
        itemRewardMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemRewardMeta.setDisplayName(ChatColor.GREEN + "Item reward");
        List<String> itemRewardLore = new ArrayList<>();
        itemRewardLore.add(ChatColor.GOLD + "Set item rewards");
        itemRewardMeta.setLore(itemRewardLore);
        itemReward.setItemMeta(itemRewardMeta);

        return itemReward;
    }

    private ItemStack moneyRewardMenu() {
        /* Menu point for Item Reward Management */
        ItemStack itemReward = new ItemStack(MONEY_REWARD_MATERIAL);
        ItemMeta itemRewardMeta = itemReward.getItemMeta();
        itemRewardMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemRewardMeta.setDisplayName(ChatColor.GREEN + "Money reward");
        List<String> itemRewardLore = new ArrayList<>();
        itemRewardLore.add(ChatColor.GOLD + "Set money rewards");
        itemRewardMeta.setLore(itemRewardLore);
        itemReward.setItemMeta(itemRewardMeta);

        return itemReward;
    }

    private ItemStack titleRewardMenu() {
        /* Menu point for Item Reward Management */
        ItemStack itemReward = new ItemStack(TITLE_REWARD_MATERIAL);
        ItemMeta itemRewardMeta = itemReward.getItemMeta();
        itemRewardMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemRewardMeta.setDisplayName(ChatColor.GREEN + "Title reward");
        List<String> itemRewardLore = new ArrayList<>();
        itemRewardLore.add(ChatColor.GOLD + "Set title rewards");
        itemRewardMeta.setLore(itemRewardLore);
        itemReward.setItemMeta(itemRewardMeta);

        return itemReward;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if( !event.getView().getTitle().equalsIgnoreCase(INVENTORY_TITLE) ) {
            return;
        }

        event.setCancelled(true);

        switch( event.getCurrentItem().getType() ) {
            /* Item reward */
            case DIAMOND_SWORD:
                player.sendMessage( "Item reward" );
                break;
            /* Title reward */
            case NAME_TAG:
                player.sendMessage( "Title reward" );
                break;
            /* Money reward */
            case GOLD_NUGGET:
                player.sendMessage( "Money reward" );
                break;
        }
    }

}
