package me.kaotich00.easyranking.command.admin.board;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.Arrays;

public class ModifyCommand {

    private final static String MODIFY_NAME = "name";
    private final static String MODIFY_DESCRIPTION = "description";
    private final static String MODIFY_MAX_SHOWN_PLAYERS = "maxShownPlayers";
    private final static String MODIFY_SUFFIX = "suffix";

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length < 4 ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not enough arguments, usage:"));
            sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "modify "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "board_id" + ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "[name/description/maxShownPlayers/suffix] " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "value" + ChatColor.DARK_GRAY + "> "));
            return CommandTypes.COMMAND_SUCCESS;
        }

        BoardService boardService = ERBoardService.getInstance();

        String boardName = args[1];
        if(!boardService.isIdAlreadyUsed(boardName)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("No board found for the name " + ChatColor.GOLD + boardName + ChatColor.RED ));
            return CommandTypes.COMMAND_SUCCESS;
        }
        Board board = boardService.getBoardById(boardName).get();

        String modifyAction = args[2];
        if(!isValidAction(modifyAction)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not a valid action, allowed actions: name/description/maxShownPlayers/suffix" ));
            return CommandTypes.COMMAND_SUCCESS;
        }

        /* Join together all the remaining args */
        String value = "";
        for(int i = 3; i < args.length; i++){
            String arg = args[i];
            if(i + 1 != args.length) {
                arg += " ";
            }
            value = value + arg;
        }

        switch(modifyAction) {
            case MODIFY_NAME:
                boardService.modifyBoardName(board, value);
                break;
            case MODIFY_DESCRIPTION:
                boardService.modifyBoardDescription(board, value);
                break;
            case MODIFY_MAX_SHOWN_PLAYERS:
                if(!StringUtils.isNumeric(args[3])) {
                    sender.sendMessage(ChatFormatter.formatErrorMessage("Max shown players must be a number"));
                    return CommandTypes.COMMAND_SUCCESS;
                }
                boardService.modifyBoardMaxShownPlayers(board, Integer.valueOf(args[3]));
                value = String.valueOf(args[3]);
                break;
            case MODIFY_SUFFIX:
                boardService.modifyBoardSuffix(board, value);
                break;
        }

        sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + board.getName() + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "Successfully modified " + ChatColor.GREEN + modifyAction + ChatColor.GRAY + " to " + ChatColor.GOLD + value));
        return CommandTypes.COMMAND_SUCCESS;
    }

    private static boolean isValidAction(String modifyAction) {
        return Arrays.asList("name","description","maxShownPlayers","suffix").contains(modifyAction);
    }

}
