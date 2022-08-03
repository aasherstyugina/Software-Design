package edu.hse.cs;

/**
 * This class describes sharper, which represents a thread. It is a Player's subclass.
 *
 * @author Sherstyugina Anastasia, BSE204.
 */
public final class Sharper extends Player {
    /**
     * Constructor.
     *
     * @param name is a sharpers name.
     */
    public Sharper(String name) {
        super(name);
    }

    /**
     * Overriden method from Player, it is how sharper behaves before interruption.
     */
    @Override
    public void run() {
        while (true) {
            // Steal or draw a card with a probability of 0.4 and 0.6 respectively.
            double choose = Math.random();
            if (choose < 0.4) {
                if (steal()) {
                    break;
                }
            } else {
                if (takeCard()) {
                    break;
                }
            }
        }
    }

    /**
     * Represents the robbery.
     *
     * @return true, if thread was interrupted, otherwise false.
     */
    private boolean steal() {
        int victimIndex = (int) (Math.random() * Game.numOfFair);
        // Increase sharper's points by how much he stole from his victim and
        // reduce the victim's balance accordingly. Then rest.
        increasePoints(Game.players.get(victimIndex).losePoints((int) (Math.random() * 9)));
        try {
            Thread.sleep(180 + (int) (Math.random() * 120));
            return false;
        } catch (InterruptedException exception) {
            return true;
        }
    }
}
