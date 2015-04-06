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
public class DiscardActionTest {
    Game game;
    @Before
    public void setup(){
        game= new Game();

        game = Action.build(Player.ROCKY, "+7H").performAllPossibilities(game).get(0);
    }
    @Test
    public void whenDiscardThenAddToDiscardPile(){

        game = Action.build(Player.ROCKY, "-7H:discard").perform(game);

        assertTrue(game.getPile(Player.DISCARD).contains("7H"));
    }
    @Test
    public void whenDiscardAndPossibilitiesThenAddToDiscardPile(){

        game = Action.build(Player.ROCKY, "-7H:discard").performAllPossibilities(game).get(0);

        assertTrue(game.getPile(Player.DISCARD).contains("7H"));
    }

    @Test
    public void whenDiscardAndCardUnknownThenAddToDiscardPile(){
        game.getPile(Player.ROCKY).add(Game.UNKNOWN_CARD);

        game = Action.build(Player.ROCKY, "-7C:discard").performAllPossibilities(game).get(0);

        assertEquals(1, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.DISCARD).contains("7C"));
    }
}
