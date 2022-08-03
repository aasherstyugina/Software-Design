package battleship;

/**
 * This class describes ship.
 */
public abstract class Ship {
    // Private fields.
    private int row;
    private int col;
    private Direction direction;

    // Protected fields.
    protected int cellsLeft;
    protected int length;

    /**
     * Gets ship's length.
     *
     * @return length.
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets row number of the start of the ship.
     *
     * @return row number.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets row number of the end of the ship.
     *
     * @return row number.
     */
    public int getEndRow() {
        return row + (length - 1) * direction.getRowDir();
    }

    /**
     * Gets column number of the end of the ship.
     *
     * @return column number.
     */
    public int getEndCol() {
        return col + (length - 1) * direction.getColDir();
    }

    /**
     * Gets column number of the start of the ship.
     *
     * @return column number.
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets row number of the start of the ship.
     *
     * @param row is a row number.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets column number of the start of the ship.
     *
     * @param col is a column number.
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Gets ship's direction.
     *
     * @return direction.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets ship's direction.
     *
     * @param direction is a direction.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Shot in the ship.
     *
     * @return true if there is at least one ship-cell left, otherwise false.
     */
    public boolean shot() {
        return (--cellsLeft > 0);
    }

    /**
     * Gets the number of the ship-cells which were not hit.
     *
     * @return the number of cells.
     */
    public int getCellsLeft() {
        return cellsLeft;
    }

    /**
     * Sets the number of the ship-cells which were not hit.
     *
     * @param cellsLeft is a number of not hit cells.
     */
    public void setCellsLeft(int cellsLeft) {
        this.cellsLeft = cellsLeft;
    }
}
