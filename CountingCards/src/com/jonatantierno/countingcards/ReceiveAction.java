package com.jonatantierno.countingcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jonatan on 31/03/15.
 */
public class ReceiveAction extends Action{
    final Player sender;

    public ReceiveAction(Player p, String raw) {
        super(p, raw);

        this.sender = Player.getPlayerFromRawString(raw);
    }

    @Override
    public Game perform(Game game, int possibilityIndex) {
        assert possibilities.size() > possibilityIndex;

        Action possibleAction = possibilities.get(possibilityIndex);
        assert possibleAction instanceof ReceiveAction;

        if (possibleAction.isPossible(game)){
            Game possibleGame = game.cloneGame();
            possibleGame.getPile(player).add(possibleAction.card);
            possibleGame.receiveCard(possibleAction.card);

            return possibleGame;
        } else {
            return Game.IMPOSSIBLE;
        }
    }

    @Override
    public Game perform(Game game) {
        Game newGame = game.cloneGame();
        newGame.getPile(player).add(card);
        newGame.receiveCard(card);

        return newGame;
    }

    @Override
    public boolean isPossible(Game game) {
        // Can Receive the card only if in Transit,
        // TODO rest of the possibilities
        return game.getPile(Player.TRANSIT).contains(card);
    }
}
