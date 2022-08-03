package battleship;

/**
 * This class describes submarine. It's a subclass of Ship.
 */
public class Submarine extends Ship {
    /**
     * Constructor.
     */
    public Submarine() {
        length = 1;
        cellsLeft = 1;
    }

    /**
     * Overrided toString method.
     *
     * @return class name.
     */
    @Override
    public String toString() {
        return "submarine";
    }
}
