package com.jonatantierno.countingcards.actions;

import com.jonatantierno.countingcards.Game;
import com.jonatantierno.countingcards.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests how the cards move between the hands, the draw pile and the discard pile.
 * Created by jonatan on 31/03/15.
 */
public class PassActionTest {
    Game game;
    @Before
    public void setup(){
        game= new Game();

        game = ActionFactory.build(Player.ROCKY, "+7H").performCertain(game);
    }

    @Test
    public void shouldPassCard(){
        game = ActionFactory.build(Player.ROCKY, "-7H:Shady").performCertain(game);

        assertEquals(0, game.getPile(Player.ROCKY).size());
        assertFalse(game.getPile(Player.SHADY).contains("7H"));
    }

    @Test
    public void ifCardNotInHandThenImpossibleGame(){
        game = ActionFactory.build(Player.ROCKY, "-7C:Shady").performCertain(game);

        assertEquals(Game.IMPOSSIBLE,game);
    }

    @Test
    public void ifFixedShouldPassCard(){
        game = ActionFactory.build(Player.ROCKY, "-7H:Shady").performCertain(game);

        assertEquals(0, game.getPile(Player.ROCKY).size());
        assertFalse(game.getPile(Player.SHADY).contains("7H"));
    }

    @Test
    public void whenPassAndCardUnknownThenAddToTransitPile(){
        game.getPile(Player.ROCKY).add(Game.UNKNOWN_CARD);

        game = ActionFactory.build(Player.ROCKY, "-7C:Shady").performCertain(game);

        assertEquals(1, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.TRANSIT).contains("7C"));
        assertFalse(game.getPile(Player.SHADY).contains("7C"));
    }

    @Test
    public void whenLilPassUnknownCardThenGeneratePossibilities() {
        game.getPile(Player.LIL).add("1H");
        game.getPile(Player.LIL).add("2H");

        Action action = ActionFactory.build(Player.LIL, "-??:Shady");
        action.possibilities.add(ActionFactory.build(Player.LIL, "-1H:Shady"));
        action.possibilities.add(ActionFactory.build(Player.LIL, "-1S:Shady"));
        action.possibilities.add(ActionFactory.build(Player.LIL, "-2H:Shady"));

        assertEquals("LIL:2H", action.performPossibility(game, 0).getPileAsString(Player.LIL));
        assertEquals(Game.IMPOSSIBLE, action.performPossibility(game, 1));
        assertEquals("LIL:1H", action.performPossibility(game, 2).getPileAsString(Player.LIL));
    }

    @Test
    public void whenLilPassUnknownCardIfPossibilityChosenThenGenerateGame() {
        game.getPile(Player.LIL).add("1H");
        game.getPile(Player.LIL).add("2H");

        Action action = ActionFactory.build(Player.LIL, "-??:Shady");
        action.possibilities.add(ActionFactory.build(Player.LIL, "-1H:Shady"));
        action.possibilities.add(ActionFactory.build(Player.LIL, "-1S:Shady"));
        action.possibilities.add(ActionFactory.build(Player.LIL, "-2H:Shady"));

        Game game1 = action.performPossibility(game, 0);
        assertEquals("LIL:2H", game1.getPileAsString(Player.LIL));

        Game game2 = action.performPossibility(game, 1);
        assertEquals(Game.IMPOSSIBLE, game2);

        Game game3 = action.performPossibility(game, 2);
        assertEquals("LIL:1H", game3.getPileAsString(Player.LIL));
    }

    @Test
    public void whenPassCardIfUnknownCardsInHandThenPossibleGame() {
        game.getPile(Player.LIL).add("??");

        Action draw = ActionFactory.build(Player.LIL, "-??:Rocky");

        draw.possibilities.add(ActionFactory.build(Player.LIL, "-2C:Rocky"));

        assertNotEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 0));
    }

    @Test
    public void whenPassCardIfUnknownCardsInHandAndCardInPlayThenImpossibleGame() {
        game.getPile(Player.LIL).add("??");
        game.getPile(Player.ROCKY).add("2C");
        game.getPile(Player.DANNY).add("3C");
        game.getPile(Player.SHADY).add("4C");
        game.getPile(Player.DISCARD).add("5C");

        Action draw = ActionFactory.build(Player.LIL, "-??:Rocky");

        draw.possibilities.add(ActionFactory.build(Player.LIL, "-2C:Rocky"));
        draw.possibilities.add(ActionFactory.build(Player.LIL, "-3C:Rocky"));
        draw.possibilities.add(ActionFactory.build(Player.LIL, "-4C:Rocky"));
        draw.possibilities.add(ActionFactory.build(Player.LIL, "-5C:Rocky"));
        draw.possibilities.add(ActionFactory.build(Player.LIL, "-7C:Rocky"));

        assertEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 0));
        assertEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 1));
        assertEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 2));
        assertEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 3));
        assertNotEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 4));
    }

    @Test
    public void whenReceiverIsNotSameInPossibilityThenImpossibleGame(){
        game.getPile(Player.LIL).add("??");

        Action action = ActionFactory.build(Player.LIL, "-??:Rocky");
        action.possibilities.add(ActionFactory.build(Player.LIL, "-1H:Shady"));

        Game newGame = action.performPossibility(game, 0);
        assertEquals(Game.IMPOSSIBLE, newGame);
    }
}
