package me.kaotich00.easyranking.command.admin;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.board.conversation.prompt.BoardCreationPrompt;
import me.kaotich00.easyranking.command.api.ERAdminCommand;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand extends ERAdminCommand {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        BoardCreationPrompt creation = new BoardCreationPrompt(Easyranking.getPlugin(Easyranking.class));
        creation.startConversationForPlayer((Player)sender);
        return;
    }

    @Override
    public String getName() {
        return CommandTypes.CREATE_COMMAND;
    }

    @Override
    public String getUsage() {
        return "/er create";
    }

    @Override
    public String getInfo() {
        return "Create a new board";
    }

}
