package battleship;

/**
 * This class describes cell.
 */
public class Cell {
    // Private field.
    private int shipIndex;

    /**
     * Constructor.
     */
    public Cell() {
        shipIndex = -1;
    }

    /**
     * Gets index of the ship which is positioned in this cell.
     *
     * @return index of the ship in ArrayList fleet.
     */
    public int getShipIndex() {
        return shipIndex;
    }

    /**
     * Sets index of the ship which is positioned in this cell.
     *
     * @param shipIndex is an index of the ship in ArrayList fleet.
     */
    public void setShipIndex(int shipIndex) {
        this.shipIndex = shipIndex;
    }
}
