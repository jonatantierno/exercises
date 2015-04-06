package com.jonatantierno.countingcards;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests how the cards move between the hands, the draw pile and the discard pile.
 * Created by jonatan on 31/03/15.
 */
public class ReceiveActionTest {
    Game game;
    @Before
    public void setup(){
        game= new Game();

        game = Action.build(Player.ROCKY, "+7H").performAllPossibilities(game).get(0);
    }

    @Test
    public void whenReceiveCardThenRemoveFromTransit(){
        game.getPile(Player.TRANSIT).add("7C");

        game = Action.build(Player.ROCKY, "+7C:Shady").perform(game);

        assertEquals(2, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.ROCKY).contains("7C"));

        assertEquals(0, game.getPile(Player.TRANSIT).size());
    }
    @Test
    public void whenReceiveCardIfFixedThenRemoveFromTransit(){
        game.getPile(Player.TRANSIT).add("7C");

        game = Action.build(Player.ROCKY, "+7C:Shady").performAllPossibilities(game).get(0);

        assertEquals(2, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.ROCKY).contains("7C"));

        assertEquals(0, game.getPile(Player.TRANSIT).size());
    }

    @Test
    public void whenLilReceivesUnknownCardIfProbabilityChosenThenGenerateGame() {
        game.getPile(Player.TRANSIT).add("1H");
        game.getPile(Player.TRANSIT).add("2H");

        Action action = Action.build(Player.LIL, "+??:Shady");

        action.possibilities.add(Action.build(Player.LIL, "+1H:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "+1S:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "+2H:Shady"));

        Game game1 = action.perform(game,0);
        assertEquals("LIL:1H", game1.getPileAsString(Player.LIL));

        Game game2 = action.perform(game,1);
        assertEquals(Game.IMPOSSIBLE, game2);

        Game game3 = action.perform(game,2);
        assertEquals("LIL:2H", game3.getPileAsString(Player.LIL));
    }
    @Test
    public void whenLilReceivesUnknownCardThenGeneratePossibilities() {
        game.getPile(Player.TRANSIT).add("1H");
        game.getPile(Player.TRANSIT).add("2H");

        Action action = Action.build(Player.LIL, "+??:Shady");

        action.possibilities.add(Action.build(Player.LIL, "+1H:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "+1S:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "+2H:Shady"));

        List<Game> gameList = action.performAllPossibilities(game);

        assertEquals(3, gameList.size());

        assertEquals("LIL:1H", gameList.get(0).getPileAsString(Player.LIL));
        assertEquals(Game.IMPOSSIBLE, gameList.get(1));
        assertEquals("LIL:2H", gameList.get(2).getPileAsString(Player.LIL));
    }

    @Test
    public void whenPassUnknownCardAndInSenderThenRemoveItFromHand(){
        game.getPile(Player.TRANSIT).add("??");
        game.getPile(Player.ROCKY).add("1H");

        Action action = Action.build(Player.LIL, "+??:Rocky");
        action.possibilities.add(Action.build(Player.LIL, "+1H:Rocky"));

        Game newGame = action.perform(game, 0);
        assertNotEquals(Game.IMPOSSIBLE, newGame);
        assertFalse(newGame.getPile(Player.ROCKY).contains("1H"));
        assertEquals(0,newGame.getPile(Player.TRANSIT).size());
    }

    @Test
    public void whenPassUnknownCardAndInPlayThenImpossibleGame(){
        game.getPile(Player.TRANSIT).add("??");
        game.getPile(Player.SHADY).add("1H");

        Action action = Action.build(Player.LIL, "+??:Rocky");
        action.possibilities.add(Action.build(Player.LIL, "+1H:Rocky"));

        Game newGame = action.perform(game, 0);
        assertEquals(Game.IMPOSSIBLE, newGame);
    }

    @Test
    public void whenSenderIsNotSameInPossibilityThenImpossibleGame(){
        game.getPile(Player.TRANSIT).add("??");

        Action action = Action.build(Player.LIL, "+??:Rocky");
        action.possibilities.add(Action.build(Player.LIL, "+1H:Shady"));

        Game newGame = action.perform(game, 0);
        assertEquals(Game.IMPOSSIBLE, newGame);
    }
}
