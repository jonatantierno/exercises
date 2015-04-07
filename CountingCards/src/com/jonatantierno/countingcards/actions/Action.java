package com.jonatantierno.countingcards.actions;

import com.jonatantierno.countingcards.Game;
import com.jonatantierno.countingcards.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonatan on 30/03/15.
 * This class represents an turns that a player performs in her turn
 */
public abstract class Action {
    public static  final Action NULL = new NullAction();
    public final String raw;
    public final String card;
    public final Player player;

    public final List<Action> possibilities = new ArrayList<>();

    Action(Player p, String raw) {
        this.raw = raw;
        this.player = p;
        this.card = getCard(raw);
    }

    private static String getCard(String raw) {
        int cardEndIndex = raw.indexOf(':');
        if(cardEndIndex == -1){
            cardEndIndex = raw.length();
        }
        return raw.substring(1,cardEndIndex);
    }

    @Override
    public String toString() {
        return player.toString() + ": " + raw;
    }

    public abstract Game performPossibility(Game game, int possibilityIndex);
    public abstract Game performCertain(Game game);
    public abstract boolean isPossible(Game game);

    public boolean severalPossibilities() {
        return Game.UNKNOWN_CARD.equals(card) && player.equals(Player.LIL);
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

    /**
     * Null pattern
     */
    private static final class NullAction extends Action{

        NullAction() {
            super(Player.NONE, "+00");
        }

        @Override
        public Game performPossibility(Game game, int possibilityIndex) {
            return Game.IMPOSSIBLE;
        }

        @Override
        public Game performCertain(Game game) {
            return Game.IMPOSSIBLE;
        }

        @Override
        public boolean isPossible(Game game) {
            return false;
        }
    }
}



