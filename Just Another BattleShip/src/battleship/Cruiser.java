package battleship;

/**
 * This class describes cruiser. It's a subclass of Ship.
 */
public class Cruiser extends Ship {
    /**
     * Constructor.
     */
    public Cruiser() {
        length = 3;
        cellsLeft = 3;
    }

    /**
     * Overrided toString method.
     *
     * @return class name.
     */
    @Override
    public String toString() {
        return "cruiser";
    }
}
