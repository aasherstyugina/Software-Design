package jigsaw.graphic;

import jigsaw.logic.Coord;
import jigsaw.logic.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.IOException;
import java.util.List;

import static jigsaw.graphic.MyFrame.*;

/**
 * This class describes custom panel for game field.
 */
public class GamePanel extends JPanel {
    private final MyFrame frame;
    private final JTextArea[][] grid = new JTextArea[CELL_COUNT][CELL_COUNT];
    private final boolean[][] isOccupied = new boolean[CELL_COUNT][CELL_COUNT];

    private int countOfMoves;

    /**
     * Object constructor.
     *
     * @param frame is link to general frame
     */
    public GamePanel(MyFrame frame) {
        this.frame = frame;
        countOfMoves = 0;

        initialize();
    }

    /**
     * Initialize panel.
     */
    private void initialize() {
        setLayout(new GridLayout(CELL_COUNT, CELL_COUNT, 1, 1));
        for (int i = 0; i < CELL_COUNT; ++i) {
            for (int j = 0; j < CELL_COUNT; ++j) {
                JTextArea cell = new JTextArea(CELL_SIZE, CELL_SIZE);
                cell.setEditable(false);
                cell.setBackground(Color.BLACK);
                new MyDropTargetAdapter(cell);
                add(cell);
                grid[i][j] = cell;
                isOccupied[i][j] = false;
            }
        }
    }

    /**
     * Getter for countOfMoves field.
     *
     * @return Integer object represents count of moves which user has done.
     */
    public int getCountOfMoves() {
        return countOfMoves;
    }

    /**
     * Describes custom DropTargetAdapter class.
     */
    class MyDropTargetAdapter extends DropTargetAdapter {
        private final JTextArea cell;

        /**
         * Object constructor.
         *
         * @param cell is drop location
         */
        public MyDropTargetAdapter(JTextArea cell) {
            this.cell = cell;
            new DropTarget(cell, DnDConstants.ACTION_COPY,
                    this, true, null);
        }

        /**
         * Overrided method from interface DropTargetListener,
         * which calls when drag operation has terminated with a drop.
         *
         * @param event describes drop event
         */
        @Override
        public void drop(DropTargetDropEvent event) {
            // Gets all the transferable data.
            Transferable transferable = event.getTransferable();
            try {
                Color color = (Color) transferable.getTransferData(MyTransferable.colorDataFlavor);
                Figure figure = (Figure) transferable.getTransferData(MyTransferable.figureDataFlavor);
                Coord start = (Coord) transferable.getTransferData(MyTransferable.coordDataFlavor);

                // Black color of dragged cell means it's not a figure,
                // but a free cells of generator panel.
                if (color.equals(Color.BLACK)) {
                    event.rejectDrop();
                }

                try {
                    // List to save new coords of transferable figure relative to the game panel.
                    List<Coord> figureCoords = figure.getCoordsRelativeToCell(
                            start, new Coord(cell), new Coord(SHIFT_X, SHIFT_Y), isOccupied);
                    for (Coord coord : figureCoords) {
                        grid[coord.getRow()][coord.getColumn()].setBackground(color);
                        isOccupied[coord.getRow()][coord.getColumn()] = true;
                    }

                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    event.dropComplete(true);

                    // After successful DnD generate new figure and increase moves counter.
                    frame.getGeneratorPanel().generate();
                    ++countOfMoves;
                } catch (InvalidDnDOperationException e) {
                    event.dropComplete(false);
                    event.rejectDrop();
                }
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
