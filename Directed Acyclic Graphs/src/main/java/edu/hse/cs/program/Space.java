package edu.hse.cs.program;

/**
 * This class describes Directed Acyclic Graphs.
 * @author Sherstyugina Anastasia, BSE204.
 */
public final class Space{
    private final Coord2D position;
    private Origin root;

    /**
     * Constructor.
     */
    public Space(){
        position = Coord2D.zeroCoord2D;
        root = null;
    }

    /**
     * Returns origin point of world coordinate system - zero coordinate.
     * @return origin coordinate.
     */
    public Coord2D getPosition() {
        return position;
    }

    /**
     * Gets root of the graph.
     * @return reference of the root.
     * @throws NullPointerException when root is not set.
     */
    public Origin getRoot() throws NullPointerException {
        if(root==null){
            throw new NullPointerException("Root is not set!");
        }
        return root;
    }

    /**
     * Sets root.
     * @param root is a root Point.
     * @throws DAGConstraintException if Point has cycle.
     * @throws IllegalArgumentException if parameter is null.
     */
    public void setRoot(Origin root) throws DAGConstraintException, IllegalArgumentException {
        if(root==null){
            throw new IllegalArgumentException("Root can not be null!");
        }
        if(root.isThereCycle()){
            throw new DAGConstraintException("There can be no cycles in the DAG!");
        } else {
            this.root = root;
            root.clearVisits();
        }
    }

    /**
     * Bound box for the world coordinate system without relative shift.
     * @return the root BoundBox without relativity.
     * @throws NullPointerException when root is not set.
     */
    public BoundBox getBoundBox() throws NullPointerException{
        if(root==null){
            throw new NullPointerException("Root is not set!");
        }
        BoundBox newBoundBox = root.getBoundBox();
        return new BoundBox(Coord2D.addCoord2D(newBoundBox.getMinPoint(),root.getPosition()),
                Coord2D.addCoord2D(newBoundBox.getMaxPoint(),root.getPosition()));
    }
}
