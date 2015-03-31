package com.jonatantierno.countingcards;

import java.util.List;

/**
 * Created by jonatan on 30/03/15.
 * This class represents an actions that a player performs in her turn
 */
abstract class Action {
    final String raw;
    final String card;
    final Player player;

    public static Action build(Player p, String raw){
        if (raw.charAt(0)=='-'){
            if(Player.DISCARD.equals(Player.getPlayerFromString(raw.substring(4)))) {
                return new DiscardAction(p, raw);
            }
            return new PassAction(p, raw);
        }
        return new DrawAction(p, raw);
    }

    Action(Player p, String raw) {
        this.raw = raw;
        this.player = p;

        card = raw.substring(1,3);
    }

    public static Game perform(List<Action> actions, Game game) {
        for(Action action : actions){
            game = action.perform(game);
        }
        return game;
    }

    public abstract Game perform(Game game);
}


