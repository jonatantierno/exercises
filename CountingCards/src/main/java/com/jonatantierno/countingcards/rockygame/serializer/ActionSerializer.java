package com.jonatantierno.countingcards.rockygame.serializer;

import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;
import com.jonatantierno.countingcards.core.actions.Action;
import com.jonatantierno.countingcards.core.actions.DrawAction;
import com.jonatantierno.countingcards.core.actions.PassAction;

/**
 * Created by jonatan on 29/06/15.
 */
public class ActionSerializer extends Serializer<Action> {
    @Override
    public String toString(Action action) {
        if (action instanceof PassAction){
            return new PassActionSerializer(RockyPlayers.ROCKY_GAME_PLAYERS).toString((PassAction) action);
        }
        if (action instanceof DrawAction){
            return new DrawActionSerializer().toString((DrawAction) action);
        }
        return null;
    }

    @Override
    public Action fromString(Player player, String string) {
        return null;
    }
}
