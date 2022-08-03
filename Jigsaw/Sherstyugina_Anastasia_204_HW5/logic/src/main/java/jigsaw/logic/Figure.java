package jigsaw.logic;

import java.awt.dnd.InvalidDnDOperationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This enum describes different figures of jigsaw.
 */
public enum Figure {
    J(Arrays.asList(
            new Coord(0, 0),
            new Coord(1, 0),
            new Coord(1, 1),
            new Coord(1, 2)
    )),
    L(Arrays.asList(
            new Coord(0, 2),
            new Coord(0, 1),
            new Coord(0, 0),
            new Coord(1, 0)
    )),
    V(Arrays.asList(
            new Coord(0, 0),
            new Coord(1, 0),
            new Coord(2, 0),
            new Coord(2, 1),
            new Coord(2, 2)
    )),
    I(Arrays.asList(
            new Coord(0, 0),
            new Coord(0, 1),
            new Coord(0, 2)
    )),
    U(Arrays.asList(
            new Coord(0, 0),
            new Coord(1, 0),
            new Coord(1, 1)
    )),
    T(Arrays.asList(
            new Coord(0, 2),
            new Coord(1, 2),
            new Coord(2, 2),
            new Coord(1, 1),
            new Coord(1, 0)
    )),
    Y(Arrays.asList(
            new Coord(0, 1),
            new Coord(1, 0),
            new Coord(1, 1),
            new Coord(1, 2)
    )),
    O(List.of(
            new Coord(0, 0)
    )),
    Z(Arrays.asList(
            new Coord(0, 1),
            new Coord(1, 1),
            new Coord(1, 0),
            new Coord(2, 0)
    ));

    public static final int CELL_COUNT = 9;

    private final List<Coord> coords;

    /**
     * Object constructor.
     *
     * @param coords is figure coordinates.
     */
    Figure(List<Coord> coords) {
        this.coords = coords;
    }

    /**
     * Getter for figure coordinates
     *
     * @return list of Coord objects
     */
    public List<Coord> getCoords() {
        return coords;
    }

    /**
     * Turns figure by specified angle.
     *
     * @param angle is specified angle.
     */
    public void turn(Rotation angle) {
        switch (angle) {
            case ANGLE_0:
                break;
            case ANGLE_90:
                for (Coord coord : coords) {
                    coord.turn(1, 0);
                }
                break;
            case ANGLE_180:
                for (Coord coord : coords) {
                    coord.turn(0, -1);
                }
                break;
            case ANGLE_270:
                for (Coord coord : coords) {
                    coord.turn(-1, 0);
                }
                break;
        }

        // Shifts figure to get non-negative coords.
        int minColumn = 0;
        int minRow = 0;
        for (Coord coord : coords) {
            if (coord.getColumn() < minColumn) {
                minColumn = coord.getColumn();
            }
            if (coord.getRow() < minRow) {
                minRow = coord.getRow();
            }
        }

        Coord pivot = new Coord(-minColumn, -minRow);
        for (Coord coord : coords) {
            coord.shift(pivot);
        }
    }

    /**
     * Calculate new figure coords relative to game panel.
     *
     * @param dragCell   is cell for which figure was dragged
     * @param dropCell   is game field's cell in which figure will drop
     * @param shift      is frames shift coordinates
     * @param isOccupied is boolean array where occupied cells are marked as true,
     *                   free cells as false
     * @return List of new coordinates
     * @throws InvalidDnDOperationException if figure can not be placed
     */
    public List<Coord> getCoordsRelativeToCell(Coord dragCell, Coord dropCell, Coord shift,
                                               boolean[][] isOccupied) throws InvalidDnDOperationException {
        List<Coord> relativeCoords = new ArrayList<>();
        for (Coord coord : coords) {
            int column = (dropCell.getColumn() - shift.getColumn() * (dragCell.getColumn() - coord.getRow())) / shift.getColumn();
            int row = (dropCell.getRow() - shift.getRow() * (dragCell.getRow() - coord.getColumn())) / shift.getRow();

            if (isCoordIncorrect(column) || isCoordIncorrect(row) || isOccupied[row][column]) {
                throw new InvalidDnDOperationException();
            }
            relativeCoords.add(new Coord(column, row));
        }

        return relativeCoords;
    }

    /**
     * Checks if index correct.
     *
     * @param index is coord's index
     * @return true, if it is correct, otherwise false
     */
    private boolean isCoordIncorrect(int index) {
        return index < 0 || index >= CELL_COUNT;
    }
}
