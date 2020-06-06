package me.kaotich00.easyranking.command.admin.board;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.data.UserData;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.service.ERRewardService;
import me.kaotich00.easyranking.utils.BoardUtil;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class CollectCommand {

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {

        BoardService boardService = ERBoardService.getInstance();
        Optional<Board> board = boardService.getBoardById(BoardUtil.MOB_KILLED_BOARD_ID);

        if(!board.isPresent()) {
            return CommandTypes.COMMAND_SUCCESS;
        }

        Bukkit.getServer().broadcastMessage(ChatFormatter.chatHeader());
        Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "oOo________[ " + ChatColor.YELLOW + ChatColor.BOLD + "Rank collection day is here" + ChatColor.GRAY + " ]_________oOo\n\n" );

        RewardService rewardService = ERRewardService.getInstance();
        rewardService.collectRewards();

        return CommandTypes.COMMAND_SUCCESS;
    }

}
