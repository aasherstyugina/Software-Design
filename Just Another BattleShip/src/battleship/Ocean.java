package battleship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class describes the Ocean.
 */
public class Ocean {
    // Private fields.
    private Cell[][] gameField;
    private int rowNum;
    private int colNum;
    private int shipsLeft;
    private ArrayList<Ship> fleet;

    /**
     * Constructor.
     *
     * @param size is the Ocean size (number of rows and columns).
     */
    Ocean(int[] size) {
        rowNum = size[0];
        colNum = size[1];
        gameField = new Cell[rowNum][colNum];
        for (int i = 0; i < rowNum; ++i) {
            for (int j = 0; j < colNum; ++j)
                gameField[i][j] = new Cell();
        }
        fleet = new ArrayList<>();
    }

    /**
     * Adds specific types of ships to ArrayList.
     *
     * @param shipsNum is array of the ships number (0 or above) for each ship type.
     */
    void createFleet(int[] shipsNum) {
        for (int i = 0; i < shipsNum[0]; ++i) {
            fleet.add(new Carrier());
        }
        for (int i = 0; i < shipsNum[1]; ++i) {
            fleet.add(new Battleship());
        }
        for (int i = 0; i < shipsNum[2]; ++i) {
            fleet.add(new Cruiser());
        }
        for (int i = 0; i < shipsNum[3]; ++i) {
            fleet.add(new Destroyer());
        }
        for (int i = 0; i < shipsNum[4]; ++i) {
            fleet.add(new Submarine());
        }
        for (int i = 0; i < 5; ++i) {
            shipsLeft += shipsNum[i];
        }

        // Randomly shuffle the order of ships.
        Collections.shuffle(fleet, new Random());
    }

    /**
     * Randomly places the defined combination of the ships in the Ocean.
     *
     * @return true if it's possible to arrange the Fleet, otherwise false.
     */
    boolean arrangeFleet() {
        if (!ifFleetCanBeArranged())
            return false;

        int i = 0;
        while (i < fleet.size()) {
            // Position the ship if it's possible, otherwise rearrange the previous ship.
            if (!findRandomPosition(i)) {
                int last = getBackToLastShip(i - 1);
                if (last < 0)
                    return false;
                else
                    i = last + 1;
            } else
                ++i;
        }

        return true;
    }

