package edu.hse.cs.program;

/**
 * An immutable class defining mathematical coordinates.
 * @author Sherstyugina Anastasia, BSE204.
 */
public class Coord2D {
    private final double x;
    private  final  double y;
    public static final Coord2D zeroCoord2D = new Coord2D(0,0);

    /**
     * Constructor.
     * @param x is the first coordinate.
     * @param y is the second coordinate.
     */
    public Coord2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the first coordinate.
     * @return double coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the second coordinate.
     * @return double coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Subtracts coordinates.
     * @param first is the first coordinate.
     * @param second is the second coordinate.
     * @return the result of subtraction.
     */
    protected static Coord2D subtractCoord2D(Coord2D first, Coord2D second){
        double newX = first.getX() - second.getX();
        double newY = first.getY() - second.getY();
        return new Coord2D(newX, newY);
    }

    /**
     * Adds coordinates.
     * @param first is the first coordinate.
     * @param second is the second coordinate.
     * @return the result of addition.
     */
    protected static Coord2D addCoord2D(Coord2D first, Coord2D second){
        double newX = first.getX()+ second.getX();
        double newY = first.getY() + second.getY();
        return new Coord2D(newX, newY);
    }

    /**
     * Overrided method for comparing two Coord2D objects.
     * @param other is the object being compared to this.
     * @return true, if objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Coord2D coord2D = (Coord2D) other;
        return Double.compare(coord2D.x, x) == 0 && Double.compare(coord2D.y, y) == 0;
    }
}
