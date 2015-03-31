package com.jonatantierno.countingcards;

import java.util.*;

/**
 * This class represents the Game
 * Created by jonatan on 31/03/15.
 */
public class Game {

    public static final String UNKNOWN_CARD = "??";
    private final Map<Player,List<String>> piles;

    public Game() {
        this.piles = new HashMap<>();
    }

    public void add(Player player){
        piles.put(player, new ArrayList<String>());
    }

    public void discard(String card) {
        piles.get(Player.DISCARD).add(card);
    }

    public void passTo(String card, Player recipient) {
        piles.get(recipient).add(card);
    }

    public String getPileAsString(Player player) {
        StringBuffer sb = new StringBuffer();
        sb.append(player.toString());
        sb.append(':');

        List<String> pile= piles.get(player);

        for(String card : pile){
            sb.append(' ');
            sb.append(card);
        }
        return sb.toString();
    }

    public List<String> getPile(Player player) {
        return piles.get(player);
    }

    public Game perform(Action action) {
        return action.perform(this);
    }

    Game cloneGame(){
        Game clone = new Game();
        Iterator<Player> keys = piles.keySet().iterator();

        while(keys.hasNext()){
            Player key = keys.next();
            clone.add(key);
            clone.getPile(key).addAll(piles.get(key));
        }
        return clone;
    }

    private List<String> copyPile(List<String> pile){
        List<String> copy = new ArrayList<>(pile.size());
        copy.addAll(pile);

        return copy;
    }
}

