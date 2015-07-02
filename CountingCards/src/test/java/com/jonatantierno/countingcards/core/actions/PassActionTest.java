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
public class PassActionTest {
    public static final String CARD = "7H";
    public static final String CARD_NOT_IN_HAND = "7C";
    public static final String CARD_0 = "1H";
    public static final String CARD_1 = "2H";
    private static final String ACE_OF_SPADES = "1S";

    Game game;
    private Player playerOne, playerTwo, playerThree;

    @Before
    public void setupAnon(){
        playerOne = new Player("One");
        playerTwo = new Player("Two");
        playerThree = new Player("Three");

        game= new Game();

        game.add(playerOne);
        game.add(playerTwo);
    }

    @Test
    public void shouldPassCard(){
        game.getPile(playerOne).add(CARD);

        game = new PassAction(playerOne, CARD, playerTwo).performCertain(game);

        assertEquals(0, game.getPile(playerOne).size());
    }

    @Test
    public void ifCardNotInHandThenImpossibleGame(){
        game.getPile(playerOne).add(CARD);

        game = new PassAction(playerOne, CARD_NOT_IN_HAND, playerTwo).performCertain(game);

        assertEquals(Game.IMPOSSIBLE, game);
    }

    @Test
    public void whenPassAndCardUnknownThenAddToTransitPile(){
        game.getPile(playerOne).add(Game.UNKNOWN_CARD);

        game = new PassAction(playerOne, CARD, playerTwo).performCertain(game);

        assertTrue(game.getPile(game.transitPile).contains(CARD));
    }

    @Test
    public void whenPlayerPassesUnknownCardThenGeneratePossibilities() {
        game.getPile(playerOne).add(CARD_0);
        game.getPile(playerOne).add(CARD_1);

        Action action = new PassAction(playerOne, Game.UNKNOWN_CARD,playerTwo);

        action.possibilities.add(new PassAction(playerOne, CARD_0, playerTwo));
        action.possibilities.add(new PassAction(playerOne, CARD_NOT_IN_HAND, playerTwo));
        action.possibilities.add(new PassAction(playerOne, CARD_1, playerTwo));

        checkPossibility0(action);

        checkPossibilityNotInHand(action);

        checkPossibility2(action);
    }

    private void checkPossibility2(Action action) {
        Game possibleGame2 = action.performPossibility(this.game, 2);
        assertEquals(CARD_0, possibleGame2.getPile(playerOne).get(0));
    }

    private void checkPossibilityNotInHand(Action action) {
        Game possibleGame1 = action.performPossibility(this.game, 1);
        assertEquals(Game.IMPOSSIBLE, possibleGame1);
    }

    private void checkPossibility0(Action action) {
        Game possibleGame0 = action.performPossibility(this.game, 0);
        assertEquals(CARD_1, possibleGame0.getPile(playerOne).get(0));
    }

    @Test
    public void whenPassCardIfUnknownCardsInHandThenGameIsPossible() {
        game.getPile(playerOne).add(Game.UNKNOWN_CARD);

        Action draw = new PassAction(playerOne, Game.UNKNOWN_CARD, playerTwo);

        draw.possibilities.add(new PassAction(playerOne, CARD, playerTwo));

        assertNotEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 0));
    }

    @Test
    public void whenPassCardIfUnknownCardsInHandAndCardInPlayThenGameIsImpossible() {
        game.getPile(playerOne).add(Game.UNKNOWN_CARD);
        game.getPile(game.discardPile).add(CARD);

        Action draw = new PassAction(playerOne,Game.UNKNOWN_CARD,playerTwo);

        draw.possibilities.add(new PassAction(playerOne, CARD,playerTwo));

        assertEquals(Game.IMPOSSIBLE, draw.performPossibility(game, 0));
    }

    @Test
    public void whenReceiverIsNotSameInPossibilityThenGameIsImpossible(){
        game.getPile(playerOne).add(Game.UNKNOWN_CARD);

        Action action = new PassAction(playerOne,Game.UNKNOWN_CARD,playerTwo);
        action.possibilities.add(new PassAction(playerOne, CARD_0, playerThree));

        Game newGame = action.performPossibility(game, 0);
        assertEquals(Game.IMPOSSIBLE, newGame);
    }
}
