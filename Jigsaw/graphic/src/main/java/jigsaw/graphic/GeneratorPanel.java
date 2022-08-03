package jigsaw.graphic;

import jigsaw.logic.Coord;
import jigsaw.logic.Figure;
import jigsaw.logic.Rotation;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.util.Random;

import static jigsaw.graphic.MyFrame.*;

/**
 * Describes custom panel for generator field.
 */
public class GeneratorPanel extends JPanel {
    private static final int SIZE = 3;

    private final JTextArea[][] grid = new JTextArea[SIZE][SIZE];
    private final Random random = new Random();
    private final MyFrame frame;

    private Figure figure;

    /**
     * Object constructor.
     *
     * @param frame is link to general frame.
     */
    public GeneratorPanel(MyFrame frame) {
        this.frame = frame;

        initialize();
    }

    /**
     * Initialize generator panel.
     */
    private void initialize() {
        setLayout(new GridLayout(SIZE, SIZE, 1, 1));
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                JTextArea cell = new JTextArea(CELL_SIZE, CELL_SIZE);
                cell.setEditable(false);
                cell.setBackground(Color.BLACK);
                DragSource dragSource = new DragSource();
                dragSource.createDefaultDragGestureRecognizer(cell,
                        DnDConstants.ACTION_COPY, frame);
                add(cell);
                grid[i][j] = cell;
            }
        }

        generate();
    }

    /**
     * Generate new figure.
     */
    public void generate() {
        clear();

        // Get random figure and random rotation.
        figure = Figure.values()[random.nextInt(9)];
        figure.turn(Rotation.values()[random.nextInt(4)]);

        // Get random color and paint figure in the generator field.
        Color color = generateColor();
        for (Coord coord : figure.getCoords()) {
            grid[coord.getColumn()][coord.getRow()].setBackground(color);
        }
    }

    /**
     * Colors all cells black.
     */
    private void clear() {
        for (JTextArea[] row : grid) {
            for (JTextArea cell : row) {
                cell.setBackground(Color.BLACK);
            }
        }
    }

    /**
     * Generate random light color (rgb from 0.3f to 1f).
     *
     * @return generated Color object.
     */
    private Color generateColor() {
        return new Color(
                2f * random.nextFloat() / 3f + 0.3f,
                2f * random.nextFloat() / 3f + 0.3f,
                2f * random.nextFloat() / 3f + 0.3f);
    }

    /**
     * Getter for figure.
     *
     * @return current Figure object.
     */
    public Figure getFigure() {
        return figure;
    }
}
