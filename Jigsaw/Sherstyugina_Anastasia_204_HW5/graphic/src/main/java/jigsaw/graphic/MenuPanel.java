package jigsaw.graphic;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This class describes custom menu panel.
 */
public class MenuPanel extends JPanel {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");
    private final MyFrame frame;

    private LocalTime now;
    private JLabel timeLabel;
    private Timer timer;

    /**
     * Object constructor.
     *
     * @param frame is link to general frame.
     */
    public MenuPanel(MyFrame frame) {
        this.frame = frame;

        initialize();
    }

    /**
     * Initialize menu panel.
     */
    private void initialize() {
        setLayout(new GridLayout(2, 1));

        JButton button = new JButton("End the game");
        button.addActionListener(e -> new EndGameDialog(frame).setVisible(true));
        add(button);

        now = LocalTime.now();
        timeLabel = new JLabel("00:00");
        // Each second timeLabel changes.
        timer = new Timer(1000,
                e -> timeLabel.setText(
                        formatter.format(LocalTime.now().minusSeconds(now.getSecond()).minusMinutes(now.getMinute()))));
        timer.start();
        add(timeLabel);
    }

    /**
     * Describes custom JDialog class.
     */
    class EndGameDialog extends JDialog {
        /**
         * Object constructor.
         *
         * @param frame is link to general frame.
         */
        public EndGameDialog(Frame frame) {
            super(frame);

            initialize();
        }

        /**
         * Initialize dialog.
         */
        private void initialize() {
            timer.stop();

            JLabel text = new JLabel("Game time: " + timeLabel.getText() +
                    ", game moves: " + frame.getGamePanel().getCountOfMoves() + "     ");

            // New game button disposes current game frame and creates new.
            JButton newGame = new JButton("New game");
            newGame.addActionListener(e -> {
                frame.dispose();
                new MyFrame();
            });

            // Exit game button finishes app work.
            JButton exit = new JButton("Exit");
            exit.addActionListener(e -> System.exit(0));

            setLayout(new FlowLayout(FlowLayout.CENTER));
            setMinimumSize(new Dimension(500, 80));
            add(text);
            add(newGame);
            add(exit);

            setTitle("End game");
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            setLocationRelativeTo(getParent());
            pack();
        }
    }
}
