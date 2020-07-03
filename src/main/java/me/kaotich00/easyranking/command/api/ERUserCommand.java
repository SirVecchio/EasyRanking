package me.kaotich00.easyranking.command.api;

import me.kaotich00.easyranking.api.command.ERCommand;
import me.kaotich00.easyranking.utils.ChatFormatter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ERUserCommand implements ERCommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Only players can run this command"));
            return;
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
