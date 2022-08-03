package battleship;

/**
 * This class describes direction.
 */
public class Direction {
    // Private field.
    private boolean isHorizontal;

    /**
     * Constructor.
     *
     * @param dir is an integer index to define direction.
     */
    public Direction(int dir) {
        isHorizontal = dir == 0;
    }

    /**
     * Gets an integer index of direction.
     *
     * @return 0 if ship is horizontal, otherwise 1.
     */
    public int getDir() {
        return isHorizontal ? 0 : 1;
    }

    /**
     * Gets a coefficient for vertical direction.
     *
     * @return 0 if ship is horizontal, otherwise 1.
     */
    public int getRowDir() {
        return isHorizontal ? 0 : 1;
    }

    /**
     * Gets a coefficient for horizontal direction.
     *
     * @return 1 if ship is horizontal, otherwise 0.
     */
    public int getColDir() {
        return isHorizontal ? 1 : 0;
    }
}
