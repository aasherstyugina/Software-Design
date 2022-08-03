package battleship;

import java.util.Scanner;

public class Main {
    /**
     * Program starts by executing the main method of class Main.
     *
     * @param args are command line arguments.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the battleship!\n");

        int[][] parsedInput = new int[2][];
        parsedInput[0] = new int[2];
        parsedInput[1] = new int[5];
        boolean flag = true;

        // Get size of the Ocean and the ships number.
        if (args.length == 0) {
            System.out.println("Define the size of the Ocean (non-negative values).");
            parsedInput = getInputFromConsole();
        } else {
            try {
                parsedInput = checkCommandLineArgs(args);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                flag = false;
            }
        }

        // Create the Ocean, arrange the Fleet and start the game.
        if (flag) {
            Ocean ocean = new Ocean(parsedInput[0]);
            ocean.createFleet(parsedInput[1]);
            if (!ocean.arrangeFleet()) {
                if (!doUserWantToQuit())
                    main(new String[0]);
            } else {
                System.out.println("Fleet is arranged successfully!");
                Game game = new Game(ocean);
                game.playGame();
            }
        }
    }

    /**
     * Gets non-negative integer value from the console.
     *
     * @return received value.
     */
    static int getNonNegativeIntInput() {
        int input = 0;
        boolean flag = true;
        Scanner in = new Scanner(System.in);

        // Re-enter data until input is correct.
        while (flag) {
            if (in.hasNextInt()) {
                input = in.nextInt();
                in.nextLine();
                // Check if value is non-negative.
                if (input >= 0)
                    flag = false;
                else
                    System.out.println("Wrong input! Try again.");
            } else {
                System.out.println("Wrong input! Try again.");
                in.nextLine();
            }
        }

        return input;
    }

    /**
     * Checks if string can be parsed to integer.
     *
     * @param input is received string.
     * @return true if string can be parsed, otherwise false.
     */
    static boolean tryParseInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks command line arguments and parse it to integer.
     *
     * @param args command line arguments.
     * @return array with parsed Ocean size and ships numbers.
     * @throws Exception if it's impossible to parse.
     */
    static int[][] checkCommandLineArgs(String[] args) throws Exception {
        int[][] parsedInput = new int[2][];
        parsedInput[0] = new int[2];
        parsedInput[1] = new int[5];

        for (int i = 0; i < 7; ++i) {
            if (tryParseInt(args[i]))
                switch (i) {
                    case 0:
                    case 1:
                        parsedInput[0][i] = Integer.parseInt(args[i]);
                        break;
                    default:
                        parsedInput[1][i - 2] = Integer.parseInt(args[i]);
                        break;
                }
            else
                throw new Exception("Couldn't parse command line arguments to int.\n");
        }

        return parsedInput;
    }

    /**
     * Gets Ocean size and ships numbers from console.
     *
     * @return array with parsed Ocean size and ships numbers.
     */
    static int[][] getInputFromConsole() {
        int[][] parsedInput = new int[2][];
        parsedInput[0] = new int[2];
        parsedInput[1] = new int[5];

        System.out.println("Enter number of rows:");
        parsedInput[0][0] = getNonNegativeIntInput();
        System.out.println("Enter number of columns:");
        parsedInput[0][1] = getNonNegativeIntInput();

        System.out.println("\nDefine the ships number (non-negative) for each ship type.");
        System.out.println("Enter number of carriers:");
        parsedInput[1][0] = getNonNegativeIntInput();
        System.out.println("Enter number of battleships:");
        parsedInput[1][1] = getNonNegativeIntInput();
        System.out.println("Enter number of cruisers:");
        parsedInput[1][2] = getNonNegativeIntInput();
        System.out.println("Enter number of destroyers:");
        parsedInput[1][3] = getNonNegativeIntInput();
        System.out.println("Enter number of submarines:");
        parsedInput[1][4] = getNonNegativeIntInput();

        return parsedInput;
    }

    /**
     * Checks if user wants to re-enter data or quit when it's impossible to arrange the Fleet.
     *
     * @return true if user wants to quit, otherwise false.
     */
    static boolean doUserWantToQuit() {
        System.out.println("It's impossible to arrange this fleet." +
                "\nEnter \"0\" to re-enter data or anything else to quit the game:");
        Scanner in = new Scanner(System.in);
        if (in.hasNextInt())
            if (0 == in.nextInt()) {
                in.nextLine();
                return false;
            }
        return true;
    }
}
