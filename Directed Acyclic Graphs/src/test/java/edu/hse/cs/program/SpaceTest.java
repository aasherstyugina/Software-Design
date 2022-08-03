package edu.hse.cs.program;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Space class.
 * @author Sherstyugina Anastasia, BSE204.
 */
class SpaceTest {
    /**
     * Checking that we can not get root if it is null, also setting root and bound box counting.
     * We test if there are any cycles in the origin in OriginTest.
     * @throws DAGConstraintException when the cycle is found.
     */
    @Test
    void testGetSetRootAndBoundBox() throws DAGConstraintException {
        Space space = new Space();

        // Space position is (0;0) as world coordinate system.
        assertEquals(Coord2D.zeroCoord2D, space.getPosition());

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

        space.setRoot(origin1);
        BoundBox boundBoxOr1 = new BoundBox(new Coord2D(-2,0),new Coord2D(1,1));
        assertEquals(boundBoxOr1, space.getBoundBox());
    }

    /**
     * Checking operations with null root.
     */
    @Test
    void testNullRoot(){
        Space space = new Space();
        assertThrows(NullPointerException.class,()->space.getRoot());
        assertThrows(IllegalArgumentException.class,()->space.setRoot(null));
        assertThrows(NullPointerException.class,()->space.getBoundBox());
    }

    /**
     * Checking that origin can not be child of itself.
     */
    @Test
    void testRootChildItself() throws DAGConstraintException {
        Space space = new Space();
        HashSet<Point> hashSet1 = new HashSet<Point>();
        Origin origin1 = new Origin(new Coord2D(1,1));
        hashSet1.add(origin1);
        space.setRoot(origin1);
        assertThrows(IllegalArgumentException.class,()->origin1.setChildren(hashSet1));
    }
}