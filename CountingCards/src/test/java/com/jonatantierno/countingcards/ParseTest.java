package com.jonatantierno.countingcards;

import com.jonatantierno.countingcards.core.actions.Action;
import com.jonatantierno.countingcards.core.actions.PassAction;
import com.jonatantierno.countingcards.core.GameNode;
import com.jonatantierno.countingcards.rockygame.RockyGame;
import com.jonatantierno.countingcards.rockygame.RockyPlayers;
import com.jonatantierno.countingcards.rockygame.serializer.ActionSerializer;
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

    GameNode root;

    @Before
    public void setup() throws FileNotFoundException {
        root = CountingCards.parse(ParseTest.PATH+"SIMPLE_INPUT.txt");
    }

    @Test
    public void shouldReadSignalsIntoActions(){
        List<Action> partnerActions = root.turns.get(3).getActions();

        assertEquals(0,partnerActions.get(0).possibilities.size());

        assertEquals(3,partnerActions.get(1).possibilities.size());

        ActionSerializer serializer = new ActionSerializer();
        assertEquals("Lil: -JS:Shady",serializer.toString(partnerActions.get(1).possibilities.get(0)));
        assertEquals("Lil: -2C:Shady",serializer.toString(partnerActions.get(1).possibilities.get(1)));
        assertEquals("Lil: -QH:Shady",serializer.toString(partnerActions.get(1).possibilities.get(2)));

        assertEquals(0,partnerActions.get(2).possibilities.size());

        assertEquals(3, partnerActions.get(3).possibilities.size());
        assertEquals("Lil: +10S", serializer.toString(partnerActions.get(3).possibilities.get(0)));
        assertEquals("Lil: +JS", serializer.toString(partnerActions.get(3).possibilities.get(1)));
        assertEquals("Lil: +10S", serializer.toString(partnerActions.get(3).possibilities.get(2)));

        assertEquals(0,partnerActions.get(4).possibilities.size());

        assertEquals(3,partnerActions.get(5).possibilities.size());
        assertEquals("Lil: +10H", serializer.toString(partnerActions.get(5).possibilities.get(0)));
        assertEquals("Lil: +9H", serializer.toString(partnerActions.get(5).possibilities.get(1)));
        assertEquals("Lil: +AC", serializer.toString(partnerActions.get(5).possibilities.get(2)));

        assertEquals(0,partnerActions.get(6).possibilities.size());
    }

    @Test
    public void shouldParsePlayers() {
        assertEquals(RockyPlayers.ADVERSARY_1, root.turns.get(0).getPlayer());
        assertEquals(RockyPlayers.PARTNER, root.turns.get(3).getPlayer());
    }

    @Test
    public void shouldParseActions() {
        assertEquals(2, root.turns.get(0).getActions().size());

        List<Action> actionList = root.turns.get(3).getActions();

        assertEquals(7, actionList.size());
        assertEquals(RockyPlayers.FIRST_PERSON, ((PassAction) actionList.get(0)).recipient);
    }

    @Test
    public void shouldParseCards(){
        List<Action> actionList = root.turns.get(3).getActions();

        assertEquals("10H", actionList.get(0).card);
        assertEquals(RockyGame.UNKNOWN_CARD, actionList.get(1).card);
    }

    @Test
    public void shouldParseTwoDigits(){
        List<Action> actionList = root.turns.get(3).getActions();

        assertEquals("10H", actionList.get(0).card);
        assertEquals("10C", actionList.get(6).card);
    }
}
