package jigsaw.logic;

import org.junit.jupiter.api.Test;

import java.awt.dnd.InvalidDnDOperationException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FigureTest {
    /*
    Will test if figure can be placed depending on game field occupation
    and check if coordinates (relative to game field) counted correctly.
     */
    private Figure figureL = Figure.L;
    private Figure figureT = Figure.T;
    private Figure figureZ = Figure.Z;
    private Figure figureV = Figure.V;

    /**
     * Check coordinates after rotation.
     */
    @Test
    public void checkFigureTurn() {
        List<Coord> myCoords;

        myCoords = Arrays.asList(
                new Coord(0, 2),
                new Coord(0, 1),
                new Coord(0, 0),
                new Coord(1, 0)
        );
        figureL.turn(Rotation.ANGLE_0);
        assertTrue(isFiguresEqual(myCoords, figureL.getCoords()));

        myCoords = Arrays.asList(
                new Coord(0, 0),
                new Coord(0, 1),
                new Coord(0, 2),
                new Coord(1, 1),
                new Coord(2, 1)
        );
        figureT.turn(Rotation.ANGLE_90);
        assertTrue(isFiguresEqual(myCoords, figureT.getCoords()));

        myCoords = Arrays.asList(
                new Coord(2, 0),
                new Coord(1, 0),
                new Coord(1, 1),
                new Coord(0, 1)
        );
        figureZ.turn(Rotation.ANGLE_180);
        assertTrue(isFiguresEqual(myCoords, figureZ.getCoords()));

        myCoords = Arrays.asList(
                new Coord(0, 2),
                new Coord(0, 1),
                new Coord(0, 0),
                new Coord(1, 0),
                new Coord(2, 0)
        );
        figureV.turn(Rotation.ANGLE_270);
        assertTrue(isFiguresEqual(myCoords, figureV.getCoords()));
    }

    private final int SIZE = 9;
    public static final int SHIFT_X = 41;
    public static final int SHIFT_Y = 65;
    private boolean[][] isOccupied = new boolean[SIZE][SIZE];

    /**
     * Check if it's possible to set figure. To demonstrate current game field state
     * using only logic module it prints in the console.
     */
    @Test
    public void canFigureBePlaced() {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                isOccupied[i][j] = false;
            }
        }

        Coord dragCell_L = new Coord(0, 0);
        Coord dropCell_L = new Coord(2 * SHIFT_X, 2 * SHIFT_Y);
        Coord shift_L = new Coord(SHIFT_X, SHIFT_Y);
        assertDoesNotThrow(() -> {
            List<Coord> newCoords = figureL.getCoordsRelativeToCell(dragCell_L, dropCell_L, shift_L, isOccupied);
            for (Coord coord : newCoords) {
                isOccupied[coord.getRow()][coord.getColumn()] = true;
            }
        });
        System.out.println("Set L:");
        printFieldConsole();

        Coord dragCell_Z = new Coord(0, 1);
        Coord dropCell_Z = new Coord(5 * SHIFT_X, 5 * SHIFT_Y);
        Coord shift_Z = new Coord(SHIFT_X, SHIFT_Y);
        assertDoesNotThrow(() -> {
            List<Coord> newCoords = figureZ.getCoordsRelativeToCell(dragCell_Z, dropCell_Z, shift_Z, isOccupied);
            for (Coord coord : newCoords) {
                isOccupied[coord.getRow()][coord.getColumn()] = true;
            }
        });
        System.out.println("Set Z:");
        printFieldConsole();

        Coord dragCell_T = new Coord(1, 1);
        Coord dropCell_T = new Coord(2 * SHIFT_X, 3 * SHIFT_Y);
        Coord shift_T = new Coord(SHIFT_X, SHIFT_Y);
        assertThrows(InvalidDnDOperationException.class, () -> {
            List<Coord> newCoords = figureT.getCoordsRelativeToCell(dragCell_T, dropCell_T, shift_T, isOccupied);
            for (Coord coord : newCoords) {
                isOccupied[coord.getRow()][coord.getColumn()] = true;
            }
        });
        System.out.println("Set T:");
        printFieldConsole();

        Coord dragCell_V = new Coord(0, 0);
        Coord dropCell_V = new Coord(8 * SHIFT_X, 7 * SHIFT_Y);
        Coord shift_V = new Coord(SHIFT_X, SHIFT_Y);
        assertThrows(InvalidDnDOperationException.class, () -> {
            List<Coord> newCoords = figureV.getCoordsRelativeToCell(dragCell_V, dropCell_V, shift_V, isOccupied);
            for (Coord coord : newCoords) {
                isOccupied[coord.getRow()][coord.getColumn()] = true;
            }
        });
        System.out.println("Set V:");
        printFieldConsole();
    }

    private boolean isFiguresEqual(List<Coord> coords1, List<Coord> coords2) {
        for (int i = 0; i < coords1.size(); ++i) {
            if (!isCoordsEquals(coords1.get(i), coords2.get(i))) {
                return false;
            }
        }

        return true;
    }

    private boolean isCoordsEquals(Coord coord1, Coord coord2) {
        return coord1.getColumn() == coord2.getColumn() && coord1.getRow() == coord2.getRow();
    }

    private void printFieldConsole() {
        for (int column = 0; column < SIZE; ++column) {
            for (int row = 0; row < SIZE; ++row) {
                if (isOccupied[row][column]) {
                    System.out.print("\t*");
                } else {
                    System.out.print("\t-");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}