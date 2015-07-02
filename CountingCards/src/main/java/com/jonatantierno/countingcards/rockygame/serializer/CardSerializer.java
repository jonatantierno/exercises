package com.jonatantierno.countingcards.rockygame.serializer;

import com.jonatantierno.countingcards.core.Game;

/**
 * Created by jonatan on 2/07/15.
 */
public class CardSerializer {
    public static final String UNKNOWN_CARD = "??";


    public String toOutputFormat(String card) {
        if (Game.UNKNOWN_CARD.equals(card)) {
            return CardSerializer.UNKNOWN_CARD;
        }
        return card;
    }

    public String toInnerModel(String card) {
        if (CardSerializer.UNKNOWN_CARD.equals(card)) {
            return Game.UNKNOWN_CARD;
        }
        return card;
    }
}
