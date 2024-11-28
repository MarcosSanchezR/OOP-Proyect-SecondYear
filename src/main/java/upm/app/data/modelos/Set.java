package upm.app.data.modelos;

import java.util.ArrayList;
import java.util.List;

public class Set {
    private static final int MINIMUM_WIN=6;
    private static final int MINIMUM_ADVANTAGE = 2;
    private static final int WIN_IN_TIEBREAK_AD=1;
    private int player1;
    private int player2;
    private Game game;

    public Set(Game game) {
        this.player1 = 0;
        this.player2 = 0;
        this.game = game;
    }

    public void player1Won(){
        if (this.game.gameWon() || setWon()){
            throw new InvalidAttributeException("Ya se ha ganado el juego");
        }else{
            this.game.serviceWon();
        }
        if (this.game.gameWon()){
            this.player1++;
            this.game=createNewGame();
        }
    }

    public void player2Won(){
        if (this.game.gameWon() || setWon()){
            throw new InvalidAttributeException("Ya se ha ganado el juego");
        }else{
            this.game.restWon();
        }
        if (this.game.gameWon()){
            this.player2++;
            this.game=createNewGame();
        }
    }

    public boolean setWon() {
        if (this.player1 > MINIMUM_WIN || this.player2> MINIMUM_WIN){
            return true;
        }
        return (this.player1 == MINIMUM_WIN && this.player1 - this.player2 >= MINIMUM_ADVANTAGE) ||
                (this.player2 == MINIMUM_WIN && this.player2 - this.player1 >= MINIMUM_ADVANTAGE);
    }

    private Game createNewGame(){
        if (this.player1==MINIMUM_WIN && this.player2==MINIMUM_WIN){
            return new TieBreak();
        }else{
         return new EstandarGame();
        }
    }

    public int getPlayer1() {
        return player1;
    }

    public void setPlayer1(int player1) {
        this.player1 = player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public void setPlayer2(int player2) {
        this.player2 = player2;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Set{" +
                "player1=" + player1 +
                ", player2=" + player2 +
                ", game=" + game +
                '}';
    }
}
