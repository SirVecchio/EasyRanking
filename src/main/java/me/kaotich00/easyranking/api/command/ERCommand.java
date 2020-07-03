package me.kaotich00.easyranking.api.command;

import org.bukkit.command.CommandSender;

public interface ERCommand {

    void onCommand(CommandSender sender, String args[]);

    String getName();

    String getUsage();

    String getInfo();

}
