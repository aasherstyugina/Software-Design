package edu.hse.cs.program;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Origin class.
 * @author Sherstyugina Anastasia, BSE204.
 */
class OriginTest {
    /**
     * Checking that the constructor does not allow working with null.
     */
    @Test
    void testNullParameter(){
        assertThrows(IllegalArgumentException.class, ()->new Origin(null));
        assertDoesNotThrow(()->new Origin(new Coord2D(7,2)));
    }

    /**
     * Checking that setChildren method does not allow make cycles.
     */
    @Test
    void testSetChildrenCycle() {
        HashSet<Point> hashSet1 = new HashSet<Point>();
        HashSet<Point> hashSet2 = new HashSet<Point>();
        HashSet<Point> hashSet3 = new HashSet<Point>();
        HashSet<Point> hashSet4 = new HashSet<Point>();
        Origin origin1 = new Origin(new Coord2D(1,1));
        Origin origin2 = new Origin(new Coord2D(-2,1));
        Origin origin3 = new Origin(new Coord2D(1,0));
        Origin origin4 = new Origin(new Coord2D(1,5));
        hashSet1.add(origin2);
        hashSet2.add(origin3);
        hashSet3.add(origin4);
        hashSet4.add(origin1);
        assertDoesNotThrow(()->origin1.setChildren(hashSet1));
        assertDoesNotThrow(()->origin2.setChildren(hashSet2));
        assertDoesNotThrow(()->origin3.setChildren(hashSet3));
        assertThrows(DAGConstraintException.class, ()->origin4.setChildren(hashSet4));
    }

    /**
     * Checking we can not make a cycle from the outside.
     */
    @Test
    void testMakeCycleOutside(){
        Origin origin1 = new Origin(new Coord2D(1,1));
        Origin origin2 = new Origin(new Coord2D(-2,1));
        HashSet<Point> hashSet1 = new HashSet<Point>();
        HashSet<Point> hashSet2 = new HashSet<Point>();
        hashSet1.add(origin2);
        assertDoesNotThrow(()->{
            origin1.setChildren(hashSet1);
            origin2.setChildren(hashSet2);
        });
        // Checking we can not make a cycle using hashset reference from the outside,
        // not using setChildren method.
        hashSet2.add(origin1);
        assertFalse(origin2.isThereCycle());
        // Checking we still have references to created objects.
        Coord2D newPosition = new Coord2D(2,8);
        origin1.setPosition(newPosition);
        assertEquals(newPosition, origin1.getPosition());
    }

    /**
     * Checking that we can not add null child in the origin.
     * @throws DAGConstraintException when the cycle is found.
     * @throws IllegalArgumentException when the child is null.
     */
    @Test
    void testSetNullChild1() throws DAGConstraintException, IllegalArgumentException{
        Origin origin1 = new Origin(new Coord2D(1,1));
        Origin origin2 = new Origin(new Coord2D(-2,1));
        HashSet<Point> hashSet1 = new HashSet<Point>();
        HashSet<Point> hashSet2 = new HashSet<Point>();
        hashSet2.add(new Point(Coord2D.zeroCoord2D));
        hashSet2.add(null);
        hashSet1.add(origin2);
        origin1.setChildren(hashSet1);
        assertThrows(IllegalArgumentException.class, ()->origin2.setChildren(hashSet2));
    }

    /**
     * Checking if it is ok to have a reusable point in the DAG.
     * @throws DAGConstraintException when the cycle is found.
     */
    @Test
    void testReusablePoint() throws DAGConstraintException {
        HashSet<Point> hashSet1 = new HashSet<Point>();
        HashSet<Point> hashSet2 = new HashSet<Point>();
        HashSet<Point> hashSet3 = new HashSet<Point>();
        Origin origin1 = new Origin(new Coord2D(1,1));
        Origin origin2 = new Origin(new Coord2D(-2,1));
        Origin origin3 = new Origin(new Coord2D(1,0));
        Point point = new Point(new Coord2D(-1,-1));
        hashSet1.add(origin2);
        hashSet1.add(origin3);
        hashSet2.add(point);
        hashSet3.add(point);
        origin2.setChildren(hashSet2);
        origin3.setChildren(hashSet3);
        origin1.setChildren(hashSet1);
        assertFalse(origin1.isThereCycle());
    }

    /**
     * Checking if it is ok to have a reusable origin in the DAG.
     * @throws DAGConstraintException when the cycle is found.
     */
    @Test
    void testReusableOrigin() throws DAGConstraintException{
        HashSet<Point> hashSet1 = new HashSet<Point>();
        HashSet<Point> hashSet2 = new HashSet<Point>();
        HashSet<Point> hashSet3 = new HashSet<Point>();
        HashSet<Point> hashSet4 = new HashSet<Point>();
        Origin origin1 = new Origin(new Coord2D(1,1));
        Origin origin2 = new Origin(new Coord2D(-2,1));
        Origin origin3 = new Origin(new Coord2D(1,0));
        Origin origin4 = new Origin(new Coord2D(1,0));
        Point point = new Point(new Coord2D(5,5));
        hashSet1.add(origin2);
        hashSet1.add(origin3);
        hashSet2.add(origin4);
        hashSet3.add(origin4);
        hashSet4.add(point);
        origin1.setChildren(hashSet1);
        origin2.setChildren(hashSet2);
        origin3.setChildren(hashSet3);
        origin4.setChildren(hashSet4);
        assertFalse(origin1.isThereCycle());
    }

    /**
     * Checking bound boxes counting for origins.
     * @throws DAGConstraintException when cycle is found.
     */
    @Test
    void testOriginBoundBox() throws DAGConstraintException {
        HashSet<Point> hashSet1 = new HashSet<Point>();
        HashSet<Point> hashSet2 = new HashSet<Point>();
        HashSet<Point> hashSet3 = new HashSet<Point>();
        Origin origin1 = new Origin(new Coord2D(1,1));
        Origin origin2 = new Origin(new Coord2D(-2,1));
        Origin origin3 = new Origin(new Coord2D(1,0));
        Point point = new Point(new Coord2D(-1,-1));
        hashSet1.add(origin2);
        hashSet1.add(origin3);
        hashSet2.add(point);
        hashSet3.add(point);
        origin2.setChildren(hashSet2);
        origin3.setChildren(hashSet3);
        origin1.setChildren(hashSet1);
        BoundBox boundBoxOr1 = new BoundBox(new Coord2D(-3,-1), new Coord2D(0,0));
        BoundBox boundBoxOr2 = new BoundBox(new Coord2D(-1,-1), new Coord2D(-1,-1));
        BoundBox boundBoxOr3 = new BoundBox(new Coord2D(-1,-1), new Coord2D(-1,-1));
        assertEquals(boundBoxOr1,origin1.getBoundBox());
        assertEquals(boundBoxOr2,origin2.getBoundBox());
        assertEquals(boundBoxOr3,origin3.getBoundBox());
    }

    /**
     * Checking that origin can not be child of itself.
     */
    @Test
    void testChildYourself() {
        HashSet<Point> hashSet1 = new HashSet<Point>();
        Origin origin1 = new Origin(new Coord2D(1,1));
        hashSet1.add(origin1);
        assertThrows(IllegalArgumentException.class,()->origin1.setChildren(hashSet1));
    }
}