package com.jonatantierno.countingcards.rockygame;

import com.jonatantierno.countingcards.core.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Set of Players
 */
public class RockyPlayers {
    public static final Player FIRST_PERSON = new Player("Rocky");
    public static final Player PARTNER = new Player("Lil");
    public static final Player ADVERSARY_1 = new Player("Shady");
    public static final Player ADVERSARY_2 = new Player("Danny");
    public static final Player SIGNAL = new Player("*");
    public static final Player TRANSIT = new Player("Transit");
    public static final Player DISCARD = new Player("discard");

    public static List<Player> PLAYERS = Arrays.asList(FIRST_PERSON, ADVERSARY_1, ADVERSARY_2, PARTNER, DISCARD, SIGNAL, TRANSIT);

    public static final RockyPlayers ROCKY_GAME_PLAYERS = new RockyPlayers(PLAYERS);

    private final Map<String,Player> playerMap;

    public RockyPlayers(List<Player> players) {
        playerMap = new HashMap<>(players.size());

        for(Player player : players){
                playerMap.put(player.name,player);
        }
    }

    public Player getPlayerFromRawString(String s) {
        return playerMap.get(s.substring(s.indexOf(':')+1));
    }
    public Player getPlayerFromString(String s) {
        return playerMap.get(s);
    }
}
