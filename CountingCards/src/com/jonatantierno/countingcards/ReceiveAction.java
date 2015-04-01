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
    public List<Game> perform(Game game) {
        List<String> originalHand = game.getPile(player);

        if (Game.UNKNOWN_CARD.equals(card) && player.equals(Player.LIL)){
            assert possibilities.size() > 0;

            List<Game> gameList = new ArrayList<>();

            for(Action possibleAction: possibilities){
                assert possibleAction instanceof ReceiveAction;

                // Can Receive the card only if in Transit,
                if (game.getPile(Player.TRANSIT).contains(possibleAction.card)){
                    Game possibleGame = game.cloneGame();
                    possibleGame.getPile(player).add(possibleAction.card);
                    possibleGame.receiveCard(possibleAction.card);

                    gameList.add(possibleGame);
                }
            }
            return gameList;
        }

        Game newGame = game.cloneGame();
        newGame.getPile(player).add(card);
        newGame.receiveCard(card);

        return Collections.singletonList(newGame);
    }
}
