package edu.hse.cs;

import java.util.ArrayList;

/**
 * This class describes game, which represents a thread.
 *
 * @author Sherstyugina Anastasia, BSE204.
 */
public final class Game extends Thread {
    // In ArrayList from 0 to numOfFair - 1 is fair players,
    // from numOfFair to the end is sharpers.
    public static ArrayList<Player> players;
    public static int numOfFair;

    /**
     * Constructor.
     */
    public Game() {
        super("Game");
    }

    /**
     * Overriden method from Thread.
     */
    public void run() {
        // Start all player's threads.
        System.out.println("\nGame started!");
        for (Player player : players) {
            player.start();
        }
        // Let them play for 10 seconds.
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }
        // Interrupt all player's threads and finish the game.
        for (Player player : players) {
            player.interrupt();
        }
        System.out.println("\nGame finished!");

        printResults();
    }

    /**
     * Print results and announce the winner (or winners).
     */
    private void printResults() {
        System.out.println("\nResults:");
        int maxPoints = 0;
        ArrayList<Player> winners = new ArrayList<Player>();
        for (Player player : players) {
            System.out.println(player);
            int currentBalance = player.getBalance();
            if (currentBalance == maxPoints) {
                winners.add(player);
            } else if (currentBalance > maxPoints) {
                winners.clear();
                winners.add(player);
                maxPoints = player.getBalance();
            }
        }
        for (Player player : winners) {
            System.out.println("\nThe winner is " + player.getName()
                    + "! He got " + maxPoints + " points.");
        }
    }
}
