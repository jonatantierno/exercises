package com.jonatantierno.countingcards.actions;

import com.jonatantierno.countingcards.Game;
import com.jonatantierno.countingcards.Player;

import java.util.List;

/**
 * Created by jonatan on 31/03/15.
 */
public class DiscardAction extends Action{

    public DiscardAction(Player p, String raw) {
        super(p, raw);
    }

    @Override
    public Game performPossibility(Game game, int possibilityIndex) {
        // Never with possibilities because always visible.
        assert false;
        return null;
    }

    @Override
    public Game performCertain(Game game) {
        Game newGame = game.cloneGame();

        List<String> cards = newGame.getPile(player);

        if (cards.contains(card)){
            cards.remove(card);
            newGame.discard(card);
        } else if (cards.contains(Game.UNKNOWN_CARD)){
            cards.remove(Game.UNKNOWN_CARD);
            newGame.discard(card);
        } else {
            return Game.IMPOSSIBLE;
        }
        return newGame;
    }

    @Override
    public boolean isPossible(Game game) {
        // Always possible because always visible
        return true;
    }
}
