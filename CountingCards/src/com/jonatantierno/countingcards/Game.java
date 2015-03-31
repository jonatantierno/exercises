package com.jonatantierno.countingcards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the Game
 * Created by jonatan on 31/03/15.
 */
public class Game {
    public static Game NULL = new NullGame();

    private final Map<Player,Hand> hands;

    public Game() {
        this.hands = new HashMap<>();
    }

    public void add(Hand hand) {
        hands.put(hand.player, hand);
    }

    public void discard(Card card) {
        hands.get(Player.DISCARD).cards.add(card);
    }
}

class NullGame extends Game {
    @Override
    public void discard(Card card) {
        // Do nothing
    }
}

