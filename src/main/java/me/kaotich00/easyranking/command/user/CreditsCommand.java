package me.kaotich00.easyranking.command.user;

import me.kaotich00.easyranking.command.api.ERUserCommand;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CreditsCommand extends ERUserCommand {

    public void onCommand(CommandSender sender, String[] args) {

        sender.sendMessage(ChatFormatter.creditsMessage());

        return;
    }

}
