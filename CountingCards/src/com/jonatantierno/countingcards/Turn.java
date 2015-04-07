package com.jonatantierno.countingcards;

import com.jonatantierno.countingcards.actions.Action;
import com.jonatantierno.countingcards.actions.ActionFactory;

import java.util.*;

/**
 * This class represents a turn, that is, the moves of a player at her turn.
 * Created by jonatan on 30/03/15.
 */
class Turn {

    private final String raw;
    private final Player player;
    private final List<Action> actions = new ArrayList<Action>();
    final List<Turn> signals = new ArrayList<Turn>();

    Turn(String raw) {
        this.raw = raw;

        Scanner scanner = new Scanner(raw);

        player = Player.getPlayerFromString(scanner.next());

        if (player == null){
            throw new RuntimeException("Invalid Format: player string");
        }

        while(scanner.hasNext()){
            actions.add(ActionFactory.build(player, scanner.next()));
        }

    }

    public Player getPlayer() {
        return player;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void addSignal(Player p, Turn signalLine) {
        if (badNumberOfActions(signalLine)){
            return;
        }

        Iterator<Action> realActionIterator = actions.iterator();

        for(Action signaledAction:signalLine.actions){
            Action hiddenAction = getNextHiddenAction(realActionIterator);

            hiddenAction.possibilities.add(ActionFactory.build(p, signaledAction.raw));

        }
        signals.add(signalLine);
    }

    private boolean badNumberOfActions(Turn signalLine) {
        return numberOfHiddenActions() != signalLine.actions.size();
    }

    private int numberOfHiddenActions() {
        int hiddenActions = 0;
        for(Action action : actions){
           if (action.card.equals(Game.UNKNOWN_CARD)){
               hiddenActions++;
           }
        }
        return hiddenActions;
    }

    private Action getNextHiddenAction(Iterator<Action> realActionIterator) {
        Action realAction = realActionIterator.next();

        while(!realAction.card.equals(Game.UNKNOWN_CARD)){
            realAction = realActionIterator.next();
        }
        return realAction;
    }

    public int getNumberOfPossibilities() {
        int max = 0;
        for(Action action :actions){
            if (action.possibilities.size() > max){
                max =action.possibilities.size();
            }
        }
        return max;
    }
}
