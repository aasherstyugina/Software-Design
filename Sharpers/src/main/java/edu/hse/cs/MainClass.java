package edu.hse.cs;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class describes physical point.
 *
 * @author Sherstyugina Anastasia, BSE204.
 */
public final class MainClass {
    public static Scanner in = new Scanner(System.in);

    /**
     * Program starts by executing the main method of class Main.
     *
     * @param args are command line arguments.
     */
    public static void main(String[] args) throws InterruptedException {
        // Choosing game mode.
        System.out.println(
                """
                        \t[1] Two sharpers and one fair player
                        \t[2] Set the number of players manually
                        """);
        int gameMode = 0;
        do {
            System.out.print("Enter 1 or 2 to select game mode: ");
            if (in.hasNextInt()) {
                gameMode = in.nextInt();
            } else {
                in.next();
            }
        } while (gameMode != 1 && gameMode != 2);

        // Set fair players and sharpers.
        if (gameMode == 1) {
            Game.numOfFair = 1;
            Game.players = createPlayers(1, 2);
        } else {
            setPlayersManually();
        }

        // Play game.
        Game game = new Game();
        game.start();
        game.join();
    }

    /**
     * Creates ArrayList of players according to the given parameters.
     *
     * @param numOfFair     is the number of fair players.
     * @param numOfSharpers is the number of sharpers.
     * @return ArrayList of players.
     */
    private static ArrayList<Player> createPlayers(int numOfFair, int numOfSharpers) {
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < numOfFair; ++i) {
            players.add(new Player("Fair player " + (i + 1)));
        }
        for (int i = 0; i < numOfSharpers; ++i) {
            players.add(new Sharper("Sharper " + (i + 1)));
        }

        return players;
    }

    /**
     * Gets parameters for creating players and creates ArrayList of players.
     */
    private static void setPlayersManually() {
        int numOfFair = 0;
        do {
            System.out.print("Enter number of fair players (integer value >= 1): ");
            if (in.hasNextInt()) {
                numOfFair = in.nextInt();
            } else {
                in.next();
            }
        } while (numOfFair < 1);

        int numOfSharpers = -1;
        do {
            System.out.print("Enter number of sharpers (integer value >= 0): ");
            if (in.hasNextInt()) {
                numOfSharpers = in.nextInt();
            } else {
                in.next();
            }
        } while (numOfSharpers < 0);

        Game.numOfFair = numOfFair;
        Game.players = createPlayers(numOfFair, numOfSharpers);
    }
}
