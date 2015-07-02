package com.jonatantierno.countingcards.rockygame.serializer;

import com.jonatantierno.countingcards.core.Game;
import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;
import com.jonatantierno.countingcards.core.actions.Action;
import com.jonatantierno.countingcards.core.actions.ReceiveAction;

/**
 * Factory for the different kinds of Actions: Draw, Pass, Discard...
 * Created by jonatan on 7/04/15.
 */
public class ActionFactory {

    public static final char OTHER_PLAYER_SEPARATOR = ':';
    public static final char ONE_MORE_CARD = '+';
    public static final char ONE_LESS_CARD = '-';

    final RockyPlayers playerSet;

    public ActionFactory(RockyPlayers playerSet) {
        this.playerSet = playerSet;
    }

    public Action build(Player player, String raw) {

        if (raw.charAt(0) == ONE_LESS_CARD) {
            if (RockyPlayers.DISCARD.equals(playerSet.getPlayerFromRawString(raw))) {
                return new DiscardActionSerializer().fromString(player, raw);
            }
            return new PassActionSerializer(playerSet).fromString(player, raw);

        } else if (raw.charAt(0) == ONE_MORE_CARD) {
            if (raw.indexOf(OTHER_PLAYER_SEPARATOR) == -1) {
                return new DrawActionSerializer().fromString(player, raw);
            } else {
                return new ReceiveAction(player, raw, getCard(raw), playerSet.getPlayerFromRawString(raw));
            }
        }
        throw new RuntimeException("Invalid input");
    }

    public String getCard(String raw) {
        int cardEndIndex = raw.indexOf(OTHER_PLAYER_SEPARATOR);
        if (cardEndIndex == -1) {
            cardEndIndex = raw.length();
        }

        String card = raw.substring(1, cardEndIndex);
        card = new CardSerializer().toInnerModel(card);

        return card;
    }
}
