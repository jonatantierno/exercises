package com.jonatantierno.countingcards;

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
        assert possibleAction instanceof PassAction;

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

    @Override
    public Game perform(Game game) {
        Game newGame = game.cloneGame();
        List<String> newHand = newGame.getPile(player);
        if (newHand.contains(card)){
            newHand.remove(card);
        } else if (newHand.contains(Game.UNKNOWN_CARD)){
            newHand.remove(Game.UNKNOWN_CARD);
        } else {
            assert false;
        }
        newGame.passCard(card);
        return newGame;
    }


    @Override
    public boolean isPossible(Game game) {
        // Can Pass the card only if I have it,
        // TODO rest of the possibilities
        return game.getPile(player).contains(card);
    }
}
