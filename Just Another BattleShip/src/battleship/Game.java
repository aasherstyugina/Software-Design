package battleship;

import java.util.Scanner;

/**
 * This class describes game.
 */
public class Game {
    // Private fields.
    private Ocean ocean;
    private int rowNum;
    private int colNum;
    private char[][] gameState;
    private int shotNumber;
    private int torpedoNum;
    private boolean isRecoveryModeOn;
    private int lastHitShip;

    /**
     * Constructor.
     *
     * @param ocean is The Ocean with the arranged Fleet.
     */
    public Game(Ocean ocean) {
        rowNum = ocean.getRowNum();
        colNum = ocean.getColNum();
        gameState = new char[rowNum][colNum];
        for (int i = 0; i < rowNum; ++i)
            for (int j = 0; j < colNum; ++j)
                gameState[i][j] = '0';
        this.ocean = ocean;
        lastHitShip = -1;
    }

    /**
     * Method for playing the game.
     */
    public void playGame() {
        printRules();
        System.out.println();

        // Add (or not) game modes.
        torpedoNum = getTorpedoNum();
        isRecoveryModeOn = getRecoveryMode();

        System.out.println("Start!");
        printField();
        // The game continues as long as there are ships in the Ocean.
        while (ocean.getShipsLeft() > 0) {
            gameTurn();
        }

        System.out.println("Game is over!\nNumber of shots: " + shotNumber);
    }

    /**
     * One game turn.
     */
    void gameTurn() {
        Shot shot = readShot();
        int row = shot.getRow(), col = shot.getCol();

        // Check if there are any torpedoes for torpedo shot.
        if (shot.isTorpedo() && torpedoNum <= 0)
            System.out.println("No torpedoes available.");
        else {
            torpedoNum = shot.isTorpedo() ? torpedoNum - 1 : torpedoNum;
            switch (gameState[row][col]) {
                // When shot in the '0' cell, check cell's ship index.
                case '0':
                    int index = ocean.getGameField()[row][col].getShipIndex();
                    if (index == -1) {
                        // If there is no ship in this cell, it's a miss shot.
                        System.out.println("Miss!");
                        gameState[row][col] = '-';
                        if (lastHitShip != -1)
                            recover(lastHitShip);
                    } else {
                        // If there is ship in this cell, it's a hit shot.
                        System.out.println("Hit!");
                        if (!ocean.shot(index) | shot.isTorpedo()) {
                            // Ship is sunk if it was last ship-cell or it was torpedo shot.
                            kill(index);
                            System.out.println("You just have sunk a " + ocean.getFleet().get(index).toString());
                        } else {
                            // Otherwise, hit the ship but check if recover mode is on.
                            if (isRecoveryModeOn) {
                                if (ocean.getFleet().get(index).getCellsLeft() + 1 == ocean.getFleet().get(index).getLength()
                                        || lastHitShip == index) {
                                    gameState[row][col] = '+';
                                    lastHitShip = index;
                                } else
                                    recover(lastHitShip);
                            } else
                                gameState[row][col] = '+';
                        }
                    }
                    break;
                default:
                    // When shot not in the '0' cell.
                    System.out.println("You've already tried to shot to this cell!");
                    break;
            }
            // Increase number of made shots.
            ++shotNumber;
        }

        printField();
        System.out.println();
    }

    /**
     * Recover the ship in a recover mode.
     *
     * @param index is an index of the ship in ArrayList fleet.
     */
    void recover(int index) {
        if (isRecoveryModeOn) {
            int rowMin = ocean.getFleet().get(index).getRow();
            int rowMax = ocean.getFleet().get(index).getEndRow();
            int colMin = ocean.getFleet().get(index).getCol();
            int colMax = ocean.getFleet().get(index).getEndCol();
            for (int i = rowMin; i <= rowMax; ++i) {
                for (int j = colMin; j <= colMax; ++j) {
                    gameState[i][j] = '0';
                }
            }
            ocean.getFleet().get(index).setCellsLeft(ocean.getFleet().get(index).getLength());
            lastHitShip = -1;
        }
    }

    /**
     * Asks user if he wants to enable the recovery mode.
     *
     * @return true if user wants, otherwise false.
     */
    boolean getRecoveryMode() {
        System.out.println("Enter \"0\" to enable the recovery mode or anything else to not:");
        Scanner in = new Scanner(System.in);
        if (in.hasNextInt())
            if (0 == in.nextInt()) {
                in.nextLine();
                return true;
            }
        return false;
    }

