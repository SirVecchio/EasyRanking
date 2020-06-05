package me.kaotich00.easyranking.listener.board;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.BoardUtil;
import me.kaotich00.easyranking.utils.ChatFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OresMinedListener implements Listener {

    List<Material> oreMaterials = Arrays.asList(
            Material.DIAMOND_ORE,
            Material.GOLD_ORE,
            Material.IRON_ORE,
            Material.COAL_ORE,
            Material.EMERALD_ORE,
            Material.LAPIS_ORE,
            Material.NETHER_QUARTZ_ORE,
            Material.REDSTONE_ORE
    );

    @EventHandler
    public void onOreMined(BlockBreakEvent event) {
        if( event.getPlayer().getGameMode().equals(GameMode.CREATIVE) ) {
            return;
        }

        if( !oreMaterials.contains(event.getBlock().getType()) ) {
            return;
        }

        if( !event.getBlock().getMetadata("PLACED").isEmpty() ) {
            return;
        }

        BoardService boardService = ERBoardService.getInstance();
        Player player = event.getPlayer();
        Optional<Board> optionalBoard = boardService.getBoardById(BoardUtil.ORES_MINED_BOARD_ID);

        if( !optionalBoard.isPresent() ) {
            return;
        }

        Board board = optionalBoard.get();

        // TODO - CUSTOM ORE VALUES FROM CONFIG

        if(!boardService.getUserData(board,player).isPresent()) {
            boardService.createUserData(board,player);
        }

        float totalScore = boardService.addScoreToPlayer(board, player, 1);
        Bukkit.getServer().broadcastMessage(ChatFormatter.formatSuccessMessage("Successfully added " + ChatColor.GOLD + 1 + ChatColor.GREEN + " points to " + ChatColor.GOLD + player.getPlayerListName()));
        Bukkit.getServer().broadcastMessage((ChatFormatter.formatSuccessMessage(ChatColor.GRAY + "New score for " + ChatColor.GOLD + player.getPlayerListName() + ChatColor.GRAY + ": " + ChatColor.GREEN + totalScore)));

    }

    @EventHandler
    public void onOrePlaced(BlockPlaceEvent event) {
        if( !oreMaterials.contains(event.getBlock().getType()) ) {
            return;
        }

        Block block = event.getBlock();
        block.setMetadata("PLACED", new FixedMetadataValue(Easyranking.getPlugin(Easyranking.class), true));
    }

}
