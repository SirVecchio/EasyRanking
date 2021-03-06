package me.kaotich00.easyranking.task;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.BoardUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Optional;

public class EconomyBoardTask {

    public static int scheduleEconomy() {
        FileConfiguration defaultConfig = Easyranking.getDefaultConfig();
        Long period = defaultConfig.getLong("economy.sync_frequency") * 1200;

        int taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Easyranking.getPlugin(Easyranking.class), () -> {
            BoardService boardService = ERBoardService.getInstance();
            Optional<Board> optionalBoard = boardService.getBoardById(BoardUtil.ECONOMY_BOARD_SERVICE_ID);

            if( !optionalBoard.isPresent() ) {
                return;
            }

            Board board = optionalBoard.get();

            for( OfflinePlayer player : Bukkit.getOfflinePlayers() ) {
                Double balance = Easyranking.getEconomy().getBalance(player);

                if(boardService.isUserExempted(player.getUniqueId())) {
                    continue;
                }

                boardService.setScoreOfPlayer(board, player.getUniqueId(), balance.floatValue());
            }
        }, period, period );
        return taskId;
    }

}
