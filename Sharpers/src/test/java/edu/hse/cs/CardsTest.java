package edu.hse.cs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardsTest {

    @Test
    void testGetCard() {
        for (int i = 0; i < 20; ++i) {
            int num = Cards.getCard();
            assertTrue(num <= 10);
            assertTrue(num >= 1);
        }
    }
}