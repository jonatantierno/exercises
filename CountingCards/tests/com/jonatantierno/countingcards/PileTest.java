package com.jonatantierno.countingcards;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for modifying a player's hand with moves
 * Created by jonatan on 30/03/15.
 */
public class PileTest {
    Game game;

    @Before
    public void setup(){
        setInitialHand();
    }
    @Test
    public void shouldGetCards(){

        List<String> rockyHand = game.getPile(Player.ROCKY);

        assertEquals(4, rockyHand.size());

        assertEquals("QD", rockyHand.get(0));
        assertEquals("7S", rockyHand.get(1));
        assertEquals("6C", rockyHand.get(2));
        assertEquals("AH", rockyHand.get(3));
    }

    private void setInitialHand() {
        game = new Game();
        game.add(Player.ROCKY);
        game.add(Player.DISCARD);

        List<Action> actions= new ArrayList<>();
        actions.add(Action.build(Player.ROCKY, "+QD"));
        actions.add(Action.build(Player.ROCKY, "+7S"));
        actions.add(Action.build(Player.ROCKY, "+6C"));
        actions.add(Action.build(Player.ROCKY, "+AH"));

        Game game1 = game;
        for(Action action : actions){
            game1 = action.performAllPossibilities(game1).get(0);
        }
        game = game1;
    }

    @Test
    public void shouldDiscard(){
        game = Action.build(Player.ROCKY, "-QD:discard").performAllPossibilities(game).get(0);

        List<String> rockyHand = game.getPile(Player.ROCKY);

        assertEquals(3, rockyHand.size());
        assertFalse(rockyHand.contains("QD"));
    }
}
