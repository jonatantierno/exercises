package com.jonatantierno.countingcards.actions;

import com.jonatantierno.countingcards.Player;

/**
 * Factory for the different kinds of Actions: Draw, Pass, Discard...
 * Created by jonatan on 7/04/15.
 */
public class ActionFactory {
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
}
