package com.jonatantierno.countingcards;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * This class tests how the cards move between the hands, the draw pile and the discard pile.
 * Created by jonatan on 31/03/15.
 */
public class GameTest {
    Game game;
    Hand rockyHand, discardPile;
    @Before
    public void setup(){
        game= new Game();

        rockyHand = new Hand(Player.ROCKY,game);
        rockyHand.perform(new Action("+7H"));

        discardPile = new Hand(Player.DISCARD,game);
    }
    @Test
    public void whenDiscardThenAddToDiscardPile(){

        rockyHand.perform(new Action("-7H:discard"));

        assertTrue(discardPile.cards.contains(new Card("7H")));
    }
}
