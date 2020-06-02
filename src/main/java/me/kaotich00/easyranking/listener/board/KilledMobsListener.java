package me.kaotich00.easyranking.listener.board;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.service.ERRewardService;
import me.kaotich00.easyranking.utils.BoardUtil;
import me.kaotich00.easyranking.utils.ChatFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KilledMobsListener implements Listener {

    @EventHandler
    public void onMobKilled(EntityDeathEvent event) {
        if(!(event.getEntity() instanceof Monster) && !(event.getEntity() instanceof Flying) && !(event.getEntity() instanceof Slime)) {
            return;
        }

        BoardService boardService = ERBoardService.getInstance();
        Player player = event.getEntity().getKiller();
        Board board = boardService.getBoardByName(BoardUtil.MOB_KILLED_BOARD_NAME);

        if(!boardService.getUserData(board,player).isPresent()) {
            boardService.createUserData(board,player);
        }

        float totalScore = boardService.addScoreToPlayer(board, player, 1);
        Bukkit.getServer().broadcastMessage(ChatFormatter.formatSuccessMessage("Successfully added " + ChatColor.GOLD + 1 + ChatColor.GREEN + " points to " + ChatColor.GOLD + player.getPlayerListName()));
        Bukkit.getServer().broadcastMessage((ChatFormatter.formatSuccessMessage(ChatColor.GRAY + "New score for " + ChatColor.GOLD + player.getPlayerListName() + ChatColor.GRAY + ": " + ChatColor.GREEN + totalScore)));

    }

}
