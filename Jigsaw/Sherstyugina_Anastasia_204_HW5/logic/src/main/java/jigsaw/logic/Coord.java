package jigsaw.logic;

import javax.swing.*;

/**
 * This class describes coordinate.
 */
public final class Coord {
    private int column;
    private int row;

    /**
     * Object constructor.
     *
     * @param column is x-coordinate
     * @param row    is y-coordinate
     */
    public Coord(int column, int row) {
        this.column = column;
        this.row = row;
    }

    /**
     * Object constructor.
     *
     * @param cell - its location describes with x and y coordinates
     */
    public Coord(JTextArea cell) {
        column = cell.getX();
        row = cell.getY();
    }

    /**
     * Getter for x-coordinate.
     *
     * @return Integer object represents column number
     */
    public int getColumn() {
        return column;
    }

    /**
     * Getter for y-coordinate.
     *
     * @return Integer object represents row number
     */
    public int getRow() {
        return row;
    }

    /**
     * Turn figure by specified angle.
     *
     * @param sin is angle sin
     * @param cos is angle cos
     */
    public void turn(int sin, int cos) {
        int newColumn = column * cos - row * sin;
        int newRow = column * sin + row * cos;
        column = newColumn;
        row = newRow;
    }

    /**
     * Shift figure relative to specified coordinate.
     *
     * @param pivot is specified coordinate.
     */
    public void shift(Coord pivot) {
        column += pivot.getColumn();
        row += pivot.getRow();
    }
}