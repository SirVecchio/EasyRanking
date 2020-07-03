package me.kaotich00.easyranking.command.admin;

import me.kaotich00.easyranking.command.api.ERAdminCommand;
import me.kaotich00.easyranking.config.ConfigurationManager;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends ERAdminCommand {

    public void onCommand(CommandSender sender, String[] args) {
        ConfigurationManager configManager = ConfigurationManager.getInstance();
        configManager.reloadDefaultConfig();
        sender.sendMessage(ChatFormatter.formatSuccessMessage("Successfully reloaded config.yml"));
        return;
    }

}
