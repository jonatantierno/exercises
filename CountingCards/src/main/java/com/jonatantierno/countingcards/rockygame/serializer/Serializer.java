package com.jonatantierno.countingcards.rockygame.serializer;

import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.core.actions.Action;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;

/**
 * Created by jonatan on 6/06/15.
 */
public abstract class Serializer<T extends Action> {
    protected final ActionFactory actionFactory = new ActionFactory(RockyPlayers.ROCKY_GAME_PLAYERS);
    protected final CardSerializer cardSerializer = new CardSerializer();

    public abstract String toString(T action);
    public abstract T fromString(Player player, String string);
}
