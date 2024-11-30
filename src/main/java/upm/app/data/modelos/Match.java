package upm.app.data.modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Match extends Entity {

    public enum MatchStatus {
        IN_PROGRESS,
        FINISHED,
        NOT_STARTED
    }

    private final Random r = new Random();

    private static final int WIN=2;
    private static final int DURATION = 2;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;
    private User user1;
    private User user2;
    private int service;
    private TennisCourt court;
    private User ganador;
    private MatchStatus status;
    private final List<Set> sets;
    private Set currentSet;


    public Match(LocalDateTime dateTimeStart, User user1, User user2, TennisCourt court) {
        this.setDateTimeStart(dateTimeStart);
        this.user1 = user1;
        this.user2 = user2;
        this.court = court;
        this.dateTimeEnd = dateTimeStart.plusHours(DURATION);
        this.status = MatchStatus.NOT_STARTED;
        this.sets = new ArrayList<>();
        this.currentSet= new Set();
        sets.add(currentSet);
        this.service=0;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        if (dateTimeStart.isBefore(LocalDateTime.now())) {
            throw new InvalidAttributeException("El partido no puede empezar en una fecha pasada: " + dateTimeStart);
        }
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeStart.plusHours(DURATION);
    }

    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public TennisCourt getCourt() {
        return court;
    }

    public void setCourt(TennisCourt court) {
        this.court = court;
    }

    public User getGanador() {
        return ganador;
    }

    public void setGanador(User ganador) {
        this.ganador = ganador;
    }

    public int getService() {
        return service;
    }

    public void altService() {
        if (this.service==0){
            this.service =r.nextInt(2)+1;
        } else {
            this.service = (this.service == 1) ? 2 : 1;
        }
    }

    public void setService(int who){
        service=who;
    }

    public void punctuate(int winner){
        if (this.service==0){
            throw new InvalidAttributeException("No se ha establecido quien tiene el servicio todavia");
        }
        if (currentSet.setWon()){
            throw new InvalidAttributeException("Ya se ha ganado el set");
        }
        if (matchWon()){
            throw new InvalidAttributeException("Ya se ha ganado el partido");
        }
        if (currentSet.getGame().getService()==0 &&currentSet.getGame().getRest()==0){
            altService();
        }
        if (winner == 1) {
            if (service == 1) {
                currentSet.player1Won();
            } else {
                currentSet.player2Won();
            }
        } else if (winner == 2) {
            if (service == 1) {
                currentSet.player2Won();
            } else {
                currentSet.player1Won();
            }
        }
        if (currentSet.setWon() && !matchWon()) {
            this.currentSet = new Set();
            sets.add(currentSet);
        }
    }

    public boolean matchWon(){
        int player1Sets = 0;
        int player2Sets = 0;

        for (Set set: sets){
            if (set.getWinner()==1){
                player1Sets++;
            }else if (set.getWinner()==2){
                player2Sets++;
            }
        }
        return player1Sets>=WIN || player2Sets>=WIN;
    }

    public String scoreboard(){
        StringBuilder scoreboard = new StringBuilder();

        int player1Sets = 0;
        int player2Sets = 0;

        for (Set set : sets) {
            player1Sets=set.getPlayer1();
            player2Sets=set.getPlayer2();
        }

        String player1GameScore = String.valueOf(currentSet.getGame().getService());
        String player2GameScore = String.valueOf(currentSet.getGame().getRest());

        scoreboard.append(user1.getName()).append(": ") .append(player1Sets).append(" (").append(player1GameScore).append(")\n");

        scoreboard.append(user2.getName()).append(": ").append(player2Sets).append(" (").append(player2GameScore).append(")");

        return scoreboard.toString();
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public List<Set> getSets() {
        return sets;
    }

     public void addSet(Set set) {
        this.sets.add(set);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Match match = (Match) o;
        return Objects.equals(dateTimeStart, match.dateTimeStart) && Objects.equals(dateTimeEnd, match.dateTimeEnd) && Objects.equals(user1, match.user1) && Objects.equals(user2, match.user2) && Objects.equals(court, match.court) && Objects.equals(ganador, match.ganador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateTimeStart, dateTimeEnd, user1, user2, court, ganador);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "Match{" +
                "dateTimeStart=" + dateTimeStart.format(formatter) +
                ", user1=" + user1 +
                ", user2=" + user2 +
                ", court=" + court +
                ", ganador=" + ganador +
                '}' + "\n";
    }
}
