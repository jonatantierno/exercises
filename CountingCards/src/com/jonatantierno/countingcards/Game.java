package com.jonatantierno.countingcards;

import java.util.*;

/**
 * This class represents the status of the Game at a given point in time
 */
public class Game {

    public static final String UNKNOWN_CARD = "??";
    public static final Game IMPOSSIBLE = new ImpossibleGame();
    private final Map<Player,List<String>> piles;

    public Game() {
        this.piles = new HashMap<>();

        add(Player.SHADY);
        add(Player.ROCKY);
        add(Player.DANNY);
        add(Player.LIL);
        add(Player.DISCARD);
        add(Player.TRANSIT);
    }

    private void add(Player player){
        piles.put(player, new ArrayList<String>());
    }

    public void discard(String card) {
        piles.get(Player.DISCARD).add(card);
    }

    public void passCard(String card) {
        piles.get(Player.TRANSIT).add(card);
    }

    public void receiveCard(String card) {
        piles.get(Player.TRANSIT).remove(card);
    }

    public String getPileAsStringNoName(Player player) {
        List<String> pile= piles.get(player);

        StringBuffer sb = new StringBuffer();

        boolean first = true;
        for(String card : pile){
            if (first){
                first = false;
            }
            else {
                sb.append(' ');
            }
            sb.append(card);
        }
        return sb.toString();
    }

    public String getPileAsString(Player player) {
        StringBuffer sb = new StringBuffer();
        sb.append(player.toString());
        sb.append(':');

        sb.append(getPileAsStringNoName(player));

        return sb.toString();
    }

    public List<String> getPile(Player player) {
        return piles.get(player);
    }

    public List<Game> perform(Action action) {
        return action.performAllPossibilities(this);
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

    public boolean isInPlay(String card) {
        return getPile(Player.LIL).contains(card) ||
                getPile(Player.ROCKY).contains(card) ||
                getPile(Player.DANNY).contains(card) ||
                getPile(Player.SHADY).contains(card) ||
                getPile(Player.TRANSIT).contains(card) ||
                getPile(Player.DISCARD).contains(card);
    }

    public boolean anybodyElseHasCard(Player player, String card){
        return isInPlay(card) && !getPile(player).contains(card);
    }

    private static final class ImpossibleGame extends Game{
        private ImpossibleGame(){
            super();
        }

        @Override
        public String getPileAsStringNoName(Player player) {
            return "IMPOSSIBLE GAME!";
        }
    }
}


