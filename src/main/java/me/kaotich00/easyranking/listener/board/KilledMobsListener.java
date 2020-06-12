package me.kaotich00.easyranking.listener.board;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.BoardUtil;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Optional;

public class KilledMobsListener implements Listener {

    @EventHandler
    public void onMobKilled(EntityDeathEvent event) {
        if(!(event.getEntity() instanceof Monster) && !(event.getEntity() instanceof Flying) && !(event.getEntity() instanceof Slime)) {
            return;
        }

        if(!(event.getEntity().getKiller() instanceof Player)) {
            return;
        }

        BoardService boardService = ERBoardService.getInstance();
        Player player = event.getEntity().getKiller();

        if(boardService.isUserExempted(player.getUniqueId())) {
            return;
        }

        Optional<Board> optionalBoard = boardService.getBoardById(BoardUtil.MOB_KILLED_BOARD_ID);

        if( !optionalBoard.isPresent() ) {
            return;
        }

        Board board = optionalBoard.get();
        boardService.addScoreToPlayer(board, player.getUniqueId(), 1f);
    }

}