    /**
     * Checks if ships fit in the Ocean.
     *
     * @return true if the length of every ship is less than at least
     * one of the sides of the Ocean, otherwise false.
     */
    boolean ifFleetCanBeArranged() {
        boolean flag = true;
        for (int i = 0; i < fleet.size(); ++i) {
            int length = fleet.get(i).getLength();
            if (rowNum < length && colNum < length) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    /**
     * Checks if it's possible to position the ship in
     * the specified place and satisfy the adjacency rules.
     *
     * @param row    is a row number of the start of the ship.
     * @param col    is a column number of the start of the ship.
     * @param dir    is a direction (horizontal or vertical).
     * @param length is ship's length.
     * @return true if the ship can be positioned in this place, otherwise false.
     */
    boolean ifSheepCanBePositioned(int row, int col, Direction dir, int length) {
        int rowDir = dir.getRowDir(), colDir = dir.getColDir();

        // Check if the end of the ship does not go beyond the ocean boundaries.
        if (row + (length - 1) * rowDir >= rowNum || col + (length - 1) * colDir >= colNum) {
            return false;
        }

        // Find the boundaries of an area that must be empty to place a ship in it.
        int rowMin, rowMax, colMin, colMax;
        if (colDir == 0) {
            rowMin = Math.max(0, Math.min(row - rowDir, row + length * rowDir));
            rowMax = Math.min(rowNum - 1, Math.max(row - rowDir, row + length * rowDir));
            colMin = Math.max(col - 1, 0);
            colMax = Math.min(col + 1, colNum - 1);
        } else {
            colMin = Math.max(0, Math.min(col - colDir, col + length * colDir));
            colMax = Math.min(colNum - 1, Math.max(col - colDir, col + length * colDir));
            rowMin = Math.max(row - 1, 0);
            rowMax = Math.min(row + 1, rowNum - 1);
        }

        // Check if area is empty.
        boolean flag = true;
        for (int i = rowMin; i <= rowMax; ++i) {
            for (int j = colMin; j <= colMax; ++j) {
                if (gameField[i][j].getShipIndex() != -1) {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                break;
            }
        }

        return flag;
    }

    /**
     * Positions the ship in the specified place.
     *
     * @param currentShip is an index of the ship in ArrayList fleet.
     * @param row         is a row number of the start of the ship.
     * @param col         is a column number of the start of the ship.
     * @param direction   is a direction (horizontal or vertical).
     */
    void positionShip(int currentShip, int row, int col, Direction direction) {
        int length = fleet.get(currentShip).getLength();
        fleet.get(currentShip).setRow(row);
        fleet.get(currentShip).setCol(col);
        fleet.get(currentShip).setDirection(direction);

        // Cells in the specified area gets ship's index.
        for (int i = row; i < row + length * direction.getRowDir(); ++i) {
            gameField[i][col].setShipIndex(currentShip);
        }
        for (int i = col; i < col + length * direction.getColDir(); ++i) {
            gameField[row][i].setShipIndex(currentShip);
        }
    }

    /**
     * Finds random place to position the ship.
     *
     * @param current is an index of the ship in ArrayList fleet.
     * @return true if at least one place was found, otherwise false.
     */
    boolean findRandomPosition(int current) {
        boolean flag = false;
        int length = fleet.get(current).getLength();
        for (int indRow = 0; indRow < rowNum; ++indRow) {
            for (int indCol = 0; indCol < colNum; ++indCol) {
                for (int indDir = 0; indDir < 2; ++indDir) {
                    if (ifSheepCanBePositioned(indRow, indCol, new Direction(indDir), length)) {
                        positionShip(current, indRow, indCol, new Direction(indDir));
                        flag = true;
                        break;
                    }
                }
                if (flag)
                    break;
            }
            if (flag)
                break;
        }

        return flag;
    }

    /**
     * Remove ship from the Ocean.
     *
     * @param index is an index of the ship in ArrayList fleet.
     */
    void removeShip(int index) {
        Ship current = fleet.get(index);
        int row = current.getRow();
        int col = current.getCol();
        int length = current.getLength();
        Direction direction = current.getDirection();

        // Remove ship's index from the cells where ship was positioned.
        for (int i = row; i < row + length * direction.getRowDir(); ++i) {
            gameField[i][col].setShipIndex(-1);
        }
        for (int i = col; i < col + length * direction.getColDir(); ++i) {
            gameField[row][i].setShipIndex(-1);
        }

        fleet.get(index).setRow(-1);
        fleet.get(index).setCol(-1);
        fleet.get(index).setDirection(null);
    }

    /**
     * Finds newt possible random position for the ship which is already positioned.
     *
     * @param current is an index of the ship in ArrayList fleet.
     * @return true if at least one place was found, otherwise false.
     */
    boolean findNextRandomPosition(int current) {
        int indRow = fleet.get(current).getRow();
        int indCol = fleet.get(current).getCol();
        int indDir = fleet.get(current).getDirection().getDir();

        if (indRow == rowNum - 1) {
            if (indCol == colNum - 1) {
                if (indDir == 1) {
                    return false;
                } else {
                    ++indDir;
                }
            } else {
                ++indRow;
                indCol = 0;
            }
        } else {
            ++indCol;
            indDir = 0;
        }

        // Remove ship from current position and find new.
        boolean flag = false;
        removeShip(current);
        for (int i1 = indRow; i1 < rowNum; ++i1) {
            for (int i2 = indCol; i2 < colNum; ++i2) {
                for (int i3 = indDir; i3 < 2; ++i3) {
                    Direction dir = new Direction(i3);
                    if (ifSheepCanBePositioned(i1, i2, dir, fleet.get(current).getLength())) {
                        positionShip(current, i1, i2, dir);
                        flag = true;
                        break;
                    }
                }
                if (flag)
                    break;
            }
            if (flag)
                break;
        }
        return flag;
    }

    /**
     * Rearrange the ship.
     *
     * @param previous is an index of the ship in ArrayList fleet.
     * @return index of the last ship which was rearranged.
     */
    int getBackToLastShip(int previous) {
        if (previous >= 0)
            // If it's impossible to rearrange current ship, rearrange the previous ship.
            if (!findNextRandomPosition(previous))
                return getBackToLastShip(previous - 1);
        return previous;
    }

    /**
     * Gets number of rows in the Ocean field.
     *
     * @return number of rows.
     */
    public int getRowNum() {
        return rowNum;
    }

    /**
     * Gets number of columns in the Ocean field.
     *
     * @return number of columns.
     */
    public int getColNum() {
        return colNum;
    }

    /**
     * Gets representation of the Ocean.
     *
     * @return cells array .
     */
    public Cell[][] getGameField() {
        return gameField;
    }

    /**
     * Shot in the ship.
     *
     * @param shipIndex is an index of the ship in ArrayList fleet.
     * @return true if the ship is not sunk, otherwise false.
     */
    public boolean shot(int shipIndex) {
        return fleet.get(shipIndex).shot();
    }

    /**
     * Gets representation of the Fleet.
     *
     * @return ArrayList of ships.
     */
    public ArrayList<Ship> getFleet() {
        return fleet;
    }

    /**
     * Gets number of ships which are not sunk.
     *
     * @return number of the ships left in the Ocean.
     */
    public int getShipsLeft() {
        return shipsLeft;
    }

    /**
     * Reduces the number of ships left in the Ocean.
     */
    public void kill() {
        --shipsLeft;
    }
}
