package com.jonatantierno.countingcards.core.actions;

import com.jonatantierno.countingcards.core.Game;
import com.jonatantierno.countingcards.core.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the Draw Action.
 * Created by jonatan on 31/03/15.
 */
public class DrawActionTest {
    public static final String CARD = "AC";
    public static final String CARD_1 = "AS";
    public static final String CARD_0 = "AH";
    public static final String SIX_OF_HEARTS = "6H";
    Game game;
    private Player player;
    private Player anotherPlayer;
    private Action drawUnknownCard;

    @Before
    public void setup() {
        player = new Player();
        game= new Game();
        game.add(player);

        drawUnknownCard = new DrawAction(player, Game.UNKNOWN_CARD);
    }

    @Test
    public void shouldDraw(){
        Action build = new DrawAction(player, SIX_OF_HEARTS);
        game = build.performCertain(game);

        assertTrue(game.getPile(player).contains(SIX_OF_HEARTS));
    }


    @Test
    public void whenDrawAndCardInPlayThenImpossibleGame(){
        anotherPlayer = new Player();

        game.add(anotherPlayer);
        game.getPile(anotherPlayer).add(CARD);

        drawUnknownCard.possibilities.add(new DrawAction(player, CARD));

        assertEquals(Game.IMPOSSIBLE, drawUnknownCard.performPossibility(game, 0));
    }


    @Test
    public void whenLilDrawsUnknownIfProbabilityChosenThenReturnGame() {
        drawUnknownCard.possibilities.add(new DrawAction(player, CARD_0));
        drawUnknownCard.possibilities.add(new DrawAction(player, CARD_1));

        Game possibleGame0 = drawUnknownCard.performPossibility(game, 0);
        assertEquals(CARD_0, possibleGame0.getPile(player).get(0));

        Game possibleGame1 = drawUnknownCard.performPossibility(game, 1);
        assertEquals(CARD_1, possibleGame1.getPile(player).get(0));
    }
}
