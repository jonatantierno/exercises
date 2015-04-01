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
    public List<Game> perform(Game game) {
        if (Game.UNKNOWN_CARD.equals(card) && player.equals(Player.LIL)){
            return performPossibilities(game);
        }
        return performSinglePossibility(game);
    }

    private List<Game> performSinglePossibility(Game game) {
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
        return Collections.singletonList(newGame);
    }

    private List<Game> performPossibilities(Game game) {
        assert possibilities.size() > 0;

        List<Game> gameList = new ArrayList<>();

        for(Action possibleAction: possibilities){
            assert possibleAction instanceof PassAction;

            // Can Pass the card only if I have it,
            if(game.getPile(player).contains(possibleAction.card)){
                Game possibleGame = game.cloneGame();

                List<String> newHand = possibleGame.getPile(player);
                newHand.remove(possibleAction.card);
                possibleGame.passCard(possibleAction.card);
                gameList.add(possibleGame);
            }
        }
        return gameList;
    }
}
