package com.jonatantierno.countingcards.core.actions;

import com.jonatantierno.countingcards.core.Game;
import com.jonatantierno.countingcards.core.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests how the cards move between the hands, the draw pile and the discard pile.
 * Created by jonatan on 31/03/15.
 */
public class DiscardActionTest {
    public static final String CARD = "7H";
    public static final String SEVEN_OF_CLUBS = "7C";
    Game game;
    Player player;
    @Before
    public void setup(){
        player = new Player();
        game = new Game();

        game.add(player);

        game = game.play(new DrawAction(player, CARD));
    }
    @Test
    public void whenDiscardThenAddToDiscardPile(){
        Game nextGame = game.play(new DiscardAction(player, CARD));

        assertTrue(nextGame.discardPileContains(CARD));
    }

    @Test
    public void whenDiscardAndCardUnknownThenAddToDiscardPile(){
        game.getPile(player).add(Game.UNKNOWN_CARD);

        Game nextGame = game.play(new DiscardAction(player, CARD));

        assertNotEquals(Game.IMPOSSIBLE, nextGame);
        assertEquals(1, nextGame.getPile(player).size());
        assertTrue(nextGame.discardPileContains(CARD));
    }
}
