package com.jonatantierno.countingcards;

import javax.swing.*;
import java.util.*;

/**
 * This class represents a line of input. May be a turn or a signal
 * Created by jonatan on 30/03/15.
 */
class InputLine {
    private final String raw;
    private final Player player;
    private final List<Action> actions = new ArrayList<Action>();

    InputLine(String raw) {
        this.raw = raw;

        Scanner scanner = new Scanner(raw);

        player = Player.getPlayerFromString(scanner.next());

        if (invalidPlayer()){
            throw new RuntimeException("Invalid Format: player string");
        }

        while(scanner.hasNext()){
            actions.add(Action.build(player, scanner.next()));
        }

    }

    private boolean invalidPlayer() {
        return player == null || player.equals(Player.NONE);
    }

    boolean isTurn(){
        return !isSignal();
    }

    boolean isSignal() {
        return player.equals(Player.SIGNAL);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Action> getActions() {
        return actions;
    }
}

