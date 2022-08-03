package battleship;

/**
 * This class describes carrier. It's a subclass of Ship.
 */
public class Carrier extends Ship {
    /**
     * Constructor.
     */
    public Carrier() {
        length = 5;
        cellsLeft = 5;
    }

    /**
     * Overrided toString method.
     *
     * @return class name.
     */
    @Override
    public String toString() {
        return "carrier";
    }
}
