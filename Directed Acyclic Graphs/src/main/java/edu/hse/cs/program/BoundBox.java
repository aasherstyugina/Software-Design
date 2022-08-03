package edu.hse.cs.program;

/**
 * An immutable class of bounding box.
 * @author Sherstyugina Anastasia, BSE204.
 */
public final class BoundBox {
    private final Coord2D minPoint;
    private final Coord2D maxPoint;

    /**
     * Constructor.
     * @param minPoint is the left-bottom coordinate of the box.
     * @param maxPoint is the right-top coordinate of the box.
     * @throws IllegalArgumentException when at least one of parameters is null.
     */
    public BoundBox(Coord2D minPoint, Coord2D maxPoint) throws IllegalArgumentException{
        if(minPoint==null || maxPoint == null){
            throw new IllegalArgumentException("BoundBox coordinates can not be null!");
        }
        this.minPoint=minPoint;
        this.maxPoint=maxPoint;
    }

    /**
     * Gets the left-bottom coordinate of the box.
     * @return Coord2D coordinate.
     */
    public Coord2D getMinPoint() {
        return minPoint;
    }

    /**
     * Gets the right-top coordinate of the box.
     * @return Coord2D coordinate.
     */
    public Coord2D getMaxPoint() {
        return maxPoint;
    }

    /**
     * Overrided method for comparing two BoundBox objects.
     * @param other is the object being compared to this.
     * @return true, if objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        BoundBox boundBox = (BoundBox) other;
        return minPoint.equals(boundBox.minPoint) && maxPoint.equals(boundBox.maxPoint);
    }
}
