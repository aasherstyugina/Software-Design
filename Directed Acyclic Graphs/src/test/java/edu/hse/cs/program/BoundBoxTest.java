package edu.hse.cs.program;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing BoundBox class.
 * @author Sherstyugina Anastasia, BSE204.
 */
class BoundBoxTest {
    /**
     * Checking that the constructor does not allow working with null.
     */
    @Test
    void testNullParameters(){
        Coord2D coordinate = new Coord2D(1,1);
        assertThrows(IllegalArgumentException.class,()->{BoundBox boundBox = new BoundBox(null,Coord2D.zeroCoord2D);});
        assertThrows(IllegalArgumentException.class,()->{BoundBox boundBox = new BoundBox(coordinate,null);});
        assertThrows(IllegalArgumentException.class,()->{BoundBox boundBox = new BoundBox(null,null);});
        assertDoesNotThrow(()->{BoundBox boundBox = new BoundBox(coordinate,coordinate);});
    }
}