package com.jonatantierno.countingcards;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

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

        game = Action.build(Player.ROCKY, "+7H").performAllPossibilities(game).get(0);
    }

    @Test
    public void shouldDraw(){
        game = Action.build(Player.ROCKY, "+6H").perform(game);

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

        Action draw = Action.build(Player.LIL,"+??");

        draw.possibilities.add(Action.build(Player.LIL,"+AC"));
        draw.possibilities.add(Action.build(Player.LIL,"+2C"));
        draw.possibilities.add(Action.build(Player.LIL,"+3C"));
        draw.possibilities.add(Action.build(Player.LIL,"+4C"));
        draw.possibilities.add(Action.build(Player.LIL,"+5C"));
        draw.possibilities.add(Action.build(Player.LIL,"+6C"));
        draw.possibilities.add(Action.build(Player.LIL,"+7C"));

        assertEquals(Game.IMPOSSIBLE, draw.perform(game,0));
        assertEquals(Game.IMPOSSIBLE, draw.perform(game,1));
        assertEquals(Game.IMPOSSIBLE, draw.perform(game,2));
        assertEquals(Game.IMPOSSIBLE, draw.perform(game,3));
        assertEquals(Game.IMPOSSIBLE, draw.perform(game,4));
        assertEquals(Game.IMPOSSIBLE, draw.perform(game,5));
        assertNotEquals(Game.IMPOSSIBLE, draw.perform(game,6));
    }


    @Test
    public void whenLilDrawsUnknownIfProbabilityChosenThenReturnGame() {

        Action action = Action.build(Player.LIL, "+??");

        action.possibilities.add(Action.build(Player.LIL, "+1H"));
        action.possibilities.add(Action.build(Player.LIL, "+1S"));
        action.possibilities.add(Action.build(Player.LIL, "+2H"));

        Game game1 = action.perform(game,0);
        assertEquals("LIL:1H",game1.getPileAsString(Player.LIL));

        Game game2 = action.perform(game,1);
        assertEquals("LIL:1S", game2.getPileAsString(Player.LIL));
    }

    @Test
    public void whenLilDrawsUnknownCardThenGeneratePossibilities() {

        Action action = Action.build(Player.LIL, "+??");

        action.possibilities.add(Action.build(Player.LIL, "+1H"));
        action.possibilities.add(Action.build(Player.LIL, "+1S"));
        action.possibilities.add(Action.build(Player.LIL, "+2H"));

        List<Game> gameList = action.performAllPossibilities(game);

        assertEquals(3, gameList.size());

        assertEquals("LIL:1H",gameList.get(0).getPileAsString(Player.LIL));
        assertEquals("LIL:1S",gameList.get(1).getPileAsString(Player.LIL));
        assertEquals("LIL:2H",gameList.get(2).getPileAsString(Player.LIL));
    }
}
