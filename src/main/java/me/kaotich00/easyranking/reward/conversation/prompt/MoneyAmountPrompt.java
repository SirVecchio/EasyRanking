package me.kaotich00.easyranking.reward.conversation.prompt;

import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.RewardService;
import me.kaotich00.easyranking.gui.reward.RewardGUI;
import me.kaotich00.easyranking.service.ERRewardService;
import me.kaotich00.easyranking.utils.ChatFormatter;
import me.kaotich00.easyranking.utils.GUIUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class MoneyAmountPrompt extends NumericPrompt {

    private Board board;
    private int rankPosition;

    public MoneyAmountPrompt(Board board, int rankPosition) {
        this.board = board;
        this.rankPosition = rankPosition;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return ChatFormatter.formatSuccessMessage(ChatColor.GRAY + "Please input a valid positive number (Ex: 12, 15.4 ecc...)");
    }

    @Override
    protected boolean isNumberValid(ConversationContext context, Number input) {
        return input.doubleValue() > 0.0;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
        if (input.equals("None")) {
            return Prompt.END_OF_CONVERSATION;
        }

        Player player = (Player) context.getForWhom();
        RewardService rewardService = ERRewardService.getInstance();

        rewardService.clearMoneyReward(this.board, this.rankPosition);
        rewardService.newMoneyReward(input.doubleValue(), this.board, this.rankPosition);

        RewardGUI rewardSelectionGUI = new RewardGUI(player, this.board, this.rankPosition);
        rewardSelectionGUI.openGUI(GUIUtil.REWARD_TS_STEP);

        player.playSound( player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1 );
        player.sendMessage(ChatFormatter.formatSuccessMessage(ChatColor.GRAY + "Successfully set " + ChatColor.GOLD + input + ChatColor.GRAY + " as money reward"));

        return Prompt.END_OF_CONVERSATION;
    }

    @Override
    protected String getFailedValidationText(ConversationContext context, Number invalidInput) {
        return "Input number must be a positive value.";
    }

}
