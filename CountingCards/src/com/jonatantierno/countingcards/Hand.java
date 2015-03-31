package com.jonatantierno.countingcards;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the cards that a Player has in her hand at a given moment
 * Created by jonatan on 30/03/15.
 */
class Hand {
    public final Player player;
    public final List<Card> cards;
    public final Game game;

    Hand(Player p) {
        this(p,Game.NULL);
    }

    Hand(Player p, Game g){
        player = p;
        cards = new ArrayList<Card>();
        game = g;
        game.add(this);
    }

    boolean perform(Action action) {
        if (action.isDraw()) {
            cards.add(action.getCard());
            return true;
        } else if (action.isDiscard()){
            if (cards.contains(action.getCard())){
                cards.remove(action.getCard());

                game.discard(action.getCard());
                return true;
            }
            return false;
        }
        return false;
    }

    void perform(List<Action> actions) {
        for(Action action : actions){
            perform(action);
        }
    }
}
