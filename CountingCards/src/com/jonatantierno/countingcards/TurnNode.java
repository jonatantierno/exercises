package com.jonatantierno.countingcards;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a node in the tree of possibilities of the game.
 * Created by jonatan on 31/03/15.
 */
public class TurnNode {
    final List<InputLine> linesRead;
    final Game game;

    public TurnNode(List<InputLine> lines, Game g) {
        linesRead = lines;
        game = g;
    }

    public List<TurnNode> advanceTurn() {
        Iterator<InputLine> iterator = linesRead.iterator();

        Game nextGame = game;

        for (int i = 0; i < 4; i++) {
            if (iterator.hasNext()) {
                InputLine line = iterator.next();
                assert(line.isTurn());

                nextGame = Action.perform(line.getActions(), nextGame);
            } else {
                assert false;
            }
        }
        return Collections.singletonList(new TurnNode(linesRead,nextGame));
    }

    public String getPileAsString(Player player) {
        return game.getPileAsString(player);
    }
}
