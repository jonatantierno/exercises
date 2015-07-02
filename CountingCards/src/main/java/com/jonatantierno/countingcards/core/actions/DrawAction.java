package com.jonatantierno.countingcards.core.actions;

import com.jonatantierno.countingcards.core.Game;
import com.jonatantierno.countingcards.rockygame.RockyGame;
import com.jonatantierno.countingcards.core.Player;

/**
 * Created by jonatan on 31/03/15.
 */
public class DrawAction extends Action{

    public DrawAction(Player player, String raw, String card) {
        super(player, raw, card);
    }

    public DrawAction(Player player, String card){
        super(player, "", card);
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
            return RockyGame.IMPOSSIBLE;
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
