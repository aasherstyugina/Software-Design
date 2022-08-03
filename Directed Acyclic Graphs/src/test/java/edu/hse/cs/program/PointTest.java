package edu.hse.cs.program;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Point class.
 * @author Sherstyugina Anastasia, BSE204.
 */
class PointTest {
    /**
     * Checking that the constructor does not allow working with null.
     */
    @Test
    void testNullParameter(){
        assertThrows(IllegalArgumentException.class, ()->{Point point = new Point(null);});
        assertDoesNotThrow(()->{Point point = new Point(new Coord2D(1,1));});
    }

    /**
     * Checking get and set methods for Point's position.
     */
    @Test
    void testChangePosition(){
        Coord2D position = new Coord2D(1,3);
        Point point = new Point(position);
        assertEquals(position,point.getPosition());
        Coord2D newPosition = new Coord2D(2,2);
        point.setPosition(newPosition);
        assertEquals(newPosition,point.getPosition());
    }

    /**
     * Checking that point's BoundBox is coordinates of this Point (even after changing point's position).
     */
    @Test
    void testPointBoundBox(){
        Coord2D position = new Coord2D(3,4);
        Point point = new Point(position);
        BoundBox boundBox = new BoundBox(position,position);
        assertEquals(boundBox,point.getBoundBox());
        Coord2D newPosition = new Coord2D(1,6);
        point.setPosition(newPosition);
        BoundBox newBoundBox = new BoundBox(newPosition,newPosition);
        assertEquals(newBoundBox,point.getBoundBox());
    }
}