package edu.hse.cs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void testPlayer() {
        Player player = new Player("Player");
        int card = Cards.getCard();
        player.increasePoints(card);
        assertEquals(card, player.getBalance());

        String toString = "Player has " + card + " points on his balance.";
        assertEquals(toString, player.toString());

        assertEquals(card, player.losePoints(10 * card));
        assertEquals(0, player.getBalance());
    }
}