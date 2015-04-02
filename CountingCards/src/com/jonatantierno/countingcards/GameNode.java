package com.jonatantierno.countingcards;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a node in the tree of possibilities of the game.
 * Created by jonatan on 31/03/15.
 */
public class GameNode {
    final List<Turn> turns;
    final Game game;

    List<GameNode> children = new ArrayList<>();

    public GameNode(Game g, List<Turn> turns) {
        this.turns = turns;
        game = g;
    }

    public String getPileAsString(Player player) {
        return game.getPileAsString(player);
    }

    public GameNode advanceTurn() {
        List<Action> actions = turns.get(0).getActions();

        Game newGame = game;
        for(Action action:actions){
            newGame = action.perform(newGame);
        }
        return new GameNode(newGame, turns.subList(1,turns.size()));
    }

    public GameNode advanceTurn(int possibilityIndex) {
        List<Action> actions = turns.get(0).getActions();

        Game newGame = game;
        for(Action action:actions){
            if (action.severalPossibilities()){
                newGame = action.perform(newGame,possibilityIndex);
            } else {
                newGame = action.perform(newGame);
            }
        }
        return new GameNode(newGame, turns.subList(1,turns.size()));
    }

    public boolean isCertain() {
        return turns.get(0).signals.size() == 0;
    }

    public Player nextPlayer() {
        return turns.get(0).getPlayer();
    }

    public GameNode advanceRound() {
        return advanceTurn().advanceTurn().advanceTurn().advanceTurn(0);
    }
}
