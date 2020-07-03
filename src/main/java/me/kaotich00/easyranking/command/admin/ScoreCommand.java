package me.kaotich00.easyranking.command.admin;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.command.api.ERAdminCommand;
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

public class ScoreCommand extends ERAdminCommand {

    public void onCommand(CommandSender sender, String[] args) {
        if( args.length < 5 ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not enough arguments, usage:"));
            sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "score "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "nome" + ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "[add/subtract] " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "player" + ChatColor.DARK_GRAY + "> " + "<" + ChatColor.GRAY + "amount" + ChatColor.DARK_GRAY + ">"));
            return;
        }

        BoardService boardService = ERBoardService.getInstance();

        String boardName = args[1];
        if(!boardService.isIdAlreadyUsed(boardName)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("No board found for the name " + ChatColor.GOLD + boardName + ChatColor.RED ));
            return;
        }
        Board board = boardService.getBoardById(boardName).get();

        String scoreOperator = args[2];
        if(!isValidScoreOperator(scoreOperator)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not a valid operator, allowed operators: add/subtract/set" ));
            return;
        }

        String playerName = args[3];
        if(Bukkit.getPlayer(playerName) == null && Bukkit.getOfflinePlayer(playerName) == null) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("The player " + playerName + " doesn't exist" ));
            return;
        }

        UUID playerUUID;
        Player player = Bukkit.getPlayer(playerName);
        if( player == null ) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[3]);
            playerUUID = offlinePlayer.getUniqueId();
            if( !offlinePlayer.hasPlayedBefore() ) {
                sender.sendMessage(ChatFormatter.formatErrorMessage("The player " + ChatColor.GOLD + playerName + ChatColor.RED + " has never played on this server"));
                return;
            }
        } else {
            playerUUID = player.getUniqueId();
        }

        if(boardService.isUserExempted(playerUUID)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Cannot modify user score: the user " + playerName + " is exempted from leaderboards" ));
            return;
        }

        String pointsAmount = args[4];
        if(!NumberUtils.isNumber(pointsAmount)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("The amount selected is not a numeric value" ));
            return;
        }
        Float score = Float.parseFloat(pointsAmount);

        Float totalScore = 0f;
        switch(scoreOperator) {
            case "add":
                totalScore = boardService.addScoreToPlayer(board, playerUUID, score);
                sender.sendMessage(ChatFormatter.formatSuccessMessage("Successfully added " + ChatColor.GOLD + ChatFormatter.thousandSeparator(score.intValue()) + " " + ChatColor.GREEN + board.getUserScoreName() + " to " + ChatColor.GOLD + playerName));
                break;
            case "subtract":
                totalScore = boardService.subtractScoreFromPlayer(board, playerUUID, score);
                sender.sendMessage(ChatFormatter.formatSuccessMessage("Successfully subtracted " + ChatColor.GOLD + ChatFormatter.thousandSeparator(score.intValue()) + " " + ChatColor.GREEN + board.getUserScoreName() + " from " + ChatColor.GOLD + playerName));
                break;
            case "set":
                totalScore = boardService.setScoreOfPlayer(board, playerUUID, score);
                sender.sendMessage(ChatFormatter.formatSuccessMessage("Successfully set " + ChatColor.GOLD + ChatFormatter.thousandSeparator(score.intValue()) + " " + ChatColor.GREEN + board.getUserScoreName() + " to " + ChatColor.GOLD + playerName));
                break;
        }

        sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.GRAY + "New score for " + ChatColor.GOLD + playerName + ChatColor.GRAY + ": " + ChatColor.GREEN + ChatFormatter.thousandSeparator(totalScore.intValue())));
        return;
    }

    private static boolean isValidScoreOperator(String scoreOperator) {
        return Arrays.asList("add","subtract","set").contains(scoreOperator);
    }

}
