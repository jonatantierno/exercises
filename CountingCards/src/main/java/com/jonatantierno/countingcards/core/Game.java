package com.jonatantierno.countingcards.core;

import com.jonatantierno.countingcards.core.actions.Action;

import java.util.*;

/**
 * This class represents the state of a game at a given moment. Performing an action in the game (a move such as passing
 * a card), generates a new game.
 *
 * Created by jonatan on 29/06/15.
 */
public class Game {

    public static final String UNKNOWN_CARD = "UNKNOWN_CARD";

    public static final Game IMPOSSIBLE = new ImpossibleGame();

    private final Map<Player,List<String>> piles;

    public final Player discardPile, transitPile;

    public Game(Player discardPile, Player transitPile) {
        this.piles = new HashMap<>();

        this.discardPile = discardPile;
        this.transitPile = transitPile;

        add(discardPile);
        add(transitPile);
    }

    public Game() {
        this(new Player(),new Player());
    }

    public void add(Player player){
        piles.put(player, new ArrayList<String>());
    }

    public void discard(String card) {
        piles.get(discardPile).add(card);
    }

    public void passCard(String card) {
        piles.get(transitPile).add(card);
    }

    public void receiveCard(String card) {
        piles.get(transitPile).remove(card);
    }

    public List<String> getPile(Player player) {
        return piles.get(player);
    }

    public Game cloneGame(){
        Game clone = new Game(discardPile,transitPile);
        Iterator<Player> keys = piles.keySet().iterator();

        while(keys.hasNext()){
            Player key = keys.next();
            clone.add(key);
            clone.getPile(key).addAll(piles.get(key));
        }
        return clone;
    }

    public boolean isInPlay(String card) {
        Iterator<Player> players = piles.keySet().iterator();

        while(players.hasNext()){
            if (getPile(players.next()).contains(card)){
                return true;
            }
        }
        return false;
    }

    public boolean anybodyElseHasCard(Player player, String card){
        return isInPlay(card) && !getPile(player).contains(card);
    }

    /**
     * Same as Action.perform(), but easier to read.
     * @param action
     */
    public Game play(Action action) {
        return action.performCertain(this);
    }

    public boolean discardPileContains(String raw) {
        return getPile(discardPile).contains(raw);
    }

    private static final class ImpossibleGame extends Game {
        private ImpossibleGame(){
            super();
        }
    }
}
