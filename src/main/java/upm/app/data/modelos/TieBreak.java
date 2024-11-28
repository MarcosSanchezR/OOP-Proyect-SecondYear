package upm.app.data.modelos;

public class TieBreak implements  Game{
    private static final int MINIMUM_WIN=7;
    private static final int MINIMUM_ADVANTAGE=2;
    private int service;
    private int rest;

    public TieBreak(){
        this.service=0;
        this.rest=0;
    }


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
        return ((this.service>=MINIMUM_WIN && this.service-this.rest>=MINIMUM_ADVANTAGE) ||
                (this.rest>=MINIMUM_WIN && this.rest-this.service>=MINIMUM_ADVANTAGE));
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }
}
