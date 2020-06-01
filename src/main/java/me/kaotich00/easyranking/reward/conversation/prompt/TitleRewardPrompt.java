package me.kaotich00.easyranking.reward.conversation.prompt;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.gui.reward.RewardGUI;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.GUIUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class TitleRewardPrompt extends StringPrompt {

    private Board board;
    private int rankPosition;

    public TitleRewardPrompt(Board board, int rankPosition) {
        this.board = board;
        this.rankPosition = rankPosition;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return ChatFormatter.formatSuccessMessage(ChatColor.GRAY + "Please input a chat displayname title. Ex: &aPrince");
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        Player player = (Player) context.getForWhom();
        RewardService rewardService = Easyranking.getRewardService();

        rewardService.clearTitleReward(this.board, this.rankPosition);
        rewardService.newTitleReward(input, this.board, this.rankPosition);

        RewardGUI rewardSelectionGUI = new RewardGUI(player, this.board, this.rankPosition);
        rewardSelectionGUI.openGUI(GUIUtil.REWARD_TS_STEP);

        player.playSound( player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1 );
        player.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.GRAY + "Successfully set " + ChatColor.translateAlternateColorCodes('&',input) + ChatColor.GRAY + " as title reward"));

        return Prompt.END_OF_CONVERSATION;
    }

}
