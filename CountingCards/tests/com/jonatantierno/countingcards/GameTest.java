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
        GameNode turn = new GameNode(objectUnderTest.game,objectUnderTest.getLinesRead());

        GameNode nextTurn = turn.advanceTurn();

        assertEquals("SHADY: ?? ?? ?? ??", nextTurn.game.getPileAsString(Player.SHADY));
        assertEquals("ROCKY:", nextTurn.game.getPileAsString(Player.ROCKY));
        assertEquals("DANNY:", nextTurn.game.getPileAsString(Player.DANNY));
        assertEquals("LIL:", nextTurn.game.getPileAsString(Player.LIL));

        assertEquals("DISCARD:", turn.game.getPileAsString(Player.DISCARD));
    }
    @Test
    public void whenFirstRoundShouldDrawCards() {
        GameNode turn = new GameNode(objectUnderTest.game,objectUnderTest.getLinesRead());

        turn.advanceRound();

        assertEquals("SHADY: ?? ?? ?? ??", turn.getPileAsString(Player.SHADY));
        assertEquals("ROCKY: QH KD 8S 9C", turn.getPileAsString(Player.ROCKY));
        assertEquals("DANNY: ?? ?? ?? ??", turn.getPileAsString(Player.DANNY));
        assertEquals("LIL: 8H 9H JS 6H", turn.getPileAsString(Player.LIL));

        assertEquals("DISCARD:", turn.getPileAsString(Player.DISCARD));
    }
    @Test
    public void whenSecondRoundAndChooseOnePossibilityThenOk() {
        GameNode turn = new GameNode(objectUnderTest.game,objectUnderTest.getLinesRead());

        // First Round
        turn = turn.advanceRound();

        // Second Round
        turn = turn.advanceTurn().advanceTurn().advanceTurn();

        assertEquals(Player.LIL, turn.nextPlayer());
        assertFalse(turn.isCertain());

        turn = turn.advanceTurn(0);

        assertEquals("SHADY: ?? ??", turn.getPileAsString(Player.SHADY));
        assertEquals("ROCKY: QH 8S 9C 7H", turn.getPileAsString(Player.ROCKY));
        assertEquals("DANNY: ?? ?? ?? ?? ??", turn.getPileAsString(Player.DANNY));
        assertEquals("LIL: 9H QS", turn.getPileAsString(Player.LIL));

        assertEquals("DISCARD: QD 2S 8H 10S", turn.getPileAsString(Player.DISCARD));
    }

    @Test
    public void shouldHaveNoEffect(){
        Game game = new Game();
        game.add(Player.ROCKY);
        game.add(Player.SHADY);
        game.add(Player.TRANSIT);

        game = game.perform(Action.build(Player.ROCKY, "+7C")).get(0);
        game = game.perform(Action.build(Player.ROCKY, "-7C:Shady")).get(0);

        assertEquals("ROCKY:",game.getPileAsString(Player.ROCKY));
        assertEquals("SHADY:",game.getPileAsString(Player.SHADY));
    }
}
