package me.kaotich00.easyranking.command;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.command.admin.board.CreateCommand;
import me.kaotich00.easyranking.command.admin.board.RewardCommand;
import me.kaotich00.easyranking.command.admin.board.ScoreCommand;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import me.kaotich00.easyranking.utils.NameUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class EasyRankingCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( args.length == 0 ) {
            sender.sendMessage(ChatFormatter.helpMessage());
            return CommandTypes.COMMAND_SUCCESS;
        }

        boolean result = CommandTypes.COMMAND_FAILURE;
        switch(args[0]) {
            case CommandTypes.HELP_COMMAND:
                sender.sendMessage(ChatFormatter.helpMessage());
                result = CommandTypes.COMMAND_SUCCESS;
                break;

            case CommandTypes.CREATE_COMMAND:
                result = CreateCommand.executeCommand(sender, command, label, args);
                break;

            case CommandTypes.REWARD_COMMAND:
                result = RewardCommand.executeCommand(sender, command, label, args);
                break;

            case CommandTypes.SCORE_COMMAND:
                result = ScoreCommand.executeCommand(sender, command, label, args);
                break;
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        String argsIndex = "";

        /* Suggest child commands */
        if( args.length == 1 ) {
            argsIndex = args[0];
            suggestions.add("help");
            suggestions.add("create");
            suggestions.add("modify");
            suggestions.add("score");
            suggestions.add("reward");
        }

        /* Suggest Boards names */
        if( args.length == 2 && !args[0].equals(CommandTypes.CREATE_COMMAND) ) {
            argsIndex = args[1];
            BoardService boardService = Easyranking.getBoardService();
            Set<Board> boardsList = boardService.getBoards();
            if( boardsList != null ) {
                for (Board board : boardsList) {
                    suggestions.add(board.getName());
                }
            }
        }

        /* Suggest add/subtract for score command */
        if(args.length == 3 && args[0].equals(CommandTypes.SCORE_COMMAND)) {
            argsIndex = args[2];
            suggestions.add("add");
            suggestions.add("subtract");
            suggestions.add("set");
        }

        /* Suggest player name for score command */
        if(args.length == 4 && args[0].equals(CommandTypes.SCORE_COMMAND)) {
            argsIndex = args[3];
            for( Player p : Bukkit.getOnlinePlayers() ) {
                suggestions.add(p.getDisplayName());
            }
        }

        return NameUtil.filterByStart(suggestions, argsIndex);
    }
}
