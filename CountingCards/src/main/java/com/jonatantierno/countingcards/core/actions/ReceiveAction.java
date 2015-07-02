package com.jonatantierno.countingcards.core.actions;

import com.jonatantierno.countingcards.core.Game;
import com.jonatantierno.countingcards.core.Player;

/**
 * Created by jonatan on 31/03/15.
 */
public class ReceiveAction extends Action{
    public final Player sender;

    public ReceiveAction(Player player, String raw, String card, Player sender) {
        super(player, raw, card);

        this.sender = sender;
    }

    public ReceiveAction(Player player, String card, Player sender) {
        this(player, "", card, sender);
    }

    @Override
    public Game performPossibility(Game game, int possibilityIndex) {
        assert possibilities.size() > possibilityIndex;

        Action possibleAction = possibilities.get(possibilityIndex);

        if (invalidPossibility(possibleAction)){
            return Game.IMPOSSIBLE;
        }

        if (possibleAction.isPossible(game)){
            Game possibleGame = game.cloneGame();
            possibleGame.getPile(player).add(possibleAction.card);
            possibleGame.getPile(sender).remove(possibleAction.card);
            possibleGame.receiveCard(card);

            return possibleGame;
        } else {
            return Game.IMPOSSIBLE;
        }
    }

    private boolean invalidPossibility(Action possibleAction) {
        if(!(possibleAction instanceof ReceiveAction)){
            return true;
        }
        return !((ReceiveAction)possibleAction).sender.equals(sender);
    }

    @Override
    public Game performCertain(Game game) {
        Game newGame = game.cloneGame();

        newGame.receiveCard(card);

        newGame.getPile(player).add(card);

        return newGame;
    }

    @Override
    public boolean isPossible(Game game) {
        // Can Receive the card only if in Transit, or if sender has it.
        if(game.getPile(sender).contains(card)){
            return true;
        }
        if(game.anybodyElseHasCard(game.transitPile,card)){
            return false;
        }
        return game.getPile(game.transitPile).contains(card) ||
                game.getPile(game.transitPile).contains(Game.UNKNOWN_CARD);
    }
}
