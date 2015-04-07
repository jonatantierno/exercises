package com.jonatantierno.countingcards.actions;

import com.jonatantierno.countingcards.Game;
import com.jonatantierno.countingcards.Player;
import org.junit.Before;
import org.junit.Test;

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

        game = ActionFactory.build(Player.ROCKY, "+7H").performCertain(game);
    }
    @Test
    public void whenDiscardThenAddToDiscardPile(){

        game = ActionFactory.build(Player.ROCKY, "-7H:discard").performCertain(game);

        assertTrue(game.getPile(Player.DISCARD).contains("7H"));
    }
    @Test
    public void whenDiscardAndPossibilitiesThenAddToDiscardPile(){

        game = ActionFactory.build(Player.ROCKY, "-7H:discard").performCertain(game);

        assertTrue(game.getPile(Player.DISCARD).contains("7H"));
    }

    @Test
    public void whenDiscardAndCardUnknownThenAddToDiscardPile(){
        game.getPile(Player.ROCKY).add(Game.UNKNOWN_CARD);

        game = ActionFactory.build(Player.ROCKY, "-7C:discard").performCertain(game);

        assertEquals(1, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.DISCARD).contains("7C"));
    }
}
