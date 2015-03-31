package com.jonatantierno.countingcards;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for processing the sample_input file
 */
public class GameTest {
    CountingCards objectUnderTest;

    @Before
    public void setup(){
        objectUnderTest = new CountingCards();
        objectUnderTest.parse(new String[]{"res/SAMPLE_INPUT.txt"});
    }

    @Test
    public void whenFirstTurnShouldDrawCards() {
        TurnNode turn = new TurnNode(objectUnderTest.getLinesRead(),objectUnderTest.game);

        List<TurnNode> nextTurnPossibilities = turn.advanceTurn();

        assertEquals(1, nextTurnPossibilities.size());

        TurnNode nextTurn = nextTurnPossibilities.get(0);

        assertEquals("SHADY: ?? ?? ?? ??", nextTurn.getPileAsString(Player.SHADY));
        assertEquals("ROCKY: QH KD 8S 9C", nextTurn.getPileAsString(Player.ROCKY));
        assertEquals("DANNY: ?? ?? ?? ??", nextTurn.getPileAsString(Player.DANNY));
        assertEquals("LIL: 8H 9H JS 6H", nextTurn.getPileAsString(Player.LIL));

        assertEquals("DISCARD:", turn.getPileAsString(Player.DISCARD));
    }

    @Test
    public void shouldHaveNoEffect(){
        Game game = new Game();
        game.add(Player.ROCKY);
        game.add(Player.SHADY);

        game = game.perform(Action.build(Player.ROCKY, "+7C"));
        game = game.perform(Action.build(Player.ROCKY, "-7C:Shady"));

        assertEquals("ROCKY:",game.getPileAsString(Player.ROCKY));
        assertEquals("SHADY: 7C",game.getPileAsString(Player.SHADY));
    }

    @Test
    public void whenSecondTurnThenShouldUnfoldPossibilities() {
        TurnNode turn = new TurnNode(objectUnderTest.getLinesRead(),objectUnderTest.game);
        List<TurnNode> possibilities = turn.advanceTurn();

        assertEquals(3,possibilities.size());

        assertEquals("SHADY: ?? ?? 8H", possibilities.get(0).getPileAsString(Player.SHADY));
        assertEquals("SHADY: ?? ?? 9H", possibilities.get(1).getPileAsString(Player.SHADY));
        assertEquals("SHADY: ?? ?? JS", possibilities.get(2).getPileAsString(Player.SHADY));
    }
}
