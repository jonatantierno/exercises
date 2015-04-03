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

        game = Action.build(Player.ROCKY, "+7H").performAllPossibilities(game).get(0);

        game.add(Player.DISCARD);
        game.add(Player.TRANSIT);

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

    @Test
    public void shouldDraw(){
        game = Action.build(Player.ROCKY, "+6H").perform(game);

        assertTrue(game.getPile(Player.ROCKY).contains("6H"));
    }
    @Test
    public void shouldPassCard(){
        game = Action.build(Player.ROCKY, "-7H:Shady").perform(game);

        assertEquals(0, game.getPile(Player.ROCKY).size());
        assertFalse(game.getPile(Player.SHADY).contains("7H"));
    }
    @Test
    public void ifFixedShouldPassCard(){
        game = Action.build(Player.ROCKY, "-7H:Shady").performAllPossibilities(game).get(0);

        assertEquals(0, game.getPile(Player.ROCKY).size());
        assertFalse(game.getPile(Player.SHADY).contains("7H"));
    }
    @Test
    public void whenReceiveCardThenRemoveFromTransit(){
        game.getPile(Player.TRANSIT).add("7C");

        game = Action.build(Player.ROCKY, "+7C:Shady").perform(game);

        assertEquals(2, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.ROCKY).contains("7C"));

        assertEquals(0, game.getPile(Player.TRANSIT).size());
    }
    @Test
    public void whenReceiveCardIfFixedThenRemoveFromTransit(){
        game.getPile(Player.TRANSIT).add("7C");

        game = Action.build(Player.ROCKY, "+7C:Shady").performAllPossibilities(game).get(0);

        assertEquals(2, game.getPile(Player.ROCKY).size());
        assertTrue(game.getPile(Player.ROCKY).contains("7C"));

        assertEquals(0, game.getPile(Player.TRANSIT).size());
    }

    @Test
    public void whenPassAndCardUnknownThenAddToTransitPile(){
        game.getPile(Player.ROCKY).add(Game.UNKNOWN_CARD);

        game = Action.build(Player.ROCKY, "-7C:Shady").performAllPossibilities(game).get(0);

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
        action.possibilities.add(Action.build(Player.LIL, "-1S:Shady"));
        action.possibilities.add(Action.build(Player.LIL,"-2H:Shady"));

        List<Game> gameList = action.performAllPossibilities(game);

        assertEquals(3, gameList.size());

        assertEquals("LIL:2H", gameList.get(0).getPileAsString(Player.LIL));
        assertEquals(Game.IMPOSSIBLE, gameList.get(1));
        assertEquals("LIL:1H", gameList.get(2).getPileAsString(Player.LIL));
    }

    @Test
    public void whenLilPassUnknownCardIfPossibilityChosenThenGenerateGame() {
        game.getPile(Player.LIL).add("1H");
        game.getPile(Player.LIL).add("2H");

        Action action = Action.build(Player.LIL, "-??:Shady");
        action.possibilities.add(Action.build(Player.LIL,"-1H:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "-1S:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "-2H:Shady"));

        Game game1 = action.perform(game,0);
        assertEquals("LIL:2H", game1.getPileAsString(Player.LIL));

        Game game2 = action.perform(game,1);
        assertEquals(Game.IMPOSSIBLE, game2);

        Game game3 = action.perform(game,2);
        assertEquals("LIL:1H", game3.getPileAsString(Player.LIL));
    }
    @Test
    public void whenLilReceivesUnknownCardIfProbabilityChosenThenGenerateGame() {
        game.getPile(Player.TRANSIT).add("1H");
        game.getPile(Player.TRANSIT).add("2H");

        Action action = Action.build(Player.LIL, "+??:Shady");

        action.possibilities.add(Action.build(Player.LIL, "+1H:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "+1S:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "+2H:Shady"));

        Game game1 = action.perform(game,0);
        assertEquals("LIL:1H",game1.getPileAsString(Player.LIL));

        Game game2 = action.perform(game,1);
        assertEquals(Game.IMPOSSIBLE, game2);

        Game game3 = action.perform(game,2);
        assertEquals("LIL:2H", game3.getPileAsString(Player.LIL));
    }
    @Test
    public void whenLilReceivesUnknownCardThenGeneratePossibilities() {
        game.getPile(Player.TRANSIT).add("1H");
        game.getPile(Player.TRANSIT).add("2H");

        Action action = Action.build(Player.LIL, "+??:Shady");

        action.possibilities.add(Action.build(Player.LIL, "+1H:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "+1S:Shady"));
        action.possibilities.add(Action.build(Player.LIL, "+2H:Shady"));

        List<Game> gameList = action.performAllPossibilities(game);

        assertEquals(3, gameList.size());

        assertEquals("LIL:1H",gameList.get(0).getPileAsString(Player.LIL));
        assertEquals(Game.IMPOSSIBLE, gameList.get(1));
        assertEquals("LIL:2H", gameList.get(2).getPileAsString(Player.LIL));
    }

    @Test
    public void whenLilDrawsUnknownIfProbabilityChosenThenReturnGame() {

        Action action = Action.build(Player.LIL, "+??");

        action.possibilities.add(Action.build(Player.LIL, "+1H"));
        action.possibilities.add(Action.build(Player.LIL, "+1S"));
        action.possibilities.add(Action.build(Player.LIL, "+2H"));

        Game game1 = action.perform(game,0);
        assertEquals("LIL:1H",game1.getPileAsString(Player.LIL));

        Game game2 = action.perform(game,1);
        assertEquals("LIL:1S", game2.getPileAsString(Player.LIL));
    }

    @Test
    public void whenLilDrawsUnknownCardThenGeneratePossibilities() {

        Action action = Action.build(Player.LIL, "+??");

        action.possibilities.add(Action.build(Player.LIL, "+1H"));
        action.possibilities.add(Action.build(Player.LIL, "+1S"));
        action.possibilities.add(Action.build(Player.LIL, "+2H"));

        List<Game> gameList = action.performAllPossibilities(game);

        assertEquals(3, gameList.size());

        assertEquals("LIL:1H",gameList.get(0).getPileAsString(Player.LIL));
        assertEquals("LIL:1S",gameList.get(1).getPileAsString(Player.LIL));
        assertEquals("LIL:2H",gameList.get(2).getPileAsString(Player.LIL));
    }
}
