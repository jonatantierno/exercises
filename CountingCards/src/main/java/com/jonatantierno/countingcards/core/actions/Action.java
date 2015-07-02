package com.jonatantierno.countingcards.core.actions;

import com.jonatantierno.countingcards.core.Game;
import com.jonatantierno.countingcards.rockygame.RockyGame;
import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonatan on 30/03/15.
 * This class represents an action that a player performs in her turn
 */
public abstract class Action {
    private final String raw;
    public final String card;
    public final Player player;

    public final List<Action> possibilities = new ArrayList<>();

    Action(Player player, String raw, String card) {
        this.raw = raw;
        this.player = player;
        this.card = card;
    }

    public abstract Game performPossibility(Game game, int possibilityIndex);
    public abstract Game performCertain(Game game);
    public abstract boolean isPossible(Game game);

    public boolean severalPossibilities() {
        return RockyGame.UNKNOWN_CARD.equals(card) && player.equals(RockyPlayers.PARTNER);
    }

    public Game perform(Game game, int possibilityIndex) {
        Game newGame;
        if (severalPossibilities()){
            newGame = performPossibility(game, possibilityIndex);
        } else {
            newGame = performCertain(game);
        }
        return newGame;
    }

    public String getRaw() {
        return raw;
    }
}



