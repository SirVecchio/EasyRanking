package me.kaotich00.easyranking.command.user;

import me.kaotich00.easyranking.api.service.ScoreboardService;
import me.kaotich00.easyranking.service.ERScoreboardService;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ShowCommand {

    public static boolean executeCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatFormatter.formatErrorMessage("Only players can run that command"));
            return CommandTypes.COMMAND_SUCCESS;
        }

        UUID playerUUID = ((Player) sender).getUniqueId();
        ScoreboardService scoreboardService = ERScoreboardService.getInstance();
        if(!scoreboardService.isPlayerInScoreboard(playerUUID)) {
            scoreboardService.newScoreboard(playerUUID);
        } else {
            scoreboardService.removePlayerFromScoreboard(playerUUID);
        }

        return CommandTypes.COMMAND_SUCCESS;
    }

}
