package me.kaotich00.easyranking.command.user;

import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CreditsCommand {

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage(ChatFormatter.creditsMessage());

        return CommandTypes.COMMAND_SUCCESS;
    }

}
