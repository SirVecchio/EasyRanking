package me.kaotich00.easyranking.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIUtil {

    /*****************************************/
    /* Reward GUI - Position selection phase */
    /*****************************************/
    public static final int REWARD_PS_STEP = 1;
    public static String REWARD_PS_INVENTORY_TITLE = "Select rank position";
    public static int REWARD_PS_INVENTORY_SIZE = 36;

    public static final int REWARD_PS_INFO_SLOT = 0;
    public static final int REWARD_PS_TITLE_SLOT = 4;
    public static final int REWARD_PS_CLOSE_SLOT = 8;
    public static final int REWARD_PS_POSITION_FIRST_SLOT = 20;
    public static final int REWARD_PS_POSITION_SECOND_SLOT = 22;
    public static final int REWARD_PS_POSITION_THIRD_SLOT = 24;

    public static final Material REWARD_PS_INFO_MATERIAL = Material.PAPER;
    public static final Material REWARD_PS_TITLE_MATERIAL = Material.NETHER_STAR;
    public static final Material REWARD_PS_CLOSE_MATERIAL = Material.BARRIER;
    public static final Material REWARD_PS_POSITION_FIRST_MATERIAL = Material.DIAMOND_BLOCK;
    public static final Material REWARD_PS_POSITION_SECOND_MATERIAL = Material.GOLD_BLOCK;
    public static final Material REWARD_PS_POSITION_THIRD_MATERIAL = Material.IRON_BLOCK;

    /********************************************/
    /* Reward GUI - Reward type selection phase */
    /********************************************/
    public static final int REWARD_TS_STEP = 2;
    public static String REWARD_TS_INVENTORY_TITLE = "Select reward type";
    public static int REWARD_TS_INVENTORY_SIZE = 45;

    public static final int REWARD_TS_ITEM_REWARD_SLOT = 11;
    public static final int REWARD_TS_MONEY_REWARD_SLOT = 13;
    public static final int REWARD_TS_TITLE_REWARD_SLOT = 15;

    public static final Material REWARD_TS_ITEM_REWARD_MATERIAL = Material.DIAMOND_SWORD;
    public static final Material REWARD_TS_MONEY_REWARD_MATERIAL = Material.GOLD_NUGGET;
    public static final Material REWARD_TS_TITLE_REWARD_MATERIAL = Material.NAME_TAG;

    /**********************/
    /* Global GUI Methods */
    /**********************/
    public static ItemStack prepareMenuPoint(Material material, String displayName, String[] lore) {
        ItemStack menuPoint = new ItemStack(material);

        ItemMeta menuPointMeta = menuPoint.getItemMeta();
        menuPointMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        menuPointMeta.setDisplayName(displayName);
        List<String> menuPointLore = new ArrayList(Arrays.asList( lore ) );
        menuPointMeta.setLore(menuPointLore);

        menuPoint.setItemMeta(menuPointMeta);

        return menuPoint;
    }

}
