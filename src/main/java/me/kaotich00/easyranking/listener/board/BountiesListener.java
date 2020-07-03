package me.kaotich00.easyranking.listener.board;

import me.kaotich00.bounties.events.BountyAddEvent;
import me.kaotich00.bounties.events.BountySubtractEvent;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.BoardUtil;
import me.kaotich00.easyranking.utils.ChatFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public class BountiesListener implements Listener {

    @EventHandler
    public void onBountyAdd(BountyAddEvent event) {
        BoardService boardService = ERBoardService.getInstance();

        Optional<Board> optionalBoard = boardService.getBoardById(BoardUtil.BOUNTY_BOARD_ID);

        if( !optionalBoard.isPresent() ) {
            return;
        }

        Board board = optionalBoard.get();

        Player player = Bukkit.getPlayer(event.getPlayerUUID());
        if(boardService.isUserExempted(player.getUniqueId())) {
            return;
        }

        Bukkit.getServer().broadcastMessage(ChatFormatter.formatSuccessMessage("The player" + player.getPlayerListName() + "received " + ChatColor.GOLD + ChatFormatter.thousandSeparator(event.getAmount().intValue()) + ChatColor.GREEN + "$"));
        boardService.addScoreToPlayer(board, player.getUniqueId(), event.getAmount().floatValue());
    }

    @EventHandler
    public void onBountySubtract(BountySubtractEvent event) {
        BoardService boardService = ERBoardService.getInstance();

        Optional<Board> optionalBoard = boardService.getBoardById(BoardUtil.BOUNTY_BOARD_ID);

        if( !optionalBoard.isPresent() ) {
            return;
        }

        Board board = optionalBoard.get();

        Player player = Bukkit.getPlayer(event.getPlayerUUID());
        if(boardService.isUserExempted(player.getUniqueId())) {
            return;
        }

        Bukkit.getServer().broadcastMessage(ChatFormatter.formatSuccessMessage("The player" + player.getPlayerListName() + "lost " + ChatColor.GOLD + ChatFormatter.thousandSeparator(event.getAmount().intValue()) + ChatColor.GREEN + "$"));
        boardService.subtractScoreFromPlayer(board, player.getUniqueId(), event.getAmount().floatValue());
    }

}
