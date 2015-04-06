package com.jonatantierno.countingcards;

import org.omg.CORBA.UNKNOWN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jonatan on 31/03/15.
 */
public class PassAction extends Action{
    final Player recipient;

    public PassAction(Player p, String raw) {
        super(p, raw);

        this.recipient = Player.getPlayerFromRawString(raw);
    }

    @Override
    public Game perform(Game game, int possibilityIndex) {
        assert possibilities.size() > possibilityIndex;

        Action possibleAction = possibilities.get(possibilityIndex);

        if (invalidPossibility(possibleAction)){
            return Game.IMPOSSIBLE;
        }

        // Can Pass the card only if I have it,
        if(possibleAction.isPossible(game)){
            Game possibleGame = game.cloneGame();
            List<String> newHand = possibleGame.getPile(player);
            newHand.remove(possibleAction.card);
            possibleGame.passCard(possibleAction.card);
            return possibleGame;
        }
        return Game.IMPOSSIBLE;
    }

    private boolean invalidPossibility(Action possibleAction) {
        if(!(possibleAction instanceof PassAction)){
            return true;
        }
        return !((PassAction)possibleAction).recipient.equals(recipient);
    }

    @Override
    public Game perform(Game game) {
        Game newGame = game.cloneGame();
        List<String> newHand = newGame.getPile(player);
        if (newHand.contains(card)){
            newHand.remove(card);
        } else if (newHand.contains(Game.UNKNOWN_CARD)){
            newHand.remove(Game.UNKNOWN_CARD);
        } else {
            // Do not remove anything.
            // This should never happen, but it does in SAMPLE_INPUT_2.txt
        }
        newGame.passCard(card);
        return newGame;
    }


    @Override
    public boolean isPossible(Game game) {
        // Can Pass the card only if I have it,

        if (game.anybodyElseHasCard(player,card)){
            return false;
        }
        return game.getPile(player).contains(card) ||
               game.getPile(player).contains(Game.UNKNOWN_CARD) ;
    }
}
