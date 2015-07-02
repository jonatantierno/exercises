package com.jonatantierno.countingcards.core;

import com.jonatantierno.countingcards.core.actions.Action;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;
import com.jonatantierno.countingcards.rockygame.serializer.PileSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a node in the tree of possibilities of the game.
 * Created by jonatan on 31/03/15.
 */
public class GameNode {
    private static final GameNode IMPOSSIBLE = new GameNode(Player.NULL, Game.IMPOSSIBLE,Collections.<Turn>emptyList(),null);

    public final List<Turn> turns; // This should be an immutable list...
    public final Game game;
    public final GameNode parent;
    public final Player player;

    private List<GameNode> children;

    public GameNode(Player player, Game game, List<Turn> turns) {
        this(player, game,turns,GameNode.IMPOSSIBLE);
    }

    public GameNode(Player player, Game game, List<Turn> turns, GameNode parent) {
        this.turns = turns;
        this.game = game;
        children = null;
        this.parent = parent;
        this.player = player;

    }

    /**
     * This method creates the tree and calculates the solutions with a backtracking algorithm.
     * @return list of leaves of the tree that contain a valid solution.
     */
    public List<GameNode> findSolutions() {
        if (!moreRounds()){
            return Collections.emptyList();
        }

        this.children = buildPossibilities();

        List<GameNode> solutions = new ArrayList<>();
        for (GameNode node : children) {
            if (node.isSolution()) {
                solutions.add(node);
            } else {
                solutions.addAll(node.findSolutions());
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
        if (RockyPlayers.PARTNER.equals(player)) {
            s = new PileSerializer(game).toStringNoName(player);
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

    public List<GameNode> buildPossibilities() {
        ArrayList<GameNode> possibleGames = new ArrayList<>();

        possibleGames.add(buildPossibility(0));

        int numberOfPossibilities = getNextTurn().getNumberOfPossibilities();
        for(int i = 1; i< numberOfPossibilities; i++){
            possibleGames.add(buildPossibility(i));
        }

        return possibleGames;
    }

    private Turn getNextTurn() {
        return turns.get(0);
    }

    public GameNode buildPossibility(int possibilityIndex) {
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

    public Player getNextPlayer() {
        return getNextTurn().getPlayer();
    }

    public GameNode advanceRound() {
        Player firstPlayerInRound = getNextPlayer();

        GameNode node = buildPossibility(0);

        while(node.moreRounds() &&
                !firstPlayerInRound.equals(node.getNextPlayer())){
            node = node.buildPossibility(0);
        }
        return node;

    }


    public boolean moreRounds() {
        return turns.size()>0;
    }
}
