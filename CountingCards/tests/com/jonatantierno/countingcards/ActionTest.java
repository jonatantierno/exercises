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

        game = Action.build(Player.ROCKY, "+7H").perform(game);

        game.add(Player.DISCARD);

    }
    @Test
    public void whenDiscardThenAddToDiscardPile(){

        game = Action.build(Player.ROCKY, "-7H:discard").perform(game);

        assertTrue(game.getPile(Player.DISCARD).contains("7H"));
    }

    @Test
    public void whenDiscardAndCardUnknownThenAddToDiscardPile(){
        game.getPile(Player.ROCKY).add(Game.UNKNOWN_CARD);

        game = Action.build(Player.ROCKY, "-7C:discard").perform(game);

        assertEquals(1, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.DISCARD).contains("7C"));
    }

    @Test
    public void shouldPassCard(){
        game.add(Player.SHADY);
        List<String> shadyHand = game.getPile(Player.SHADY);

        game = Action.build(Player.ROCKY, "-7H:Shady").perform(game);

        assertEquals(0, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.SHADY).contains("7H"));
    }

    @Test
    public void whenPassAndCardUnknownThenPass(){
        game.add(Player.SHADY);

        game.getPile(Player.ROCKY).add(Game.UNKNOWN_CARD);

        game = Action.build(Player.ROCKY, "-7C:Shady").perform(game);

        assertEquals(1, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.SHADY).contains("7C"));

    }
}
