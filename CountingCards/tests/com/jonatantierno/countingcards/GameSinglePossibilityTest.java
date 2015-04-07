package com.jonatantierno.countingcards;

import com.jonatantierno.countingcards.actions.Action;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for processing the sample_input file
 */
public class GameSinglePossibilityTest {
    CountingCards objectUnderTest;
    GameNode initialTurn;

    @Before
    public void setup() throws FileNotFoundException {
        initialTurn = CountingCards.parse("res/SAMPLE_INPUT.txt");
    }

    @Test
    public void whenFirstTurnShouldDrawCards() {
        GameNode turn = initialTurn.calculateNodeChildren(0);

        assertEquals("SHADY:?? ?? ?? ??", turn.game.getPileAsString(Player.SHADY));
        assertEquals("ROCKY:", turn.game.getPileAsString(Player.ROCKY));
        assertEquals("DANNY:", turn.game.getPileAsString(Player.DANNY));
        assertEquals("LIL:", turn.game.getPileAsString(Player.LIL));

        assertEquals("DISCARD:", turn.game.getPileAsString(Player.DISCARD));
    }
    @Test
    public void whenFirstRoundShouldDrawCards() {
        GameNode turn = initialTurn.advanceRound();

        assertEquals("SHADY:?? ?? ?? ??", turn.getPileAsString(Player.SHADY));
        assertEquals("ROCKY:QH KD 8S 9C", turn.getPileAsString(Player.ROCKY));
        assertEquals("DANNY:?? ?? ?? ??", turn.getPileAsString(Player.DANNY));
        assertEquals("LIL:8H 9H JS 6H", turn.getPileAsString(Player.LIL));

        assertEquals("DISCARD:", turn.getPileAsString(Player.DISCARD));
    }
    @Test
    public void whenSecondRoundAndChooseOnePossibilityThenOk() {
        // First Round
        GameNode turn = initialTurn.advanceRound();

        // Second Round
        turn = turn.calculateNodeChildren(0).calculateNodeChildren(0).calculateNodeChildren(0);

        assertEquals(Player.LIL, turn.getNextPlayer());
        assertFalse(turn.isCertain());

        turn = turn.calculateNodeChildren(0);

        assertEquals("SHADY:?? ??", turn.getPileAsString(Player.SHADY));
        assertEquals("ROCKY:QH 8S 9C 7H", turn.getPileAsString(Player.ROCKY));
        assertEquals("DANNY:?? ?? ?? ?? ??", turn.getPileAsString(Player.DANNY));
        assertEquals("LIL:9H QS", turn.getPileAsString(Player.LIL));

        assertEquals("DISCARD:QD 2S 8H 10S", turn.getPileAsString(Player.DISCARD));
    }

    @Test
    public void sampleShouldWork() throws FileNotFoundException {
        Scanner expectedScanner = new Scanner(new File("res/SAMPLE_SOLUTION.txt")).useDelimiter("\\n");

        List<GameNode> nodeList = initialTurn.createSolutionTree();

        assertEquals(1, nodeList.size());


        String resultAsString = nodeList.get(0).getResultAsString();
        Scanner actualScanner = new Scanner(resultAsString).useDelimiter("\\n");

        while(expectedScanner.hasNext()) {
            assertTrue(actualScanner.hasNext());

            assertEquals(expectedScanner.nextLine(), actualScanner.nextLine());
        }
    }
}
