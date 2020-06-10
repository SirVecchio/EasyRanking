package me.kaotich00.easyranking.command.admin.board;

import me.kaotich00.easyranking.config.ConfigurationManager;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadCommand {

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigurationManager configManager = ConfigurationManager.getInstance();
        configManager.reloadDefaultConfig();
        sender.sendMessage(ChatFormatter.formatSuccessMessage("Successfully reloaded config.yml"));
        return CommandTypes.COMMAND_SUCCESS;
    }

}
