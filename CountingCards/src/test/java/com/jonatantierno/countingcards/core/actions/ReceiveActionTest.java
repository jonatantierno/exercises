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
public class ReceiveActionTest {
    public static final String CARD = "7C";
    public static final String CARD_0 = "1H";
    public static final String CARD_1 = "2H";
    private static final String CARD_NOT_IN_TRANSIT = "1S";
    Game game;
    private Player playerTwo;
    private Player playerOne;
    private Player playerThree;

    @Before
    public void setupAnon(){
        game= new Game();

        playerOne = new Player("One");
        playerTwo = new Player("Two");
        playerThree = new Player("Three");

        game.add(playerOne);
        game.add(playerTwo);
        game.add(playerThree);
    }

    @Test
    public void whenReceiveCardThenRemoveFromTransit(){
        game.getPile(game.transitPile).add(CARD);

        game = new ReceiveAction(playerTwo, CARD,playerOne).performCertain(game);

        assertTrue(game.getPile(playerTwo).contains(CARD));
        assertEquals(0, game.getPile(game.transitPile).size());
    }

    @Test
    public void whenPlayerReceivesUnknownCardThenThereAreDifferentPossibilities() {
        game.getPile(game.transitPile).add(CARD_0);
        game.getPile(game.transitPile).add(CARD_1);

        Action action = new ReceiveAction(playerTwo, Game.UNKNOWN_CARD, playerOne);

        action.possibilities.add(new ReceiveAction(playerTwo, CARD_0, playerOne));
        action.possibilities.add(new ReceiveAction(playerTwo, CARD_NOT_IN_TRANSIT,playerOne));
        action.possibilities.add(new ReceiveAction(playerTwo, CARD_1, playerOne));

        Game possibleGame0 = action.performPossibility(this.game, 0);
        assertEquals(CARD_0, possibleGame0.getPile(playerTwo).get(0));

        Game possibleGame1 = action.performPossibility(this.game, 1);
        assertEquals(Game.IMPOSSIBLE, possibleGame1);

        Game possibleGame2 = action.performPossibility(this.game, 2);
        assertEquals(CARD_1, possibleGame2.getPile(playerTwo).get(0));
    }

    @Test
    public void whenPassUnknownCardAndInSenderThenRemoveItFromHand(){
        game.getPile(game.transitPile).add(Game.UNKNOWN_CARD);
        game.getPile(playerOne).add(CARD);

        Action action = new ReceiveAction(playerTwo, Game.UNKNOWN_CARD, playerOne);
        action.possibilities.add(new ReceiveAction(playerTwo, CARD, playerOne));

        Game newGame = action.performPossibility(game, 0);

        assertNotEquals(Game.IMPOSSIBLE, newGame);
        assertFalse(newGame.getPile(playerOne).contains(CARD));
        assertEquals(0, newGame.getPile(newGame.transitPile).size());
    }

    @Test
    public void whenPassUnknownCardAndInPlayThenImpossibleGame(){
        game.getPile(game.transitPile).add(Game.UNKNOWN_CARD);
        game.getPile(playerThree).add(CARD);

        Action action = new ReceiveAction(playerTwo, Game.UNKNOWN_CARD, playerOne);

        action.possibilities.add(new ReceiveAction(playerTwo, CARD, playerOne));

        Game newGame = action.performPossibility(game, 0);
        assertEquals(Game.IMPOSSIBLE, newGame);
    }

    @Test
    public void whenSenderIsNotSameInPossibilityThenImpossibleGame(){
        game.getPile(game.transitPile).add(Game.UNKNOWN_CARD);

        Action action = new ReceiveAction(playerTwo, Game.UNKNOWN_CARD, playerOne);
        action.possibilities.add(new ReceiveAction(playerTwo, CARD, playerThree));

        Game newGame = action.performPossibility(game, 0);
        assertEquals(Game.IMPOSSIBLE, newGame);
    }
}