    /**
     * Gets integer value of torpedoes in interval from 0 up to number of ships.
     *
     * @return number of torpedoes.
     */
    int getTorpedoNum() {
        int input = 0;
        boolean flag = true;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of torpedoes (int value in interval 0.." + ocean.getShipsLeft() + "):");

        // Re-enter data until input is correct.
        while (flag) {
            if (in.hasNextInt()) {
                input = in.nextInt();
                in.nextLine();
                // Check if number is in specified interval.
                if (input >= 0 && input <= ocean.getShipsLeft())
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
     * Read input shot from the console.
     *
     * @return parsed shot.
     */
    Shot readShot() {
        boolean flag = true;
        int[] parsedCoors = new int[2];
        Scanner in = new Scanner(System.in);
        boolean torpedoFlag = false;
        System.out.println("Enter row and column separated by space:");

        while (flag) {
            String[] input = in.nextLine().split(" ");
            // Checks if shot is torpedo.
            torpedoFlag = input[0].equals("T");
            String input1, input2;
            // Checks if there are two arguments or three but then shot is torpedo.
            if (input.length == 2 || input.length == 3 && torpedoFlag) {
                if (torpedoFlag) {
                    input1 = input[1];
                    input2 = input[2];
                } else {
                    input1 = input[0];
                    input2 = input[1];
                }
                // Try parse arguments to integer and check if it is valid cell's coordinates.
                if (Main.tryParseInt(input1) && Main.tryParseInt(input2)) {
                    parsedCoors[0] = Integer.parseInt(input1);
                    parsedCoors[1] = Integer.parseInt(input2);
                    if (parsedCoors[0] < ocean.getRowNum() && parsedCoors[1] < ocean.getColNum()
                            && parsedCoors[0] >= 0 && parsedCoors[1] >= 0)
                        flag = false;
                }
            }
            // Print error message if input is incorrect.
            if (flag)
                System.out.println("Wrong input! Try again.\n");
        }

        return new Shot(parsedCoors[0], parsedCoors[1], torpedoFlag);
    }

    /**
     * Kill the ship.
     *
     * @param shipIndex is an index of the ship in ArrayList fleet.
     */
    void kill(int shipIndex) {
        int rowMin = ocean.getFleet().get(shipIndex).getRow();
        int rowMax = ocean.getFleet().get(shipIndex).getEndRow();
        int colMin = ocean.getFleet().get(shipIndex).getCol();
        int colMax = ocean.getFleet().get(shipIndex).getEndCol();

        // Cells of sunk ship have '*' pointer.
        for (int i = rowMin; i <= rowMax; ++i) {
            for (int j = colMin; j <= colMax; ++j) {
                gameState[i][j] = '*';
            }
        }

        // Reduce number of the ships left in the Ocean.
        ocean.kill();
        lastHitShip = -1;
    }

    /**
     * Print current game field in the console.
     */
    void printField() {
        for (int i = 0; i < rowNum; ++i) {
            for (int j = 0; j < colNum; ++j) {
                System.out.print(gameState[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Help message with rules.
     */
    void printRules() {
        System.out.println("Rules:\nAt each game turn the you have to perform" +
                " a shot to an Ocean cell and specify the row and column number of it. For example:" +
                "\n\t3 2\nmeans shot to an Ocean cell with row 3 and column 2.\n" +
                "After each shot, the computer redisplays the current view of the Ocean and" +
                " responds with a single bit of information: \"hit\" or \"miss\".\n" +
                "A ship is \"sunk\" when all the cells it occupies are hit.\n\n" +
                "The representation of an Ocean cell for a human player is one of the following: " +
                "\n\tnot-fired is \"0\"\n\tfired-miss is \"-\"\n\tfired-hit is \"+\"\n\tsunk is \"*\"" +
                "\nYour goal is to sink all the Fleet with as few shots as possible.\n\n" +
                "Also you can activate two modes: \n1.\tTorpedo firing mode\nWhen a ship is hit with a torpedo, " +
                "it becomes sunk entirely, with no respect to its state and size. You should enter shot like:" +
                "\n\t T 3 2\n2.\tShip recovery mode\nWhen this mode is enabled and the user hits a ship, which is" +
                " not sunk yet, the user has to hit the same ship with the next shot. \nOtherwise, the ship gets" +
                " recovered to its initial state (that it had before the first hit in it). \nIt means that to sink" +
                " a ship the user has to hit it by every shot starting from the first hit and until the ship is sunk;" +
                "\notherwise the user needs to repeat the attack from the very beginning.\n\nEnjoy!");
    }
}
