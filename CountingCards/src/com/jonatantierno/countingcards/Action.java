package com.jonatantierno.countingcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jonatan on 30/03/15.
 * This class represents an turns that a player performs in her turn
 */
abstract class Action {
    public static  final Action NULL = new NullAction();
    final String raw;
    final String card;
    final Player player;

    final List<Action> possibilities = new ArrayList<>();

    public static Action build(Player p, String raw){
        if (raw.charAt(0)=='-'){
            if(Player.DISCARD.equals(Player.getPlayerFromRawString(raw))) {
                return new DiscardAction(p, raw);
            }
            return new PassAction(p, raw);
        } else if (raw.charAt(0)=='+'){
            if (raw.indexOf(':')==-1){
                return new DrawAction(p, raw);
            } else {
                return new ReceiveAction(p,raw);
            }
        }
        throw new RuntimeException("Invalid input");
    }

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

    public List<Game> performAllPossibilities(Game game) {
        if (severalPossibilities()){
            return performPossibilities(game);
        }
        return Collections.singletonList(perform(game));
    }

    private List<Game> performPossibilities(Game game) {
        assert possibilities.size() > 0;

        List<Game> gameList = new ArrayList<>();

        for(int i=0; i<possibilities.size(); i++){
            gameList.add(perform(game,i));
        }
        return gameList;
    }

    @Override
    public String toString() {
        return player.toString() + ": " + raw;
    }

    public abstract Game perform(Game game, int possibilityIndex);
    public abstract Game perform(Game game);

    public boolean severalPossibilities() {
        return Game.UNKNOWN_CARD.equals(card) && player.equals(Player.LIL);
    }

    public abstract boolean isPossible(Game game);

    public Game performSomehow(Game game, int possibilityIndex) {
        Game newGame;
        if (severalPossibilities()){
            newGame = perform(game,possibilityIndex);
        } else {
            newGame = perform(game);
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
        public Game perform(Game game, int possibilityIndex) {
            return Game.IMPOSSIBLE;
        }

        @Override
        public Game perform(Game game) {
            return Game.IMPOSSIBLE;
        }

        @Override
        public boolean isPossible(Game game) {
            return false;
        }
    }
}



