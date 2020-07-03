package me.kaotich00.easyranking.command.admin;

import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.command.api.ERAdminCommand;
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

public class ExemptCommand extends ERAdminCommand {

    private final static String EXEMPT_ADD = "add";
    private final static String EXEMPT_REMOVE = "remove";
    private final static String EXEMPT_LIST = "list";

    public void onCommand(CommandSender sender, String[] args) {

        if( args.length < 1 ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not enough arguments, usage:"));
            sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "exempt "  + ChatColor.DARK_GRAY + "[add/remove/list] " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "player" + ChatColor.DARK_GRAY + "> "));
            return;
        }

        if( (args[1].equals(EXEMPT_ADD) || args[1].equals(EXEMPT_REMOVE)) && args.length < 2 ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not enough arguments, usage:"));
            sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "exempt "  + ChatColor.DARK_GRAY + "[add/remove] " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "player" + ChatColor.DARK_GRAY + "> "));
            return;
        }

        UUID playerUUID = null;
        if( (args[1].equals(EXEMPT_ADD) || args[1].equals(EXEMPT_REMOVE)) ) {
            Player player = Bukkit.getPlayer(args[2]);
            if( player == null ) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[2]);
                playerUUID = offlinePlayer.getUniqueId();
            } else {
                playerUUID = player.getUniqueId();
            }
        }

        if( (args[1].equals(EXEMPT_ADD) || args[1].equals(EXEMPT_REMOVE)) && playerUUID == null ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("No user found for the name " + args[2]));
            return;
        }

        String playerName = "";
        BoardService boardService = ERBoardService.getInstance();
        switch(args[1]) {
            case EXEMPT_ADD:
                playerName = args[2];
                if(boardService.isUserExempted(playerUUID)) {
                    sender.sendMessage(ChatFormatter.formatErrorMessage("The user " + playerName + " is already exempted"));
                    return;
                } else {
                    boardService.toggleUserExempt(playerUUID);
                }
                sender.sendMessage(ChatFormatter.formatSuccessMessage("Successfully added " + ChatColor.GOLD + playerName + ChatColor.GREEN + " to exempted players"));
                break;
            case EXEMPT_REMOVE:
                playerName = args[2];
                if(!boardService.isUserExempted(playerUUID)) {
                    sender.sendMessage(ChatFormatter.formatErrorMessage("The user " + playerName + " is not exempted"));
                    return;
                } else {
                    boardService.toggleUserExempt(playerUUID);
                }
                sender.sendMessage(ChatFormatter.formatSuccessMessage("Successfully removed " + ChatColor.GOLD + playerName + ChatColor.GREEN + " from exempted players"));
                break;
            case EXEMPT_LIST:
                sender.sendMessage(ChatFormatter.formatSuccessMessage("List of exempted users:"));
                if( boardService.getExemptedUsers().size() == 0 ) {
                    sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_GRAY + "No users found"));
                } else {
                    for (UUID uuid : boardService.getExemptedUsers()) {
                        String name = "";
                        Player player = Bukkit.getPlayer(uuid);
                        if (player == null) {
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                            if (offlinePlayer != null) {
                                name = offlinePlayer.getName();
                            }
                        } else {
                            name = player.getPlayerListName();
                        }
                        sender.sendMessage(ChatFormatter.formatErrorMessage(ChatColor.GOLD + "> " + ChatColor.YELLOW + name));
                    }
                }
                break;
        }

        return;
    }

}
