package com.jonatantierno.countingcards;

import com.jonatantierno.countingcards.actions.Action;
import com.jonatantierno.countingcards.actions.PassAction;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for parsing the input file
 */
public class ParseTest {
    public static final String PATH = "src/test/resources/";

    CountingCards objectUnderTest;
    GameNode root;

    @Before
    public void setup() throws FileNotFoundException {
        root = CountingCards.parse(ParseTest.PATH+"SIMPLE_INPUT.txt");
    }

    @Test
    public void shouldReadSignalsIntoActions(){
        List<Action> lilsActions = root.turns.get(3).getActions();

        assertEquals(0,lilsActions.get(0).possibilities.size());

        assertEquals(3,lilsActions.get(1).possibilities.size());
        assertEquals("LIL: -JS:Shady",lilsActions.get(1).possibilities.get(0).toString());
        assertEquals("LIL: -2C:Shady",lilsActions.get(1).possibilities.get(1).toString());
        assertEquals("LIL: -QH:Shady",lilsActions.get(1).possibilities.get(2).toString());

        assertEquals(0,lilsActions.get(2).possibilities.size());

        assertEquals(3, lilsActions.get(3).possibilities.size());
        assertEquals("+10S",lilsActions.get(3).possibilities.get(0).raw);
        assertEquals("+JS",lilsActions.get(3).possibilities.get(1).raw);
        assertEquals("+10S",lilsActions.get(3).possibilities.get(2).raw);

        assertEquals(0,lilsActions.get(4).possibilities.size());

        assertEquals(3,lilsActions.get(5).possibilities.size());
        assertEquals("+10H",lilsActions.get(5).possibilities.get(0).raw);
        assertEquals("+9H",lilsActions.get(5).possibilities.get(1).raw);
        assertEquals("+AC",lilsActions.get(5).possibilities.get(2).raw);

        assertEquals(0,lilsActions.get(6).possibilities.size());
    }

    @Test
    public void shouldParsePlayers() {
        assertEquals(Player.SHADY, root.turns.get(0).getPlayer());
        assertEquals(Player.LIL, root.turns.get(3).getPlayer());
    }

    @Test
    public void shouldParseActions() {
        assertEquals(2, root.turns.get(0).getActions().size());

        List<Action> actionList = root.turns.get(3).getActions();

        assertEquals(7, actionList.size());
        assertEquals(Player.ROCKY, ((PassAction) actionList.get(0)).recipient);
    }

    @Test
    public void shouldParseCards(){
        List<Action> actionList = root.turns.get(3).getActions();

        assertEquals("10H", actionList.get(0).card);
        assertEquals(Game.UNKNOWN_CARD, actionList.get(1).card);
    }

    @Test
    public void shouldParseTwoDigits(){
        List<Action> actionList = root.turns.get(3).getActions();

        assertEquals("10H", actionList.get(0).card);
        assertEquals("10C", actionList.get(6).card);
    }
}
