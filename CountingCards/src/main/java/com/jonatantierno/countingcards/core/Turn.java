package com.jonatantierno.countingcards.core;

import com.jonatantierno.countingcards.core.actions.Action;

import java.util.*;

/**
 * This class represents a turn, that is, the moves of a player at her turn.
 * Created by jonatan on 30/03/15.
 */
public class Turn {

    public final Player player;
    private final List<Action> actions = new ArrayList<>();
    public final List<Turn> signals = new ArrayList<>();

    public Turn(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void addAction(Action action){
        actions.add(action);
    }

    public void addSignal(Turn signal) {
        signals.add(signal);
    }

    public int numberOfHiddenActions() {
        int hiddenActions = 0;
        for(Action action : actions){
           if (action.card.equals(Game.UNKNOWN_CARD)){
               hiddenActions++;
           }
        }
        return hiddenActions;
    }

    public Action getNextHiddenAction(Iterator<Action> realActionIterator) {
        Action realAction = realActionIterator.next();

        while(!realAction.card.equals(Game.UNKNOWN_CARD)){
            realAction = realActionIterator.next();
        }
        return realAction;
    }

    public int getNumberOfPossibilities() {
        int max = 0;
        for(Action action :getActions()){
            if (action.possibilities.size() > max){
                max =action.possibilities.size();
            }
        }
        return max;
    }
}
