package me.kaotich00.easyranking.command.admin.board;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CreateCommand {

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length < 2 ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not enough arguments"));
            return CommandTypes.COMMAND_SUCCESS;
        }

        BoardService boardService = ERBoardService.getInstance();

        String boardName = args[1];
        if( boardService.isNameAlreadyUsed(boardName) ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("A board with the name " + ChatColor.GOLD + boardName + ChatColor.RED + " already exists"));
            return CommandTypes.COMMAND_SUCCESS;
        }

        boardService.createBoard(boardName, "Test descrizione", 100, "punti");
        sender.sendMessage(ChatFormatter.formatSuccessMessage("Board " + ChatColor.GOLD + boardName + ChatColor.GREEN + " successfully created!"));

        return CommandTypes.COMMAND_SUCCESS;
    }

}
