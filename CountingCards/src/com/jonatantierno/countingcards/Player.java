package com.jonatantierno.countingcards;

import java.util.HashMap;
import java.util.Map;

/**
 * This enumeration represents a possible player of an action
 *
 * Created by jonatan on 30/03/15.
 */
public enum Player{
    SHADY, ROCKY, DANNY, LIL, SIGNAL, DISCARD, NONE;

    private static final Map<String,Player> playerMap = initPlayerMap();

    private static Map<String, Player> initPlayerMap() {
        Map map = new HashMap<String,Player>();

        map.put("Shady", Player.SHADY);
        map.put("Rocky", Player.ROCKY);
        map.put("Danny", Player.DANNY);
        map.put("Lil", Player.LIL);
        map.put("*", Player.SIGNAL);
        map.put("discard", Player.DISCARD);

        return map;
    }

    public static Player getPlayerFromString(String s) {
        return playerMap.get(s);
    }
}
