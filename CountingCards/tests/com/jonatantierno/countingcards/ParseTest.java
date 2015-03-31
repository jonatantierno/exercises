package com.jonatantierno.countingcards;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for parsing the input file
 */
public class ParseTest {
    CountingCards objectUnderTest;

    @Before
    public void setup(){
        objectUnderTest = new CountingCards();
        objectUnderTest.parse(new String[]{"res/SIMPLE_INPUT.txt"});
    }

    @Test
    public void whenFileNameEnteredThenLinesAreProcessed(){
        assertEquals(5, objectUnderTest.getLinesRead().size());
    }

    @Test
    public void shouldParseTurnsAndSignals(){
        assertTrue(objectUnderTest.getLine(0).isTurn());
        assertFalse(objectUnderTest.getLine(0).isSignal());

        assertFalse(objectUnderTest.getLine(4).isTurn());
        assertTrue(objectUnderTest.getLine(4).isSignal());
    }

    @Test
    public void shouldParsePlayers() {
        assertEquals(Player.SHADY, objectUnderTest.getLine(0).getPlayer());
        assertEquals(Player.LIL, objectUnderTest.getLine(3).getPlayer());
        assertEquals(Player.SIGNAL, objectUnderTest.getLine(4).getPlayer());
    }

    @Test
    public void shouldParseActions() {
        assertEquals(2, objectUnderTest.getLine(0).getActions().size());

        List<Action> actionList = objectUnderTest.getLine(3).getActions();

        assertEquals(6, actionList.size());
        assertEquals(Player.ROCKY, ((PassAction) actionList.get(0)).recipient);
    }

    @Test
    public void shouldParseCards(){
        List<Action> actionList = objectUnderTest.getLine(3).getActions();

        assertEquals("6H", actionList.get(0).card);
        assertEquals(Game.UNKNOWN_CARD, actionList.get(1).card);
    }

}
