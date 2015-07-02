package com.jonatantierno.countingcards.core;

/**
 * This class represents a possible actor of the game, mainly Players.
 * The discard pile and the signals are actors as well
 *
 * Created by jonatan on 30/03/15.
 */
public class Player {

    public static final Player NULL = new Player("-");

    public final String name;

    public Player() {
        this.name = Integer.toString(super.hashCode());
    }

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) return false;
        Player other = (Player) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
