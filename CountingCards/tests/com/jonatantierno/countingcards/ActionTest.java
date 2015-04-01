package com.jonatantierno.countingcards;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests how the cards move between the hands, the draw pile and the discard pile.
 * Created by jonatan on 31/03/15.
 */
public class ActionTest {
    Game game;
    @Before
    public void setup(){
        game= new Game();

        game.add(Player.ROCKY);
        game.add(Player.SHADY);
        game.add(Player.LIL);

        game = Action.build(Player.ROCKY, "+7H").perform(game).get(0);

        game.add(Player.DISCARD);
        game.add(Player.TRANSIT);

    }
    @Test
    public void whenDiscardThenAddToDiscardPile(){

        game = Action.build(Player.ROCKY, "-7H:discard").perform(game).get(0);

        assertTrue(game.getPile(Player.DISCARD).contains("7H"));
    }

    @Test
    public void whenDiscardAndCardUnknownThenAddToDiscardPile(){
        game.getPile(Player.ROCKY).add(Game.UNKNOWN_CARD);

        game = Action.build(Player.ROCKY, "-7C:discard").perform(game).get(0);

        assertEquals(1, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.DISCARD).contains("7C"));
    }

    @Test
    public void shouldPassCard(){
        List<String> shadyHand = game.getPile(Player.SHADY);

        game = Action.build(Player.ROCKY, "-7H:Shady").perform(game).get(0);

        assertEquals(0, game.getPile(Player.ROCKY).size());
        assertFalse(game.getPile(Player.SHADY).contains("7H"));
    }
    @Test
    public void whenReceiveCardThenRemoveFromTransit(){
        game.getPile(Player.TRANSIT).add("7C");

        game = Action.build(Player.ROCKY, "+7C:Shady").perform(game).get(0);

        assertEquals(2, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.ROCKY).contains("7C"));

        assertEquals(0, game.getPile(Player.TRANSIT).size());
    }

    @Test
    public void whenPassAndCardUnknownThenAddToTransitPile(){
        game.getPile(Player.ROCKY).add(Game.UNKNOWN_CARD);

        game = Action.build(Player.ROCKY, "-7C:Shady").perform(game).get(0);

        assertEquals(1, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.TRANSIT).contains("7C"));
        assertFalse(game.getPile(Player.SHADY).contains("7C"));
    }

    @Test
    public void whenLilPassUnknownCardThenGeneratePossibilities() {
        game.getPile(Player.LIL).add("1H");
        game.getPile(Player.LIL).add("2H");

        Action action = Action.build(Player.LIL, "-??:Shady");
        action.possibilities.add(Action.build(Player.LIL,"-1H:Shady"));
        action.possibilities.add(Action.build(Player.LIL,"-1S:Shady"));
        action.possibilities.add(Action.build(Player.LIL,"-2H:Shady"));

        List<Game> gameList = action.perform(game);

        assertEquals(2, gameList.size());

        assertEquals("LIL: 2H", gameList.get(0).getPileAsString(Player.LIL));
        assertEquals("LIL: 1H", gameList.get(1).getPileAsString(Player.LIL));
    }
    @Test
    public void whenLilReceivesUnknownCardThenGeneratePossibilities() {
        game.getPile(Player.TRANSIT).add("1H");
        game.getPile(Player.TRANSIT).add("2H");

        Action action = Action.build(Player.LIL, "+??:Shady");

        action.possibilities.add(Action.build(Player.LIL, "+1H:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "+1S:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "+2H:Shady"));

        List<Game> gameList = action.perform(game);

        assertEquals(2, gameList.size());

        assertEquals("LIL: 1H",gameList.get(0).getPileAsString(Player.LIL));
        assertEquals("LIL: 2H",gameList.get(1).getPileAsString(Player.LIL));
    }
}
