package me.kaotich00.easyranking.utils;

import org.bukkit.ChatColor;

import java.text.NumberFormat;
import java.util.Locale;

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

    public static String thousandSeparator(Integer value) {
        return NumberFormat.getNumberInstance(Locale.ITALY).format(value);
    }

    public static String helpMessage() {
        String message = chatHeader();
        message = message.concat(
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "create " + ChatColor.DARK_GRAY +
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "info " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "board_id" + ChatColor.DARK_GRAY + "> " +
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "modify "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "board_id" + ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "name/description/maxShownPlayers/suffix" + ChatColor.DARK_AQUA + "] " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "value" + ChatColor.DARK_GRAY + "> " +
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "delete "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "board_id" + ChatColor.DARK_GRAY + "> " +
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "reward " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "board_id" + ChatColor.DARK_GRAY + ">" +
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "score "  + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "board_id" + ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "add/subtract" + ChatColor.DARK_AQUA + "] " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "player" + ChatColor.DARK_GRAY + "> " + "<" + ChatColor.GRAY + "amount" + ChatColor.DARK_GRAY + ">" +
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "top " +  ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "board_id" + ChatColor.DARK_GRAY + "> " + ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "page" + ChatColor.DARK_AQUA + "]" +
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "exempt " + ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "list/add/remove" + ChatColor.DARK_AQUA + "] " + ChatColor.DARK_GRAY + "<" + ChatColor.GRAY + "player" + ChatColor.DARK_GRAY + "> " +
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "collect " +
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "reload" +
                "\n" + ChatColor.GRAY + ">> " + ChatColor.DARK_GREEN + "/er " + ChatColor.GREEN + "credits"
        );
        return message;
    }

    public static String creditsMessage() {
        String message = chatHeader();
        message = message.concat(
                "\n" + ChatColor.GOLD + "Author: " + ChatColor.GREEN + "Kaotich00" +
                "\n" + ChatColor.GOLD + "Web UI/UX: " + ChatColor.GREEN + "OhMeMuffin" +
                "\n" + ChatColor.GOLD + "Web API: " + ChatColor.GREEN + "Kaotich00"
        );
        return message;
    }



}
