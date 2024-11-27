package upm.app.data.modelos;

public class TieBreak implements  Game{
    private static final int MINIMUM_WIN=7;
    private static final int MINIMUM_ADVANTAGE=2;
    private int service;
    private int rest;


    @Override
    public void serviceWon() {
        if (gameWon()){
            return;
        }
        this.service++;
    }

    @Override
    public void restWon() {
        if (gameWon()){
            return;
        }
        this.rest++;
    }

    @Override
    public boolean gameWon() {
        return ((this.service>=MINIMUM_WIN && this.service-this.rest==MINIMUM_ADVANTAGE) ||
                (this.rest>=MINIMUM_WIN && this.rest-this.service>=MINIMUM_ADVANTAGE));
    }
}
