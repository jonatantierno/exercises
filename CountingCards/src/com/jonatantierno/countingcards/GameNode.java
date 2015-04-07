package com.jonatantierno.countingcards;

import com.jonatantierno.countingcards.actions.Action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a node in the tree of possibilities of the game.
 * Created by jonatan on 31/03/15.
 */
public class GameNode {
    private static final GameNode IMPOSSIBLE = new GameNode(Player.NONE, Game.IMPOSSIBLE,Collections.<Turn>emptyList(),null);

    final List<Turn> turns; // This should be an immutable list...
    final Game game;
    List<GameNode> children;
    public final GameNode parent;
    public final Player player;

    public GameNode(Player player, Game g, List<Turn> turns) {
        this(player, g,turns,GameNode.IMPOSSIBLE);
    }

    public GameNode(Player player, Game g, List<Turn> turns, GameNode parent) {
        this.turns = turns;
        game = g;
        children = null;
        this.parent = parent;
        this.player = player;

    }

    /**
     * This method creates the tree and calculates the solutions with a backtracking algorithm.
     * @return list of leaves of the tree that contain a valid solution.
     */
    public List<GameNode> createSolutionTree() {
        if (!moreRounds()){
            return Collections.emptyList();
        }

        this.children = calculateNodeChildren();

        List<GameNode> solutions = new ArrayList<>();
        for (GameNode node : children) {
            if (node.isSolution()) {
                solutions.add(node);
            } else {
                solutions.addAll(node.createSolutionTree());
            }
        }

        return solutions;
    }

    /**
     * Tells if this is a leaf with a valid solution.
     * @return true if valid solution.
     */
    private boolean isSolution() {
        return !moreRounds() && !game.equals(Game.IMPOSSIBLE);
    }

    /**
     * This recursive method prints the solution with the OUTPUT format.
     * Navigates the tree from the leaf to the root.
     * @return solution as a String with several lines.
     */
    public String getResultAsString() {
        String s = "";
        if (player.equals(Player.LIL)) {
            s = game.getPileAsStringNoName(Player.LIL);
        }

        String prev = "";
        if (parent != GameNode.IMPOSSIBLE) {
            prev = parent.getResultAsString();
        }

        String delimiter = "";
        if (!prev.isEmpty() && !s.isEmpty()){
            delimiter = "\n";
        }

        return prev+ delimiter +s;
    }

    public String getPileAsString(Player player) {
        return game.getPileAsString(player);
    }


    public List<GameNode> calculateNodeChildren() {
        ArrayList<GameNode> possibleGames = new ArrayList<>();

        int numberOfPossibilities = getNextTurn().getNumberOfPossibilities();

        if (numberOfPossibilities > 0){
            for(int i = 0; i< numberOfPossibilities; i++){
                GameNode result = calculateNodeChildren(i);
                possibleGames.add(result);
            }
        }
        else {
            possibleGames.add(calculateNodeChildren(0));
        }

        return possibleGames;
    }

    private Turn getNextTurn() {
        return turns.get(0);
    }

    public GameNode calculateNodeChildren(int possibilityIndex) {
        List<Action> actions = getNextTurn().getActions();

        Game newGame = game;
        for(Action action:actions){
            newGame = action.perform(newGame, possibilityIndex);

            if(newGame.equals(Game.IMPOSSIBLE)){
                return GameNode.IMPOSSIBLE;
            }
        }
        return new GameNode(getNextPlayer(), newGame, turns.subList(1,turns.size()),this);
    }

    public boolean isCertain() {
        return getNextTurn().signals.size() == 0;
    }

    Player getNextPlayer() {
        return getNextTurn().getPlayer();
    }

    GameNode advanceRound() {
        Player firstPlayerInRound = getNextPlayer();

        GameNode node = calculateNodeChildren(0);

        while(node.moreRounds() &&
                !firstPlayerInRound.equals(node.getNextPlayer())){
            node = node.calculateNodeChildren(0);
        }
        return node;

    }


    boolean moreRounds() {
        return turns.size()>0;
    }
}
