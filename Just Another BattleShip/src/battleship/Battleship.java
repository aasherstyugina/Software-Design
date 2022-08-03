package battleship;

/**
 * This class describes battleship. It's a subclass of Ship.
 */
public class Battleship extends Ship {
    /**
     * Constructor.
     */
    public Battleship() {
        length = 4;
        cellsLeft = 4;
    }

    /**
     * Overrided toString method.
     *
     * @return class name.
     */
    @Override
    public String toString() {
        return "battleship";
    }
}
