package com.jonatantierno.countingcards.rockygame.serializer;

import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;
import com.jonatantierno.countingcards.core.actions.ReceiveAction;

/**
 * Created by jonatan on 1/06/15.
 */
public class ReceiveActionSerializer extends Serializer<ReceiveAction> {

    private final RockyPlayers playerSet;

    public ReceiveActionSerializer(RockyPlayers playerSet) {
        this.playerSet = playerSet;
    }

    public ReceiveAction fromString(Player player, String raw){
        return new ReceiveAction(player, raw, actionFactory.getCard(raw), playerSet.getPlayerFromRawString(raw));
    }

    public String toString(ReceiveAction receiverAction) {
        return receiverAction.player.name+": -"+cardSerializer.toOutputFormat(receiverAction.card)+":"+receiverAction.sender.name;
    }
}
