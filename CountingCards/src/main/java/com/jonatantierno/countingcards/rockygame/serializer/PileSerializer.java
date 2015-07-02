package com.jonatantierno.countingcards.rockygame.serializer;

import com.jonatantierno.countingcards.core.Game;
import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;

import java.util.List;

/**
 * Created by jonatan on 24/06/15.
 */
public class PileSerializer{
    private final Game game;
    public PileSerializer(Game game) {
        this.game = game;
    }

    public String toString(Player player){

        StringBuffer sb = new StringBuffer();
        sb.append(player.name);
        sb.append(':');

        sb.append(toStringNoName(player));

        return sb.toString();
    }

    public String toStringNoName(Player player) {
        List<String> pile= game.getPile(player);

        StringBuffer sb = new StringBuffer();

        boolean first = true;
        for(String card : pile){
            if (first){
                first = false;
            }
            else {
                sb.append(' ');
            }
            sb.append(new CardSerializer().toOutputFormat(card));
        }
        return sb.toString();
    }
}
