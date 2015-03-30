package com.jonatantierno.countingcards;

/**
 * This class represents a card in the deck.
 * Created by jonatan on 30/03/15.
 */
public final class Card {
    public static final Card UNKNOWN = new Card("??");

    public final String raw;

    Card(String raw) {
        this.raw = raw;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Card){
            Card c = (Card) obj;

            return raw.equals(c.raw);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return raw.hashCode();
    }
}
