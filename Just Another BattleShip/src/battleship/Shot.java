package battleship;

/**
 * This class describes shot.
 */
public class Shot {
    // Private fields.
    private int row;
    private int col;
    private boolean isTorpedo;

    /**
     * Constructor.
     *
     * @param a    is a row number.
     * @param b    is a column number.
     * @param flag is a torpedo flag.
     */
    public Shot(int a, int b, boolean flag) {
        row = a;
        col = b;
        isTorpedo = flag;
    }

    /**
     * Gets row number of the shot.
     *
     * @return row number.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets column number of the shot.
     *
     * @return column number.
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets if shot is torpedo.
     *
     * @return true if shot is torpedo, otherwise false.
     */
    public boolean isTorpedo() {
        return isTorpedo;
    }
}
