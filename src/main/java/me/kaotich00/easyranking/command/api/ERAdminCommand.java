package me.kaotich00.easyranking.command.api;

import me.kaotich00.easyranking.utils.ChatFormatter;
import org.bukkit.command.CommandSender;

public class ERAdminCommand extends ERUserCommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(!sender.hasPermission("easyranking.admin")) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("You don't have permissions to run this command"));
            return;
        }
    }

}
