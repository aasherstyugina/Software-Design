package edu.hse.cs;

/**
 * This class describes endless deck of cards.
 *
 * @author Sherstyugina Anastasia, BSE204.
 */
public final class Cards {
    /**
     * Gets a random card.
     *
     * @return integer value from 1 to 10.
     */
    synchronized public static int getCard() {
        return 1 + (int) (Math.random() * 10);
    }
}
