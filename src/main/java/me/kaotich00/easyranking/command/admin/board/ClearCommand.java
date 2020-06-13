package me.kaotich00.easyranking.command.admin.board;

import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClearCommand {

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {

        if( args.length < 2 ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not enough arguments, usage:"));
            sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "clear "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "player" + ChatColor.DARK_GRAY + "> "));
            return CommandTypes.COMMAND_SUCCESS;
        }

        UUID playerUUID = null;
        Player player = Bukkit.getPlayer(args[1]);
        if( player == null ) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
            playerUUID = offlinePlayer.getUniqueId();
        } else {
            playerUUID = player.getUniqueId();
        }

        if( playerUUID == null ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("No user found for the name " + args[1]));
            return CommandTypes.COMMAND_SUCCESS;
        }

        String playerName = args[1];

        BoardService boardService = ERBoardService.getInstance();
        boardService.clearUserScores(playerUUID);
        sender.sendMessage(ChatFormatter.formatSuccessMessage("Successfully cleared data for player " + ChatColor.GOLD + playerName));

        return CommandTypes.COMMAND_SUCCESS;
    }

}
