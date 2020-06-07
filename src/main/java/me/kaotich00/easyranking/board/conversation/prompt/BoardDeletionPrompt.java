package me.kaotich00.easyranking.board.conversation.prompt;

import me.kaotich00.easyranking.Easyranking;
import me.kaotich00.easyranking.api.board.Board;
import me.kaotich00.easyranking.api.service.BoardService;
import me.kaotich00.easyranking.service.ERBoardService;
import me.kaotich00.easyranking.utils.ChatFormatter;
import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BoardDeletionPrompt implements ConversationAbandonedListener {

    private ConversationFactory conversationFactory;
    private Board board;

    public BoardDeletionPrompt(Easyranking plugin, Board board) {
        this.board = board;
        this.conversationFactory = new ConversationFactory(plugin)
                .withModality(false)
                .withFirstPrompt(new BoardConfirmDeletePrompt())
                .withEscapeSequence("no")
                .withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Only players can run this conversation")
                .addConversationAbandonedListener(this);
    }

    public void startConversationForPlayer(Player player) {
        conversationFactory.buildConversation(player).begin();
    }

    @Override
    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
        if (abandonedEvent.gracefulExit()) {
            abandonedEvent.getContext().getForWhom().sendRawMessage(ChatFormatter.formatSuccessMessage("Deleted successfully"));
        } else {
            abandonedEvent.getContext().getForWhom().sendRawMessage(ChatFormatter.formatErrorMessage("The board was not deleted"));
        }
    }

    private class BoardConfirmDeletePrompt extends FixedSetPrompt {

        @Override
        public String getPromptText(ConversationContext context) {
            String promptMessage = ChatFormatter.formatSuccessMessage(ChatColor.YELLOW + "Warning: the board will be deleted completely, are you sure? Type 'yes' or 'no'");
            return promptMessage;
        }

        @Override
        protected boolean isInputValid(ConversationContext context, String input) {
            return Arrays.asList("yes","no").contains(input);
        }

        @Override
        protected Prompt acceptValidatedInput(ConversationContext context, String input) {
            BoardService boardService = ERBoardService.getInstance();
            boardService.deleteBoard(board);
            return END_OF_CONVERSATION;
        }

        @Override
        protected String getFailedValidationText(ConversationContext context, String invalidInput) {
            return "Only 'yes' or 'no' options are allowed here!";
        }
    }

}
