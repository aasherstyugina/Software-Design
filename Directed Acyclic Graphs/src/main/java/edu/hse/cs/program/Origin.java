package edu.hse.cs.program;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class describes origin point of some coordinate system. It's a subclass of Point.
 * @author Sherstyugina Anastasia, BSE204.
 */
public final class Origin extends Point{
    private Set<Point> children;

    /**
     * Constructor.
     * @param position is origin point's coordinate.
     */
    public Origin(Coord2D position){
        super(position);
        children = new HashSet<Point>();
    }

    /**
     * Gets set of children.
     * @return Set<Point> Collection.
     */
    public Set<Point> getChildren() {
        return children;
    }

    /**
     * Sets set of children.
     * @param children new value for children.
     * @throws DAGConstraintException when graph with new set of children has cycles.
     * @throws IllegalArgumentException when there is a null in a Set<Point> children parameter.
     */
    public void setChildren(Set<Point> children) throws DAGConstraintException, IllegalArgumentException {
        if(children.contains(null)){
            throw new IllegalArgumentException("You can not set null as a child!");
        }

        if(children.contains(this)){
            throw new IllegalArgumentException("You can not set this object as a child of itself!");
        }

        HashSet<Point> copyChildren = new HashSet<Point>(children);
        Set<Point> oldChildren = this.children;
        this.children = copyChildren;
        if(isThereCycle()){
            this.children=oldChildren;
            throw new DAGConstraintException("There can be no cycles in the DAG!");
        }
        clearVisits();
    }

    /**
     * Bound box for origin without relativity.
     * @return the resulting BoundBox.
     */
    @Override
    public BoundBox getBoundBox() {
        BoundBox result = super.getBoundBox();
        result = new BoundBox(Coord2D.subtractCoord2D(result.getMinPoint(), super.getPosition()),
                Coord2D.subtractCoord2D(result.getMaxPoint(), super.getPosition()));
        return result;
    }

    /**
     * Overrided method for Origin nodes, because graph traverse is necessary.
     * @return true, if there is cycle in the graph, otherwise false.
     */
    @Override
    protected boolean isThereCycle(){
        return traverse(this);
    }

    /**
     * Graph traverse.
     * @param current is a checking Point object.
     * @return true, if cycle is found, and false, if traverse ends without finding any cycle.
     */
    private boolean traverse(Point current){
        // Make point as visited.
        current.setVisit(1);
        if(current instanceof Origin) {
            for(Point child: ((Origin) current).getChildren()){
                // Traverse for child, if it was not visited yet.
                if(child.getVisit() == 0){
                    // If cycle is found in child.
                    if(traverse(child)){
                        return true;
                    }
                }
                // If child was visited and was not left, cycle is found.
                if(child.getVisit() == 1){
                    return true;
                }
            }
        }
        // If it's a Point, or it's an Origin, but all children have no cycles,
        // then make point as visited and left.
        current.setVisit(2);
        return false;
    }

    /**
     * Deep reset of traverse flag.
     */
    @Override
    protected void clearVisits() {
        super.clearVisits();
        for(Point child:children){
            child.clearVisits();
        }
    }

    /**
     * Overrided recursive count of bound box considering children.
     * @return resulting BoundBox object for Origin.
     */
    @Override
    protected BoundBox countBound(){
        Iterator<Point> iterator = children.iterator();
        // If Origin has children, otherwise it's like a Point.
        if(iterator.hasNext()){
            // Take first child to compare with others.
            BoundBox result = iterator.next().countBound();
            while(iterator.hasNext()){
                BoundBox current = iterator.next().countBound();
                result = getBoundsOfTwo(result,current);
            }
            // Take into account relativity.
            result = new BoundBox(Coord2D.addCoord2D(result.getMinPoint(),super.getPosition()),
                    Coord2D.addCoord2D(result.getMaxPoint(),super.getPosition()));
            return result;
        } else{
            return new BoundBox(super.getPosition(), super.getPosition());
        }
    }

    /**
     * Static method for counting bound box for two other BoundBox objects.
     * @param first is the first BoundBox object.
     * @param second is the second BoundBox object.
     * @return the resulting BoundBox object for two bound boxes.
     */
    private static BoundBox getBoundsOfTwo(BoundBox first, BoundBox second){
        Coord2D resultMin = getMinCoord2D(first.getMinPoint(), second.getMinPoint());
        Coord2D resultMax = getMaxCoord2D(first.getMaxPoint(),second.getMaxPoint());

        return new BoundBox(resultMin,resultMax);
    }

    /**
     * Static method for counting min coordinate from two Coord2D objects.
     * @param first is the first coordinate.
     * @param second is the second coordinate.
     * @return min coordinate.
     */
    private static Coord2D getMinCoord2D(Coord2D first, Coord2D second){
        double minX = Math.min(first.getX(), second.getX());
        double minY = Math.min(first.getY(), second.getY());
        return new Coord2D(minX, minY);
    }

    /**
     * Static method for counting max coordinate from two Coord2D objects.
     * @param first is the first coordinate.
     * @param second is the second coordinate.
     * @return max coordinate.
     */
    private static Coord2D getMaxCoord2D(Coord2D first, Coord2D second){
        double maxX = Math.max(first.getX(), second.getX());
        double maxY = Math.max(first.getY(), second.getY());
        return new Coord2D(maxX, maxY);
    }
}
