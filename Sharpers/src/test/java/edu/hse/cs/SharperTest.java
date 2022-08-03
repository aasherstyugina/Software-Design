package edu.hse.cs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SharperTest {
    @Test
    void testRobbery() {
        Player fair = new Player("Fair");
        Sharper sharper = new Sharper("Sharper");

        fair.increasePoints(5);
        sharper.increasePoints(fair.losePoints(7));
        assertEquals(5, sharper.getBalance());
        assertEquals(0, fair.getBalance());

        fair.increasePoints(9);
        sharper.increasePoints(fair.losePoints(4));
        assertEquals(9, sharper.getBalance());
        assertEquals(5, fair.getBalance());
    }

    @Test
    void testTakeCard() {
        Sharper sharper = new Sharper("Sharper");
        int card = Cards.getCard();
        sharper.increasePoints(card);
        assertEquals(card, sharper.getBalance());

        String toString = "Sharper has " + card + " points on his balance.";
        assertEquals(toString, sharper.toString());
    }
}