package com.jonatantierno.countingcards;

import java.util.List;

/**
 * Created by jonatan on 31/03/15.
 */
public class DiscardAction extends Action{

    public DiscardAction(Player p, String raw) {
        super(p, raw);
    }

    @Override
    public Game perform(Game game) {
        Game newGame = game.cloneGame();

        List<String> cards = newGame.getPile(player);

        if (cards.contains(card)){
            cards.remove(card);
            newGame.discard(card);
        } else if (cards.contains(Game.UNKNOWN_CARD)){
            cards.remove(Game.UNKNOWN_CARD);
            newGame.discard(card);
        }
        return newGame;
    }
}
