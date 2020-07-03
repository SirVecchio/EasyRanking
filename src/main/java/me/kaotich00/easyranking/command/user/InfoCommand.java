package me.kaotich00.easyranking.command.user;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.command.api.ERUserCommand;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InfoCommand extends ERUserCommand {

    public void onCommand(CommandSender sender, String[] args) {

        if( args.length < 2 ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not enough arguments, usage:"));
            sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "info "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "board_id" + ChatColor.DARK_GRAY + ">"));
            return;
        }

        BoardService boardService = ERBoardService.getInstance();

        String boardName = args[1];
        if(!boardService.isIdAlreadyUsed(boardName)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("No board found for the name " + ChatColor.GOLD + boardName + ChatColor.RED ));
            return;
        }
        Board board = boardService.getBoardById(boardName).get();

        sender.sendMessage(ChatFormatter.chatHeader());
        List<String> boardInfo = boardService.getBoardInfo(board);
        for( String s : boardInfo ) {
            sender.sendMessage(s);
        }
        sender.sendMessage(ChatFormatter.chatFooter());

        return;
    }

}
