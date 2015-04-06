package com.jonatantierno.countingcards;

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
    public Game perform(Game game, int possibilityIndex) {
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
    public Game perform(Game game) {
        Game newGame = game.cloneGame();
        newGame.getPile(player).add(card);
        return newGame;
    }

    @Override
    public List<Game> performAllPossibilities(Game game) {
        if (severalPossibilities()){
            assert possibilities.size() > 0;

            List<Game> gameList = new ArrayList<>();

            for(int i=0; i<possibilities.size(); i++){
                Game possibleGame = perform(game,i);
                gameList.add(possibleGame);
            }
            return gameList;
        }
        return Collections.singletonList(perform(game));
    }

    @Override
    public boolean isPossible(Game game) {
        if (game.isInPlay(card)){
            return false;
        }
        return true;
    }

}
