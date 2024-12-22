package upm.app.data.modelos;

public abstract class Game {
    protected int service;
    protected int rest;

    protected Game() {
        this.service = 0;
        this.rest = 0;
    }

    public abstract void serviceWon();

    public abstract void restWon();

    public abstract boolean gameWon();

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

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "service=" + service +
                ", rest=" + rest +
                '}';
    }
}
