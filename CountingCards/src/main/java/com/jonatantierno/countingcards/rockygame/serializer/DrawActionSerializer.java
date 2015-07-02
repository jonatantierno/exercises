package com.jonatantierno.countingcards.rockygame.serializer;

import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;
import com.jonatantierno.countingcards.core.actions.DrawAction;

/**
 * Created by jonatan on 1/06/15.
 */
public class DrawActionSerializer extends Serializer<DrawAction> {

    public DrawAction fromString(Player player, String raw){
        return new DrawAction(player, raw, actionFactory.getCard(raw));
    }

    public String toString(DrawAction drawAction) {
        return drawAction.player.name+": +"+cardSerializer.toOutputFormat(drawAction.card);
    }
}
