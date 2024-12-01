package upm.app.data.modelos;

public interface Game {
    void serviceWon();

    void restWon();

    boolean gameWon();

    int getService();

    void setService(int service);

    int getRest();

    void setRest(int rest);
}
