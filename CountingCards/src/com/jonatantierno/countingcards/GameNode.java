package com.jonatantierno.countingcards;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a node in the tree of possibilities of the game.
 * Created by jonatan on 31/03/15.
 */
public class GameNode {
    final List<Action> actions;
    final Game game;

    List<GameNode> children = new ArrayList<>();

    public GameNode(Game g, List<Action> actions) {
        this.actions = actions;
        game = g;
    }

    public String getPileAsString(Player player) {
        return game.getPileAsString(player);
    }

    public List<GameNode> advanceAction() {
        Action action = actions.get(0);
        List<Game> gameList = action.perform(game);

        for(Game possibleGame : gameList){
            children.add(new GameNode(possibleGame, getRemainingActions()));
        }
        return children;
    }

    private List<Action> getRemainingActions() {
        List<Action> nextActions= new ArrayList<Action>();
        nextActions.addAll(actions.subList(1, actions.size()));
        return nextActions;
    }

    public List<GameNode> advanceTurn() {
        return null;
    }
}
