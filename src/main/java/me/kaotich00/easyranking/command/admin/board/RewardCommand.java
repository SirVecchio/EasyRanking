package me.kaotich00.easyranking.command.admin.board;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.gui.reward.RewardGUI;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RewardCommand {

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {
        if( !(sender instanceof Player) ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Only players can run that command"));
            return CommandTypes.COMMAND_SUCCESS;
        }

        if( args.length < 2 ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Not enough arguments"));
            return CommandTypes.COMMAND_SUCCESS;
        }

        BoardService boardService = Easyranking.getBoardService();

        String boardName = args[1];
        if( !boardService.isNameAlreadyUsed(boardName) ) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("No board found for the name " + ChatColor.GOLD + boardName + ChatColor.RED ));
            return CommandTypes.COMMAND_SUCCESS;
        }

        RewardGUI gui = new RewardGUI((Player)sender);
        gui.openRewardGUI(1);

        return CommandTypes.COMMAND_SUCCESS;
    }

}
