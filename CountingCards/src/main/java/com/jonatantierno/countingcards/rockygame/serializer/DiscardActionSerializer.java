package com.jonatantierno.countingcards.rockygame.serializer;

import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;
import com.jonatantierno.countingcards.core.actions.DiscardAction;

public class DiscardActionSerializer extends Serializer<DiscardAction> {

    public DiscardAction fromString(Player player, String raw){
        return new DiscardAction(player, raw, actionFactory.getCard(raw));
    }

    public String toString(DiscardAction discardAction) {
        return discardAction.player.name+": -"+cardSerializer.toOutputFormat(discardAction.card)+":discard";
    }
}
