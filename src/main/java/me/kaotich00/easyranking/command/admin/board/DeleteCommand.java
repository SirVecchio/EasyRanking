package me.kaotich00.easyranking.command.admin.board;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.board.conversation.prompt.BoardCreationPrompt;
import me.kaotich00.easyranking.board.conversation.prompt.BoardDeletionPrompt;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteCommand {

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length < 1 ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not enough arguments, usage:"));
            sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "delete "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "board_id" + ChatColor.DARK_GRAY + "> "));
            return CommandTypes.COMMAND_SUCCESS;
        }

        BoardService boardService = ERBoardService.getInstance();

        String boardName = args[1];
        if(!boardService.isIdAlreadyUsed(boardName)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("No board found for the name " + ChatColor.GOLD + boardName + ChatColor.RED ));
            return CommandTypes.COMMAND_SUCCESS;
        }
        Board board = boardService.getBoardById(boardName).get();
        if(board.isDefault()) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("You cannot delete this board, cause its a default one. However, you can modify it from configs"));
            return CommandTypes.COMMAND_SUCCESS;
        }

        BoardDeletionPrompt creation = new BoardDeletionPrompt(Easyranking.getPlugin(Easyranking.class),board);
        creation.startConversationForPlayer((Player)sender);

        return CommandTypes.COMMAND_SUCCESS;
    }

}
