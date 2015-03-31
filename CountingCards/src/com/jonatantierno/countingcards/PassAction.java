package com.jonatantierno.countingcards;

import java.util.List;

/**
 * Created by jonatan on 31/03/15.
 */
public class PassAction extends Action{
    final Player recipient;

    public PassAction(Player p, String raw) {
        super(p, raw);

        this.recipient = Player.getPlayerFromString(raw.substring(4));
    }

    @Override
    public Game perform(Game game) {
        Game newGame = game.cloneGame();
        List<String> cards = newGame.getPile(player);

        if (cards.contains(card)){
            cards.remove(card);
            newGame.passTo(card, recipient);
        } else if (cards.contains(Game.UNKNOWN_CARD)){
            cards.remove(Game.UNKNOWN_CARD);
            newGame.passTo(card, recipient);
        }
        return newGame;
    }
}
