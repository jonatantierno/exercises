package com.jonatantierno.countingcards;

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

    public void addSignal(Turn signalLine) {
        signals.add(signalLine);

        Iterator<Action> realActionIterator = actions.iterator();

        for(Action signaledAction:signalLine.actions){
            Action hiddenAction = getNextHiddenAction(realActionIterator);

            hiddenAction.possibilities.add(signaledAction);
        }
    }

    private Action getNextHiddenAction(Iterator<Action> realActionIterator) {
        Action realAction = realActionIterator.next();

        while(!realAction.card.equals(Game.UNKNOWN_CARD)){
            realAction = realActionIterator.next();
        }
        return realAction;
    }
}
