package me.kaotich00.easyranking.utils;

import org.bukkit.ChatColor;

public class ChatFormatter {

    public static String pluginPrefix() {
        return  ChatColor.DARK_GRAY + "[" +
                ChatColor.DARK_GREEN + ChatColor.BOLD + "E" + ChatColor.GREEN + ChatColor.BOLD + "R" +
                ChatColor.DARK_GRAY + "] " +
                ChatColor.RESET;
    }

    public static String chatHeader() {
        return  ChatColor.GREEN + "-------------------[ " +
                ChatColor.DARK_GREEN + ChatColor.BOLD + "Easy" + ChatColor.GREEN + ChatColor.BOLD + "Ranking" +
                ChatColor.GREEN + " ]-------------------";
    }

    public static String chatFooter() {
        return  ChatColor.GREEN + "-----------------------------------------------------";
    }

    public static String formatSuccessMessage(String message) {
        message = pluginPrefix() + ChatColor.GREEN + message;
        return message;
    }

    public static String formatErrorMessage(String message) {
        message = pluginPrefix() + ChatColor.RED + message;
        return message;
    }

    public static String helpMessage() {
        String message = chatHeader();
        message = message.concat(
                "\n" + ChatColor.GOLD + "> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "create " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "nome" + ChatColor.DARK_GRAY + ">" +
                "\n" + ChatColor.GOLD + "> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "modify "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "board_id" + ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "[name/description/maxShownPlayers/suffix] " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "value" + ChatColor.DARK_GRAY + "> " +
                "\n" + ChatColor.GOLD + "> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "reward " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "nome" + ChatColor.DARK_GRAY + ">" +
                "\n" + ChatColor.GOLD + "> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "score "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "nome" + ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "[add/subtract] " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "player" + ChatColor.DARK_GRAY + "> " + "<" + ChatColor.GRAY + "amount" + ChatColor.DARK_GRAY + ">"
        );
        return message;
    }

}
