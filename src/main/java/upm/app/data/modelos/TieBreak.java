package upm.app.data.modelos;

public class TieBreak extends Game {

    private static final int MINIMUM_WIN = 7;
    private static final int MINIMUM_ADVANTAGE = 2;

    public TieBreak() {
        super();
    }

    @Override
    public void serviceWon() {
        if (!gameWon()) {
            this.service++;
        }
    }

    @Override
    public void restWon() {
        if (!gameWon()) {
            this.rest++;
        }
    }

    @Override
    public boolean gameWon() {
        return ((this.service >= MINIMUM_WIN && this.service - this.rest >= MINIMUM_ADVANTAGE) ||
                (this.rest >= MINIMUM_WIN && this.rest - this.service >= MINIMUM_ADVANTAGE));
    }
}
