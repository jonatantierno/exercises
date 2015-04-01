package com.jonatantierno.countingcards;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonatan on 30/03/15.
 * This class represents an actions that a player performs in her turn
 */
abstract class Action {
    final String raw;
    final String card;
    final Player player;

    final List<Action> possibilities = new ArrayList<>();

    public static Action build(Player p, String raw){
        if (raw.charAt(0)=='-'){
            if(Player.DISCARD.equals(Player.getPlayerFromRawString(raw))) {
                return new DiscardAction(p, raw);
            }
            return new PassAction(p, raw);
        } else if (raw.charAt(0)=='+'){
            if (raw.indexOf(':')==-1){
                return new DrawAction(p, raw);
            } else {
                return new ReceiveAction(p,raw);
            }
        }
        throw new RuntimeException("Invalid input");
    }

    Action(Player p, String raw) {
        this.raw = raw;
        this.player = p;
        this.card = getCard(raw);
    }

    private static String getCard(String raw) {
        int cardEndIndex = raw.indexOf(':');
        if(cardEndIndex == -1){
            cardEndIndex = raw.length();
        }
        return raw.substring(1,cardEndIndex);
    }

    public abstract List<Game> perform(Game game);
}


