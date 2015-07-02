package com.jonatantierno.countingcards;

import com.jonatantierno.countingcards.core.GameNode;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;
import com.jonatantierno.countingcards.rockygame.serializer.PileSerializer;
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
    GameNode initialTurn;

    @Before
    public void setup() throws FileNotFoundException {
        initialTurn = CountingCards.parse(ParseTest.PATH +"SAMPLE_INPUT.txt");
    }

    @Test
    public void whenFirstTurnShouldDrawCards() {
        GameNode turn = initialTurn.buildPossibility(0);

        PileSerializer serializer = new PileSerializer(turn.game);

        assertEquals("Shady:?? ?? ?? ??", serializer.toString(RockyPlayers.ADVERSARY_1));
        assertEquals("Rocky:", serializer.toString(RockyPlayers.FIRST_PERSON));
        assertEquals("Danny:", serializer.toString(RockyPlayers.ADVERSARY_2));
        assertEquals("Lil:", serializer.toString(RockyPlayers.PARTNER));

        assertEquals("discard:", serializer.toString(RockyPlayers.DISCARD));
    }
    @Test
    public void whenFirstRoundShouldDrawCards() {
        GameNode turn = initialTurn.advanceRound();

        PileSerializer serializer = new PileSerializer(turn.game);

        assertEquals("Shady:?? ?? ?? ??", serializer.toString(RockyPlayers.ADVERSARY_1));
        assertEquals("Rocky:QH KD 8S 9C", serializer.toString(RockyPlayers.FIRST_PERSON));
        assertEquals("Danny:?? ?? ?? ??", serializer.toString(RockyPlayers.ADVERSARY_2));
        assertEquals("Lil:8H 9H JS 6H", serializer.toString(RockyPlayers.PARTNER));

        assertEquals("discard:", serializer.toString(RockyPlayers.DISCARD));
    }
    @Test
    public void whenSecondRoundAndChooseOnePossibilityThenOk() {
        // First Round
        GameNode turn = initialTurn.advanceRound();

        // Second Round
        turn = turn.buildPossibility(0).buildPossibility(0).buildPossibility(0);

        assertEquals(RockyPlayers.PARTNER, turn.getNextPlayer());
        assertFalse(turn.isCertain());

        turn = turn.buildPossibility(0);

        PileSerializer serializer = new PileSerializer(turn.game);

        assertEquals("Shady:?? ??", serializer.toString(RockyPlayers.ADVERSARY_1));
        assertEquals("Rocky:QH 8S 9C 7H", serializer.toString(RockyPlayers.FIRST_PERSON));

        assertEquals("Danny:?? ?? ?? ?? ??", serializer.toString(RockyPlayers.ADVERSARY_2));
        assertEquals("Lil:9H QS", serializer.toString(RockyPlayers.PARTNER));

        assertEquals("discard:QD 2S 8H 10S", serializer.toString(RockyPlayers.DISCARD));
    }

    @Test
    public void sampleShouldWork() throws FileNotFoundException {
        Scanner expectedScanner = new Scanner(new File(ParseTest.PATH+ "SAMPLE_SOLUTION.txt")).useDelimiter("\\n");

        List<GameNode> nodeList = initialTurn.findSolutions();

        assertEquals(1, nodeList.size());


        String resultAsString = nodeList.get(0).getResultAsString();
        Scanner actualScanner = new Scanner(resultAsString).useDelimiter("\\n");

        while(expectedScanner.hasNext()) {
            assertTrue(actualScanner.hasNext());

            assertEquals(expectedScanner.nextLine(), actualScanner.nextLine());
        }
    }
}
