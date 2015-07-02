package com.jonatantierno.countingcards.rockygame.serializer;

import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;
import com.jonatantierno.countingcards.core.actions.PassAction;

/**
 * Created by jonatan on 1/06/15.
 */
public class PassActionSerializer extends Serializer<PassAction> {


    private final RockyPlayers playerSet;

    public PassActionSerializer(RockyPlayers playerSet) {
        this.playerSet = playerSet;
    }

    public PassAction fromString(Player player, String raw){
        return new PassAction(player, raw, new ActionFactory(RockyPlayers.ROCKY_GAME_PLAYERS).getCard(raw), playerSet.getPlayerFromRawString(raw));
    }

    public String toString(PassAction passAction) {
        return passAction.player.name+": -"+cardSerializer.toOutputFormat(passAction.card)+":"+passAction.recipient.name;
    }
}
