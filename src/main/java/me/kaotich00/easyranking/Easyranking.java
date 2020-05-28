package me.kaotich00.easyranking;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Easyranking extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("EasyRanking abilitato con successo");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
