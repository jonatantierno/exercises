package com.jonatantierno.countingcards;

/**
 * Created by jonatan on 30/03/15.
 * This class represents an actions that a player performs in her turn
 */
final class Action {
    final String raw;
    final Player recipient;
    final Card card;

    public Action(String raw) {
        this.raw = raw;

        card = new Card(raw.substring(1,3));

        if (raw.charAt(0)=='-'){
            this.recipient = Player.getPlayerFromString(raw.substring(4));
        }
        else {
            this.recipient = Player.NONE;
        }
    }

    public boolean isPass() {
        return recipient != Player.NONE && recipient != Player.DISCARD;
    }

    public boolean isDraw() {
        return recipient == Player.NONE;
    }

    public boolean isDiscard() {
        return recipient == Player.DISCARD;
    }

    public Card getCard() {
        return card;
    }
}


