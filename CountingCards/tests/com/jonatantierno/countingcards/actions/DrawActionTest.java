package com.jonatantierno.countingcards.actions;

import com.jonatantierno.countingcards.Game;
import com.jonatantierno.countingcards.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the Draw Action.
 * Created by jonatan on 31/03/15.
 */
public class DrawActionTest {
    Game game;
    @Before
    public void setup(){
        game= new Game();

        game = ActionFactory.build(Player.ROCKY, "+7H").performCertain(game);
    }

    @Test
    public void shouldDraw(){
        game = ActionFactory.build(Player.ROCKY, "+6H").performCertain(game);

        assertTrue(game.getPile(Player.ROCKY).contains("6H"));
    }

    @Test
    public void whenDrawAndCardInPlayThenImpossibleGame(){
        game.getPile(Player.LIL).add("AC");
        game.getPile(Player.ROCKY).add("2C");
        game.getPile(Player.DANNY).add("3C");
        game.getPile(Player.SHADY).add("4C");
        game.getPile(Player.DISCARD).add("5C");
        game.getPile(Player.TRANSIT).add("6C");

        Action draw = ActionFactory.build(Player.LIL, "+??");

        draw.possibilities.add(ActionFactory.build(Player.LIL, "+AC"));
        draw.possibilities.add(ActionFactory.build(Player.LIL, "+2C"));
        draw.possibilities.add(ActionFactory.build(Player.LIL, "+3C"));
        draw.possibilities.add(ActionFactory.build(Player.LIL, "+4C"));
        draw.possibilities.add(ActionFactory.build(Player.LIL, "+5C"));
        draw.possibilities.add(ActionFactory.build(Player.LIL, "+6C"));
        draw.possibilities.add(ActionFactory.build(Player.LIL, "+7C"));

        assertEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 0));
        assertEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 1));
        assertEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 2));
        assertEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 3));
        assertEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 4));
        assertEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 5));
        assertNotEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 6));
    }


    @Test
    public void whenLilDrawsUnknownIfProbabilityChosenThenReturnGame() {

        Action action = ActionFactory.build(Player.LIL, "+??");

        action.possibilities.add(ActionFactory.build(Player.LIL, "+1H"));
        action.possibilities.add(ActionFactory.build(Player.LIL, "+1S"));
        action.possibilities.add(ActionFactory.build(Player.LIL, "+2H"));

        Game game1 = action.performPossibility(game, 0);
        assertEquals("LIL:1H",game1.getPileAsString(Player.LIL));

        Game game2 = action.performPossibility(game, 1);
        assertEquals("LIL:1S", game2.getPileAsString(Player.LIL));
    }

    @Test
    public void whenLilDrawsUnknownCardThenGeneratePossibilities() {

        Action action = ActionFactory.build(Player.LIL, "+??");

        action.possibilities.add(ActionFactory.build(Player.LIL, "+1H"));
        action.possibilities.add(ActionFactory.build(Player.LIL, "+1S"));
        action.possibilities.add(ActionFactory.build(Player.LIL, "+2H"));

        assertEquals("LIL:1H",action.performPossibility(game, 0).getPileAsString(Player.LIL));
        assertEquals("LIL:1S", action.performPossibility(game, 1).getPileAsString(Player.LIL));
        assertEquals("LIL:2H", action.performPossibility(game, 2).getPileAsString(Player.LIL));
    }
}
