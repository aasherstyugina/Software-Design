package jigsaw.graphic;

import jigsaw.logic.Coord;
import jigsaw.logic.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

/**
 * This class describes custom frame for game jigsaw.
 */
public class MyFrame extends JFrame implements DragGestureListener {
    public static final int CELL_COUNT = 9;
    public static final int CELL_SIZE = 4;
    public static final int SHIFT_X = 41;
    public static final int SHIFT_Y = 65;

    private final GeneratorPanel generatorPanel;
    private final GamePanel gamePanel;
    private final MenuPanel menuPanel;

    /**
     * Object constructor.
     */
    public MyFrame() {
        gamePanel = new GamePanel(this);
        generatorPanel = new GeneratorPanel(this);
        menuPanel = new MenuPanel(this);

        initialize();
    }

    /**
     * Frame initialization.
     */
    private void initialize() {
        setLayout(new FlowLayout());
        add(generatorPanel);
        add(gamePanel);
        add(menuPanel);

        setTitle("Tetris");
        setSize(800, 760);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        pack();
    }

    /**
     * Getter for generator panel.
     *
     * @return frame's GeneratorPanel object
     */
    public GeneratorPanel getGeneratorPanel() {
        return generatorPanel;
    }

    /**
     * Getter for game panel.
     *
     * @return frame's GamePanel object.
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * Owerrided method from interface DragGestureListener, which detects drag gesture in frame.
     *
     * @param dragGestureEvent describes detected drag gesture
     */
    @Override
    public void dragGestureRecognized(DragGestureEvent dragGestureEvent) {
        Cursor cursor = Cursor.getDefaultCursor();

        // Get cell which was dragged and remember its color.
        JTextArea cell = (JTextArea) dragGestureEvent.getComponent();
        Color color = cell.getBackground();

        if (dragGestureEvent.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }

        // Start drag operation and remember useful info in MyTransferable object.
        dragGestureEvent.startDrag(cursor, new MyTransferable(color,
                generatorPanel.getFigure(),
                new Coord(cell.getX() / SHIFT_X, cell.getY() / SHIFT_Y)));
    }
}

/**
 * Describes custom realization of interface Transferable.
 */
class MyTransferable implements Transferable {
    // Saves info about figure, its color and coords of the cell, which was dragged.
    protected static final DataFlavor figureDataFlavor =
            new DataFlavor(Figure.class, "Figure");
    protected static final DataFlavor colorDataFlavor =
            new DataFlavor(Color.class, "Color");
    protected static final DataFlavor coordDataFlavor =
            new DataFlavor(Coord.class, "Coord");
    protected static final DataFlavor[] transferDataFlavors =
            {colorDataFlavor, figureDataFlavor, coordDataFlavor};

    private final Color color;
    private final Figure figure;
    private final Coord coord;

    /**
     * Object constructor.
     *
     * @param color  is figure's color
     * @param figure is generated Figure object
     * @param coord  is coords of cell which was dragged from generator panel
     */
    public MyTransferable(Color color, Figure figure, Coord coord) {
        this.color = color;
        this.figure = figure;
        this.coord = coord;
    }

    /**
     * Overrided method from interface Transferable, which gets info about transferable data.
     *
     * @return object with transferable data
     */
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return transferDataFlavors;
    }

    /**
     * Overrided method from interface Transferable, which checks if specified data flavor is supported for this object.
     *
     * @param flavor is specified data
     * @return true, if flavor matches at least with one of MyTransferable data flavors, otherwise false
     */
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(colorDataFlavor) || flavor.equals(figureDataFlavor) ||
                flavor.equals(coordDataFlavor);
    }

    /**
     * Gets transferable data.
     *
     * @param flavor is specified data
     * @return object which represents transferable data
     * @throws UnsupportedFlavorException if flavor is not supported
     */
    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(colorDataFlavor)) {
            return color;
        }
        if (flavor.equals(figureDataFlavor)) {
            return figure;
        }
        if (flavor.equals(coordDataFlavor)) {
            return coord;
        }

        throw new UnsupportedFlavorException(flavor);
    }
}