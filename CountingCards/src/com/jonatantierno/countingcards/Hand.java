package com.jonatantierno.countingcards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the cards that a Player has in her hand at a given moment
 * Created by jonatan on 30/03/15.
 */
class Hand {
    public final Player player;
    public final List<Card> cards;

    Hand(Player p) {
        player = p;
        cards = new ArrayList<Card>();
    }

    void perform(Action action) {
        if (action.isDraw()) {
            cards.add(action.getCard());
        } else if (action.isDiscard()){
            cards.remove(action.getCard());
        }
    }

    void perform(List<Action> actions) {
        for(Action action : actions){
            perform(action);
        }
    }
}
