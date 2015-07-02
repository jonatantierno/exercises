package com.jonatantierno.countingcards.core.actions;

import com.jonatantierno.countingcards.core.Game;
import com.jonatantierno.countingcards.rockygame.RockyGame;
import com.jonatantierno.countingcards.core.Player;

import java.util.List;

/**
 * Created by jonatan on 31/03/15.
 */
public class PassAction extends Action{
    public final Player recipient;

    public PassAction(Player player, String raw, String card, Player recipient) {
        super(player, raw, card);

        this.recipient = recipient;
    }

    public PassAction(Player player, String card, Player recipient) {
        super(player, "", card);

        this.recipient = recipient;
    }

    @Override
    public Game performPossibility(Game game, int possibilityIndex) {
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
    public Game performCertain(Game game) {
        Game newGame = game.cloneGame();
        List<String> newHand = newGame.getPile(player);

        if (!isPossible(game)){
            return Game.IMPOSSIBLE;
        }

        if (newHand.contains(card)){
            newHand.remove(card);
        } else if (newHand.contains(RockyGame.UNKNOWN_CARD)){
            newHand.remove(RockyGame.UNKNOWN_CARD);
        }
        newGame.passCard(card);
        return newGame;

    }


    @Override
    public boolean isPossible(Game game) {
        // Can Pass the card only if I have it,
        if (game.anybodyElseHasCard(player,card) && !game.getPile(game.transitPile).contains(card)){
            return false;
        }
        if (game.getPile(player).contains(card)){
            return true;
        }
        if (game.getPile(player).contains(RockyGame.UNKNOWN_CARD)){
            return true;
        }
        if (card.equals(RockyGame.UNKNOWN_CARD) && game.getPile(player).size()>0){
            return true;
        }
        return false;
    }

}
