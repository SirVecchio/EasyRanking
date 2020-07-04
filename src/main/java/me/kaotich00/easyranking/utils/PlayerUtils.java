package me.kaotich00.easyranking.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class PlayerUtils {

    public static boolean doesPlayerExist(UUID player) {
        OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(player);
        if(offlinePlayer == null) {
            return false;
        }
        if(!offlinePlayer.hasPlayedBefore()) {
            return false;
        }
        return true;
    }

}
