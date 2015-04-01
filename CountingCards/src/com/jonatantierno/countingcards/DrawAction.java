package com.jonatantierno.countingcards;

import java.util.Collections;
import java.util.List;

/**
 * Created by jonatan on 31/03/15.
 */
public class DrawAction extends Action{

    public DrawAction(Player p, String raw) {
        super(p, raw);
    }

    @Override
    public List<Game> perform(Game game) {
        Game newGame = game.cloneGame();
        List<String> cards = newGame.getPile(player);

        cards.add(card);
        return Collections.singletonList(newGame);
    }
}
