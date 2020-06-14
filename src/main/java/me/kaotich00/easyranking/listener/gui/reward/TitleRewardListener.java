package me.kaotich00.easyranking.listener.gui.reward;

import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.service.ERRewardService;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Optional;

public class TitleRewardListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        RewardService rewardService = ERRewardService.getInstance();
        Optional<String> title = rewardService.getUserTitleIfActive(event.getPlayer().getUniqueId());

        if(title.isPresent()) {
            event.setFormat( ChatColor.translateAlternateColorCodes('&',title.get() + " " + "&r") + event.getFormat());
        }
    }

}
