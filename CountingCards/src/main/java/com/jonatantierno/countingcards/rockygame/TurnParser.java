package com.jonatantierno.countingcards.rockygame;

import com.jonatantierno.countingcards.core.Player;
import com.jonatantierno.countingcards.core.Turn;
import com.jonatantierno.countingcards.core.actions.Action;
import com.jonatantierno.countingcards.rockygame.serializer.ActionFactory;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by jonatan on 1/07/15.
 */
public class TurnParser {
    private static final ActionFactory actionFactory = new ActionFactory(RockyPlayers.ROCKY_GAME_PLAYERS);

    public Turn readTurn(String raw){

        Scanner scanner = new Scanner(raw);

        Player player = RockyPlayers.ROCKY_GAME_PLAYERS.getPlayerFromString(scanner.next());

        if (player == null){
            throw new RuntimeException("Invalid Format: player string");
        }
        Turn turn = new Turn(player);

        while(scanner.hasNext()){
            turn.addAction(actionFactory.build(player, scanner.next()));
        }
        return turn;
    }

    public void addSignal(Turn turn, Turn signal) {
        assert turn != null;
        assert RockyPlayers.PARTNER.equals(turn.getPlayer());

        if (badNumberOfActions(turn,signal)){
            return;
        }
        ActionFactory actionFactory = new ActionFactory(RockyPlayers.ROCKY_GAME_PLAYERS);

        Iterator<Action> realActionIterator = turn.getActions().iterator();

        for(Action signaledAction:signal.getActions()){
            Action hiddenAction = turn.getNextHiddenAction(realActionIterator);

            hiddenAction.possibilities.add(actionFactory.build(turn.player, signaledAction.getRaw()));
        }
        turn.addSignal(signal);
    }

    private static boolean badNumberOfActions(Turn turn, Turn signalLine) {
        return turn.numberOfHiddenActions() != signalLine.getActions().size();
    }

}
