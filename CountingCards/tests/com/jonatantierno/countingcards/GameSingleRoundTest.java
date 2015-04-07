package com.jonatantierno.countingcards;

import com.jonatantierno.countingcards.actions.ActionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Tests for processing the sample_input file
 */
public class GameSingleRoundTest {
    CountingCards objectUnderTest;
    GameNode initialTurn;

    @Before
    public void setup() throws FileNotFoundException {
        initialTurn = CountingCards.parse("res/SAMPLE_INPUT_2.txt");
    }

    @Test
    public void whenTooManyPossibilitiesInTurnThenDoNotAdd(){

        Turn turn = new Turn("Lil +??");
        turn.addSignal(Player.LIL, new Turn("* +8H"));
        turn.addSignal(Player.LIL, new Turn("* +9H +4C"));

        assertEquals(1, turn.signals.size());
        assertEquals(1, turn.getActions().get(0).possibilities.size());
        assertEquals("LIL: +8H", turn.getActions().get(0).possibilities.get(0).toString());
    }

    @Test
    public void sample2ShouldWork() throws FileNotFoundException {
        GameNode turn = initialTurn;

        Scanner scanner = new Scanner(new File("res/SAMPLE_SOLUTION_2.txt")).useDelimiter("\\n");


        List<GameNode> solutions = turn.createSolutionTree();
        assertEquals(1, solutions.size());

        String resultAsString = solutions.get(0).getResultAsString();
        Scanner actualScanner = new Scanner(resultAsString).useDelimiter("\\n");

        // Solution contains last round only.Skip.
        actualScanner.next();

        assertEquals(scanner.next(), actualScanner.next());
    }

    @Test
    public void shouldProcessRoundsAccordingToFirstPlayer(){
        assertEquals(Player.LIL, initialTurn.getNextPlayer());

        GameNode turn = initialTurn.advanceRound();

        assertEquals(Player.LIL, turn.getNextPlayer());
    }

    @Test
    public void whenFirstActionInTurnIsKnownShouldObtainNumberOfPossibilities(){
        Game game = new Game();

        Turn impossibleTurn = new Turn("Lil +2H +??");

        assertEquals(0, impossibleTurn.getNumberOfPossibilities());

        impossibleTurn.getActions().get(0).possibilities.add(ActionFactory.build(Player.LIL, "+5H"));
        assertEquals(1, impossibleTurn.getNumberOfPossibilities());

        impossibleTurn.getActions().get(0).possibilities.add(ActionFactory.build(Player.LIL, "+7H"));
        assertEquals(2, impossibleTurn.getNumberOfPossibilities());
    }
    @Test
    public void shouldHandleSeveralPossibleGames(){
        Game game = new Game();
        game.getPile(Player.LIL).add("6H");

        Turn impossibleTurn = new Turn("Lil +??");
        impossibleTurn.getActions().get(0).possibilities.add(ActionFactory.build(Player.LIL, "+5H"));
        impossibleTurn.getActions().get(0).possibilities.add(ActionFactory.build(Player.LIL, "+7H"));

        GameNode gameNode = new GameNode(Player.NONE, game, Collections.singletonList(impossibleTurn));

        List<GameNode> possibleTurns = gameNode.calculateNodeChildren();

        assertEquals(2,possibleTurns.size());
        assertEquals(gameNode,possibleTurns.get(0).parent);
    }

    @Test
    public void shouldMarkImpossibleGamesAsImpossible(){
        Game game = new Game();
        game.getPile(Player.LIL).add("6H");

        Turn impossibleTurn = new Turn("Lil +??");
        impossibleTurn.getActions().get(0).possibilities.add(ActionFactory.build(Player.LIL, "+6H"));

        GameNode gameNode = new GameNode(Player.NONE, game, Collections.singletonList(impossibleTurn));

        List<GameNode> children = gameNode.calculateNodeChildren();

        assertEquals(1,children.size());
        assertEquals(Game.IMPOSSIBLE,children.get(0).game);
    }
}
