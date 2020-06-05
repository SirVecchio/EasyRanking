package me.kaotich00.easyranking.task;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.BoardUtil;
import me.kaotich00.easyranking.utils.ChatFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

public class EconomyBoardTask {

    public static void scheduleEconomy() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Easyranking.getPlugin(Easyranking.class), () -> {
            BoardService boardService = ERBoardService.getInstance();
            Optional<Board> optionalBoard = boardService.getBoardById(BoardUtil.ECONOMY_BOARD_SERVICE_ID);

            if( !optionalBoard.isPresent() ) {
                return;
            }

            Board board = optionalBoard.get();

            for( Player player : Bukkit.getOnlinePlayers() ) {
                Double balance = Easyranking.getEconomy().getBalance(player);

                if(!boardService.getUserData(board,player).isPresent()) {
                    boardService.createUserData(board,player);
                }

                float totalScore = boardService.setScoreOfPlayer(board, player, balance.floatValue());
                Bukkit.getServer().broadcastMessage(ChatFormatter.formatSuccessMessage("Successfully set $" + ChatColor.GOLD + balance + ChatColor.GREEN + " for " + ChatColor.GOLD + player.getPlayerListName()));
                Bukkit.getServer().broadcastMessage((ChatFormatter.formatSuccessMessage(ChatColor.GRAY + "New score for " + ChatColor.GOLD + player.getPlayerListName() + ChatColor.GRAY + ": " + ChatColor.GREEN + totalScore)));

            }
        }, 400L, 6000L );
    }

}
