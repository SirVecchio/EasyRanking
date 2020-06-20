package me.kaotich00.easyranking.listener.board;

import it.forgottenworld.dungeons.event.DungeonCompletedEvent;
import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.BoardUtil;
import me.kaotich00.easyranking.utils.ChatFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;
import java.util.UUID;

public class FwDungeonListener implements Listener {

    @EventHandler
    public void onDungeonCompleted(DungeonCompletedEvent event) {
        BoardService boardService = ERBoardService.getInstance();

        Optional<Board> optionalBoard = boardService.getBoardById(BoardUtil.DUNGEON_BOARD_ID);

        if( !optionalBoard.isPresent() ) {
            return;
        }

        Board board = optionalBoard.get();

        Bukkit.getServer().broadcastMessage(ChatFormatter.formatSuccessMessage("The following players received " + ChatColor.GOLD + event.getPoints() + ChatColor.GREEN + " points for completing the dungeon"));
        for( UUID playerUUID: event.getPlayers() ) {
            Player player = Bukkit.getPlayer(playerUUID);

            if(boardService.isUserExempted(player.getUniqueId())) {
                return;
            }

            Bukkit.getServer().broadcastMessage(ChatFormatter.formatSuccessMessage(ChatColor.DARK_AQUA + "> " + ChatColor.GREEN + player.getPlayerListName()));
            boardService.addScoreToPlayer(board, player.getUniqueId(), event.getPoints());
        }
    }

}
