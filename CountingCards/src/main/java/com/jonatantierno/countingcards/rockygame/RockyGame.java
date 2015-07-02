package com.jonatantierno.countingcards.rockygame;

import com.jonatantierno.countingcards.core.Game;

/**
 * This class represents the status of the Game at a given point in time
 */
public class RockyGame extends Game {

    public RockyGame(){
        super(RockyPlayers.DISCARD, RockyPlayers.TRANSIT);

        add(RockyPlayers.ADVERSARY_1);
        add(RockyPlayers.FIRST_PERSON);
        add(RockyPlayers.ADVERSARY_2);
        add(RockyPlayers.PARTNER);
    }
}


