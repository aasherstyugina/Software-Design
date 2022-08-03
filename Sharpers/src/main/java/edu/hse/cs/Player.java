package edu.hse.cs;

/**
 * This class describes player, which represents a thread.
 *
 * @author Sherstyugina Anastasia, BSE204.
 */
public class Player extends Thread {
    private int balance;

    /**
     * Constructor.
     *
     * @param name is a player's name.
     */
    public Player(String name) {
        super(name);
        balance = 0;
    }

    /**
     * Overriden method from Thread, it is how fair player behaves before interruption.
     */
    public void run() {
        while (true) {
            if (takeCard()) {
                break;
            }
        }
    }

    /**
     * Information about player and his balance.
     *
     * @return String line.
     */
    @Override
    public final String toString() {
        return this.getName() + " has " + balance + " points on his balance.";
    }

    /**
     * Decreases player's balance after the robbery.
     *
     * @param stolenPoints is how many points sharper wants to steal from this fair player.
     * @return how many points did the sharper manage to steal.
     */
    synchronized public final int losePoints(int stolenPoints) {
        if (balance >= stolenPoints) {
            balance -= stolenPoints;
        } else {
            stolenPoints = balance;
            balance = 0;
        }

        return stolenPoints;
    }

    /**
     * Increase sharper's balance after the robbery.
     *
     * @param points how many points did the sharper manage to steal.
     */
    synchronized public final void increasePoints(int points) {
        balance += points;
    }

    /**
     * Gets player's balance.
     *
     * @return
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Represents how player takes a card.
     *
     * @return true, if thread was interrupted, otherwise false.
     */
    public boolean takeCard() {
        // Draw a card and rest.
        balance += Cards.getCard();
        try {
            Thread.sleep(100 + (int) (Math.random() * 100));
            return false;
        } catch (InterruptedException exception) {
            return true;
        }
    }
}
