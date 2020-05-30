package me.kaotich00.easyranking.utils;

import org.bukkit.ChatColor;

public class ChatFormatter {

    public static String pluginPrefix() {
        return  ChatColor.DARK_GRAY + "[" +
                ChatColor.GREEN + "EasyRanking" +
                ChatColor.DARK_GRAY + "] " +
                ChatColor.RESET;
    }

    public static String chatHeader() {
        return  ChatColor.GREEN + "-------------------[ " +
                ChatColor.DARK_GREEN + ChatColor.BOLD + "Easy" + ChatColor.GREEN + ChatColor.BOLD + "Ranking" +
                ChatColor.GREEN + " ]-------------------";
    }

    public static String formatCommandMessage(String message) {

        return message;
    }

    public static String formatErrorMessage(String message) {

        return message;
    }

    public static String helpMessage() {
        String message = chatHeader();
        message = message.concat(
                "\n" + ChatColor.GOLD + "> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "create " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "nome" + ChatColor.DARK_GRAY + ">" +
                "\n" + ChatColor.GOLD + "> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "modify " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "nome" + ChatColor.DARK_GRAY + ">" +
                "\n" + ChatColor.GOLD + "> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "reward " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "nome" + ChatColor.DARK_GRAY + ">" +
                "\n" + ChatColor.GOLD + "> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "score "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "nome" + ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "[add/subtract] " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "player" + ChatColor.DARK_GRAY + "> " + "<" + ChatColor.GRAY + "amount" + ChatColor.DARK_GRAY + ">"
        );
        return message;
    }

}
