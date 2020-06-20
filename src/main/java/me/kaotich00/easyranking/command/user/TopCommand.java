package me.kaotich00.easyranking.command.user;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import me.rayzr522.jsonmessage.JSONMessage;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class TopCommand {

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Only players can run that command"));
            return CommandTypes.COMMAND_SUCCESS;
        }

        if( args.length < 2 ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not enough arguments, usage:"));
            sender.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "top "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "board_id" + ChatColor.DARK_GRAY + "> " + "[" + ChatColor.AQUA + "page" + ChatColor.DARK_AQUA + "]" ));
            return CommandTypes.COMMAND_SUCCESS;
        }

        BoardService boardService = ERBoardService.getInstance();

        String boardName = args[1];
        if(!boardService.isIdAlreadyUsed(boardName)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("No board found for the name " + ChatColor.GOLD + boardName));
            return CommandTypes.COMMAND_SUCCESS;
        }
        Board board = boardService.getBoardById(boardName).get();

        Integer page = 1;
        if( args.length == 3 ) {
            if(!NumberUtils.isNumber(args[2])) {
                sender.sendMessage(ChatFormatter.formatErrorMessage("The page number must be a numeric value" ));
                return CommandTypes.COMMAND_SUCCESS;
            }
            page = Integer.parseInt(args[2]);
        }

        List<UUID> userScores = boardService.sortScores(board);
        paginateBoard(sender, board, userScores, page);

        return CommandTypes.COMMAND_SUCCESS;
    }

    private static void paginateBoard(CommandSender sender, Board board, List<UUID> playerList, int page) {

        int maxPlayersPerPage = 15;
        int totalPages = 0;

        if( playerList.size() > 0 ) {
            totalPages = (int) Math.ceil((double) playerList.size() / maxPlayersPerPage);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(ChatFormatter.chatHeader());
        sb.append("Top players for the board " + net.md_5.bungee.api.ChatColor.DARK_AQUA + board.getName());
        sb.append(" \n \n");

        if(playerList.size() == 0) {
            sb.append(ChatColor.DARK_GRAY + "No players found");
        }

        int position = ((page - 1) * maxPlayersPerPage) + 1;
        for(int i = ((page - 1) * maxPlayersPerPage); i < (maxPlayersPerPage * page); i++) {
            if(i >= playerList.size()) {
                sb.append(" \n");
                position++;
                continue;
            }
            UUID uuid = playerList.get(i);
            Player player = Bukkit.getPlayer(uuid);
            if( player == null ) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                if( offlinePlayer != null ) {
                    sb.append("\n" + ChatColor.YELLOW + position + "." + ChatColor.GOLD + " " + offlinePlayer.getName() + ChatColor.DARK_GRAY + " (" + ChatColor.GREEN + ChatFormatter.thousandSeparator(board.getUserScore(uuid).get().intValue()) + " " + board.getUserScoreName() + ChatColor.DARK_GRAY + ")");
                }
            } else {
                sb.append("\n" + ChatColor.YELLOW + position + "." + ChatColor.GOLD + " " + player.getPlayerListName() + ChatColor.DARK_GRAY + " (" + ChatColor.GREEN + ChatFormatter.thousandSeparator(board.getUserScore(uuid).get().intValue()) + " " + board.getUserScoreName() + ChatColor.DARK_GRAY + ")");
            }
            position++;
        }
        sb.append(" \n \n");
        sender.sendMessage(sb.toString());
        sendChatPaginationFooter((Player)sender,board.getId(),page,totalPages);
    }

    public static void sendChatPaginationFooter(Player player, String boardId, int currentPage, int totalPage) {
        JSONMessage message = JSONMessage.create();
        if(currentPage > 1) {
            message.then("[prev] ")
                    .color(ChatColor.DARK_GREEN)
                    .tooltip("Previous page")
                    .runCommand("/er top " + boardId + " " + String.valueOf(currentPage-1));
        } else {
            message.then("------")
                    .color(ChatColor.GREEN);
        }

        message.then("----------------< " + currentPage + "/" + totalPage + " >------------------").color(ChatColor.GREEN);

        if(currentPage < totalPage) {
            message.then(" [next]")
                    .color(ChatColor.DARK_GREEN)
                    .tooltip("Next page")
                    .runCommand("/er top " + boardId + " " + String.valueOf(currentPage+1));
        } else {
            message.then("------")
                    .color(ChatColor.GREEN);
        }

        message.send(player);
    }

}
