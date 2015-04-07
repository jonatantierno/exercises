package com.jonatantierno.countingcards.actions;

import com.jonatantierno.countingcards.Game;
import com.jonatantierno.countingcards.Player;

import java.util.ArrayList;
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
    public Game performPossibility(Game game, int possibilityIndex) {
        assert possibilities.size() > possibilityIndex;

        Action possibleAction = possibilities.get(possibilityIndex);
        assert possibleAction instanceof DrawAction;

        if (possibleAction.isPossible(game)){
            Game possibleGame = game.cloneGame();
            possibleGame.getPile(player).add(possibleAction.card);

            return possibleGame;
        } else {
            return Game.IMPOSSIBLE;
        }
    }

    @Override
    public Game performCertain(Game game) {
        Game newGame = game.cloneGame();
        newGame.getPile(player).add(card);
        return newGame;
    }

    @Override
    public boolean isPossible(Game game) {
        if (game.isInPlay(card)){
            return false;
        }
        return true;
    }

}
