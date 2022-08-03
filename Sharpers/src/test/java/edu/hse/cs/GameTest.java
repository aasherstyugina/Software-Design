package edu.hse.cs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void testGame() {
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < 10; ++i) {
            players.add(new Player("Fair player " + i));
        }
        for (int i = 0; i < 8; ++i) {
            players.add(new Player("Sharper " + i));
        }

        Game game = new Game();
        Game.players = players;
        Game.numOfFair = 10;

        assertDoesNotThrow(() -> game.start());
    }
}