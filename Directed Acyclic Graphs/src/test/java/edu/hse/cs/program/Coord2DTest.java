package edu.hse.cs.program;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Coord2D class.
 * @author Sherstyugina Anastasia, BSE204.
 */
class Coord2DTest {
    /**
     * Two tests to check subtraction and equal methods.
     */
    @Test
    void testSubtract1(){
        Coord2D first = new Coord2D(1,-5);
        Coord2D second = new Coord2D(-1,3);
        Coord2D methodResult = Coord2D.subtractCoord2D(first,second);
        Coord2D expectedResult = new Coord2D(2,-8);
        assertEquals(expectedResult,methodResult);
    }

    @Test
    void testSubtract2(){
        Coord2D first = new Coord2D(-8,6);
        Coord2D second = new Coord2D(-5,-10);
        Coord2D methodResult = Coord2D.subtractCoord2D(first,second);
        Coord2D expectedResult = new Coord2D(-3,16);
        assertTrue(methodResult.equals(expectedResult));
    }

    /**
     * Two tests to check addition and equal methods.
     */
    @Test
    void testAdd1(){
        Coord2D first = new Coord2D(1,-5);
        Coord2D second = new Coord2D(-1,3);
        Coord2D methodResult = Coord2D.addCoord2D(first,second);
        Coord2D expectedResult = new Coord2D(0,-2);
        assertEquals(expectedResult,methodResult);
    }

    @Test
    void testAdd2(){
        Coord2D first = new Coord2D(-8,6);
        Coord2D second = new Coord2D(-5,-10);
        Coord2D methodResult = Coord2D.addCoord2D(first,second);
        Coord2D expectedResult = new Coord2D(-13,-4);
        assertTrue(methodResult.equals(expectedResult));
    }
}