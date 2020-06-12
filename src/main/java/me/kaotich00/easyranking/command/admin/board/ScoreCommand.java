package me.kaotich00.easyranking.command.admin.board;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class ScoreCommand {

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length < 5 ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not enough arguments, usage:"));
            sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "score "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "nome" + ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "[add/subtract] " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "player" + ChatColor.DARK_GRAY + "> " + "<" + ChatColor.GRAY + "amount" + ChatColor.DARK_GRAY + ">"));
            return CommandTypes.COMMAND_SUCCESS;
        }

        BoardService boardService = ERBoardService.getInstance();

        String boardName = args[1];
        if(!boardService.isIdAlreadyUsed(boardName)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("No board found for the name " + ChatColor.GOLD + boardName + ChatColor.RED ));
            return CommandTypes.COMMAND_SUCCESS;
        }
        Board board = boardService.getBoardById(boardName).get();

        String scoreOperator = args[2];
        if(!isValidScoreOperator(scoreOperator)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not a valid operator, allowed operators: add/subtract/set" ));
            return CommandTypes.COMMAND_SUCCESS;
        }

        String playerName = args[3];
        if(Bukkit.getPlayer(playerName) == null && Bukkit.getOfflinePlayer(playerName) == null) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("The player " + playerName + " doesn't exist" ));
            return CommandTypes.COMMAND_SUCCESS;
        }
        UUID playerUUID;
        Player player = Bukkit.getPlayer(playerName);
        if( player == null ) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[2]);
            playerUUID = offlinePlayer.getUniqueId();
        } else {
            playerUUID = player.getUniqueId();
        }

        if(boardService.isUserExempted(playerUUID)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Cannot modify user score: the user " + playerName + " is exempted from leaderboards" ));
            return CommandTypes.COMMAND_SUCCESS;
        }

        String pointsAmount = args[4];
        if(!NumberUtils.isNumber(pointsAmount)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("The amount selected is not a numeric value" ));
            return CommandTypes.COMMAND_SUCCESS;
        }
        Float score = Float.parseFloat(pointsAmount);

        float totalScore = 0;
        switch(scoreOperator) {
            case "add":
                totalScore = boardService.addScoreToPlayer(board, playerUUID, score);
                sender.sendMessage(ChatFormatter.formatSuccessMessage("Successfully added " + ChatColor.GOLD + score.intValue() + " " + ChatColor.GREEN + board.getUserScoreName() + " to " + ChatColor.GOLD + playerName));
                break;
            case "subtract":
                totalScore = boardService.subtractScoreFromPlayer(board, playerUUID, score);
                sender.sendMessage(ChatFormatter.formatSuccessMessage("Successfully subtracted " + ChatColor.GOLD + score.intValue() + " " + ChatColor.GREEN + board.getUserScoreName() + " from " + ChatColor.GOLD + playerName));
                break;
            case "set":
                totalScore = boardService.setScoreOfPlayer(board, playerUUID, score);
                sender.sendMessage(ChatFormatter.formatSuccessMessage("Successfully set " + ChatColor.GOLD + score.intValue() + " " + ChatColor.GREEN + board.getUserScoreName() + " to " + ChatColor.GOLD + playerName));
                break;
        }

        sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.GRAY + "New score for " + ChatColor.GOLD + playerName + ChatColor.GRAY + ": " + ChatColor.GREEN + totalScore));
        return CommandTypes.COMMAND_SUCCESS;
    }

    private static boolean isValidScoreOperator(String scoreOperator) {
        return Arrays.asList("add","subtract","set").contains(scoreOperator);
    }

}
