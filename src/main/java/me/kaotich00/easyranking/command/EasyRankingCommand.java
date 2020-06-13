package me.kaotich00.easyranking.command;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.command.admin.board.*;
import me.kaotich00.easyranking.command.user.CreditsCommand;
import me.kaotich00.easyranking.command.user.InfoCommand;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.CommandTypes;
import me.kaotich00.easyranking.utils.NameUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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

            case CommandTypes.DELETE_COMMAND:
                result = DeleteCommand.executeCommand(sender, command, label, args);
                break;

            case CommandTypes.MODIFY_COMMAND:
                result = ModifyCommand.executeCommand(sender, command, label, args);
                break;

            case CommandTypes.INFO_COMMAND:
                result = InfoCommand.executeCommand(sender, command, label, args);
                break;

            case CommandTypes.REWARD_COMMAND:
                result = RewardCommand.executeCommand(sender, command, label, args);
                break;

            case CommandTypes.SCORE_COMMAND:
                result = ScoreCommand.executeCommand(sender, command, label, args);
                break;

            case CommandTypes.COLLECT_COMMAND:
                result = CollectCommand.executeCommand(sender, command, label, args);
                break;

            case CommandTypes.RELOAD_COMMAND:
                result = ReloadCommand.executeCommand(sender, command, label, args);
                break;

            case CommandTypes.EXEMPT_COMMAND:
                result = ExemptCommand.executeCommand(sender, command, label, args);
                break;

            case CommandTypes.CLEAR_COMMAND:
                result = ClearCommand.executeCommand(sender, command, label, args);
                break;

            case CommandTypes.CREDITS_COMMAND:
                result = CreditsCommand.executeCommand(sender, command, label, args);
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
            /* Admin commands */
            suggestions.add("create");
            suggestions.add("modify");
            suggestions.add("delete");
            suggestions.add("score");
            suggestions.add("reward");
            suggestions.add("collect");
            suggestions.add("reload");
            suggestions.add("exempt");
            suggestions.add("clear");
            suggestions.add("credits");
            /* User commands */
            suggestions.add("help");
            suggestions.add("info");
        }

        if( args.length == 2 && (   args[0].equals(CommandTypes.MODIFY_COMMAND) ||
                                    args[0].equals(CommandTypes.SCORE_COMMAND) ||
                                    args[0].equals(CommandTypes.DELETE_COMMAND) ||
                                    args[0].equals(CommandTypes.REWARD_COMMAND) ||
                                    args[0].equals(CommandTypes.INFO_COMMAND))) {
            argsIndex = args[1];
            BoardService boardService = ERBoardService.getInstance();
            Set<Board> boardsList = boardService.getBoards();
            if( boardsList != null ) {
                for (Board board : boardsList) {
                    suggestions.add(board.getId());
                }
            }
        }

        if(args.length == 2 && args[0].equals(CommandTypes.EXEMPT_COMMAND)) {
            argsIndex = args[1];
            suggestions.add("add");
            suggestions.add("remove");
            suggestions.add("list");
        }

        if(args.length == 3) {
            argsIndex = args[2];
            switch(args[0]) {
                case CommandTypes.SCORE_COMMAND:
                    suggestions.add("add");
                    suggestions.add("subtract");
                    suggestions.add("set");
                    break;
                case CommandTypes.MODIFY_COMMAND:
                    suggestions.add("name");
                    suggestions.add("description");
                    suggestions.add("maxShownPlayers");
                    suggestions.add("suffix");
                    break;
                case CommandTypes.EXEMPT_COMMAND:
                case CommandTypes.CLEAR_COMMAND:
                    if(args[2].equals("list")) {
                        break;
                    }
                    for( Player p : Bukkit.getOnlinePlayers() ) {
                        suggestions.add(p.getPlayerListName());
                    }
                    break;
            }
        }

        /* Suggest player name for score command */
        if(args.length == 4 && args[0].equals(CommandTypes.SCORE_COMMAND)) {
            argsIndex = args[3];
            for( Player p : Bukkit.getOnlinePlayers() ) {
                suggestions.add(p.getPlayerListName());
            }
        }

        return NameUtil.filterByStart(suggestions, argsIndex);
    }
}
