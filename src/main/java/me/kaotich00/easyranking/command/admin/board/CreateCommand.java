package me.kaotich00.easyranking.command.admin.board;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.board.conversation.prompt.BoardCreationPrompt;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand {

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {
        BoardCreationPrompt creation = new BoardCreationPrompt(Easyranking.getPlugin(Easyranking.class));
        creation.startConversationForPlayer((Player)sender);
        return CommandTypes.COMMAND_SUCCESS;
    }

}
