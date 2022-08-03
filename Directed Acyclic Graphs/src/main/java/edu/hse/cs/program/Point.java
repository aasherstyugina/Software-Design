package edu.hse.cs.program;

/**
 * This class describes physical point.
 * @author Sherstyugina Anastasia, BSE204.
 */
public class Point {
    private Coord2D position;
    private int visit;

    /**
     * Constructor.
     * @param position is point's coordinates relative to the coordinate system.
     * @throws IllegalArgumentException when parameter is null.
     */
    public Point(Coord2D position)throws IllegalArgumentException{
        if(position==null){
            throw new IllegalArgumentException("Point's position can not be null!");
        }
        this.position = position;
    }

    /**
     * Gets point's position.
     * @return Coord2D coordinate
     */
    public final Coord2D getPosition() {
        return position;
    }

    /**
     * Sets point's position.
     * @param newPosition is new value for position.
     * @throws IllegalArgumentException when parameter is null.
     */
    public final void setPosition(Coord2D newPosition) throws IllegalArgumentException{
        if(newPosition==null){
            throw new IllegalArgumentException("Point's position can not be null!");
        }
        position = newPosition;
    }

    /**
     * Bound box for the point has coordinates of this point.
     * @return the resulting BoundBox.
     */
    public BoundBox getBoundBox() {
        return countBound();
    }

    /**
     * Bound box for the point has coordinates of this point.
     * @return the resulting BoundBox.
     */
    protected BoundBox countBound(){
        return new BoundBox(position, position);
    }

    /**
     * If node is not Origin, then there is no cycle.
     * @return false.
     */
    protected boolean isThereCycle(){
        return false;
    }

    /**
     * Return the traversal flag.
     * @return 0 if the node was never visited, 1 if node was visited, 2 if node was visited and left.
     */
    protected final int getVisit() {
        return visit;
    }

    /**
     * Sets the traversal flag.
     * @param visit is node state.
     */
    protected final void setVisit(int visit){
        this.visit = visit;
    }

    /**
     * Resets the traversal flag.
     */
    protected void clearVisits(){
        visit = 0;
    }
}
