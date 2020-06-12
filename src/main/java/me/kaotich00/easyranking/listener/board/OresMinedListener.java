package me.kaotich00.easyranking.listener.board;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.BoardUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
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

        if(boardService.isUserExempted(player.getUniqueId())) {
            return;
        }

        Optional<Board> optionalBoard = boardService.getBoardById(BoardUtil.ORES_MINED_BOARD_ID);

        if( !optionalBoard.isPresent() ) {
            return;
        }

        Board board = optionalBoard.get();

        FileConfiguration defaultConfig = Easyranking.getDefaultConfig();
        ConfigurationSection oreSection = defaultConfig.getConfigurationSection("oresMined.values");
        String minedOre = event.getBlock().getType().name();

        Integer score = oreSection.contains(minedOre) ? defaultConfig.getInt("oresMined.values." + minedOre) : 1;
        boardService.addScoreToPlayer(board, player.getUniqueId(), score.floatValue());
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
