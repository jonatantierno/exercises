package com.jonatantierno.countingcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jonatan on 31/03/15.
 */
public class ReceiveAction extends Action{
    final Player sender;

    public ReceiveAction(Player p, String raw) {
        super(p, raw);

        this.sender = Player.getPlayerFromRawString(raw);
    }

    @Override
    public Game perform(Game game, int possibilityIndex) {
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
    public Game perform(Game game) {
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
        if(game.anybodyElseHasCard(Player.TRANSIT,card)){
            return false;
        }
        return game.getPile(Player.TRANSIT).contains(card) ||
                game.getPile(Player.TRANSIT).contains(Game.UNKNOWN_CARD);
    }
}
