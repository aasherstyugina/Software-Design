package battleship;

/**
 * This class describes destroyer. It's a subclass of Ship.
 */
public class Destroyer extends Ship {
    /**
     * Constructor.
     */
    public Destroyer() {
        length = 2;
        cellsLeft = 2;
    }

    /**
     * Overrided toString method.
     *
     * @return class name.
     */
    @Override
    public String toString() {
        return "destroyer";
    }
}
