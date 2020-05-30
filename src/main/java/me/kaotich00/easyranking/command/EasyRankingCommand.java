package me.kaotich00.easyranking.command;

import me.kaotich00.easyranking.command.admin.board.CreateCommand;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EasyRankingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length == 0 ) {
            sender.sendMessage(ChatFormatter.helpMessage());
            return true;
        }

        boolean result = CommandTypes.COMMAND_FAILURE;
        switch(args[0]) {
            case CommandTypes.HELP_COMMAND:
                sender.sendMessage(ChatFormatter.helpMessage());
                result = CommandTypes.COMMAND_SUCCESS;
                break;

            case CommandTypes.CREATE_COMMAND:
                CreateCommand.executeCommand(sender, command, label, args);
                result = CommandTypes.COMMAND_SUCCESS;
                break;
        }
        return result;
    }

}
