package upm.app.data.modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Match extends Entity {

    private static final int WIN = 2;
    private static final int DURATION = 3;
    private static final int MINIMUM_START = 9;
    private final List<Set> sets;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;
    private User user1;
    private User user2;
    private int service;
    private TennisCourt court;
    private MatchStatus status;

    public Match(LocalDateTime dateTimeStart, User user1, User user2, TennisCourt court) {
        this.setDateTimeStart(dateTimeStart);
        this.user1 = user1;
        this.user2 = user2;
        this.court = court;
        this.dateTimeEnd = dateTimeStart.plusHours(DURATION);
        this.status = MatchStatus.NOT_STARTED;
        this.sets = new ArrayList<>();
        this.service = 0;
    }

    public void altService() {
        Random r = new Random();
        if (this.service == 0) {
            this.service = r.nextInt(2) + 1;
        } else {
            this.service = (this.service == 1) ? 2 : 1;
        }
    }

    public void punctuate(int winner) {
        if (this.service == 0) {
            throw new InvalidAttributeException("No se ha establecido quien tiene el servicio todavia");
        }
        if (sets.isEmpty()) {
            Set firstSet = new Set();
            sets.add(firstSet);
        }
        if (getLastSet().setWon() && !matchWon()) {
            sets.add(new Set());
        }
        if (matchWon()) {
            throw new InvalidAttributeException("Ya se ha ganado el partido");
        }
        if (getLastSet().getGame().getService() == 0 && getLastSet().getGame().getRest() == 0) {
            altService();
        }

        addPoints(winner);
        getLastSet().setWon();

    }

    private void addPoints(int winner) {
        if (winner == 1) {
            if (service == 1) {
                getLastSet().player1Won();
            } else {
                getLastSet().player2Won();
            }
        } else if (winner == 2) {
            if (service == 1) {
                getLastSet().player2Won();
            } else {
                getLastSet().player1Won();
            }
        }
    }

    public boolean matchWon() {
        int[] setsWon = calculateSetsWon();
        return setsWon[0] >= WIN || setsWon[1] >= WIN;
    }

    private int[] calculateSetsWon() {
        int player1Sets = 0;
        int player2Sets = 0;

        for (Set set : sets) {
            if (set.getWinner() == 1) {
                player1Sets++;
            } else if (set.getWinner() == 2) {
                player2Sets++;
            }
        }

        return new int[]{player1Sets, player2Sets};
    }

    public String scoreboard() {
        StringBuilder scoreboard = new StringBuilder();

        int player1Sets = 0;
        int player2Sets = 0;

        for (Set set : sets) {
            player1Sets = set.getPlayer1();
            player2Sets = set.getPlayer2();
        }

        String player1GameScore = String.valueOf(getLastSet().getGame().getService());
        String player2GameScore = String.valueOf(getLastSet().getGame().getRest());

        scoreboard.append(user1.getName()).append(": ").append(player1Sets).append(" (").append(player1GameScore).append(")\n");

        scoreboard.append(user2.getName()).append(": ").append(player2Sets).append(" (").append(player2GameScore).append(")");

        return scoreboard.toString();
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        if (dateTimeStart.isBefore(LocalDateTime.now())) {
            throw new InvalidAttributeException("El partido no puede empezar en una fecha pasada: " + dateTimeStart);
        }
        if (dateTimeStart.getHour() < MINIMUM_START) {
            throw new InvalidAttributeException("El partido no puede empezar antes de las " + MINIMUM_START);
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
        int[] setsWon = calculateSetsWon();
        if (setsWon[0] >= WIN) {
            return user1;
        } else if (setsWon[1] >= WIN) {
            return user2;
        }
        return null;
    }

    public int getService() {
        return service;
    }

    public void setService(int who) {
        service = who;
    }

    public Set getLastSet() {
        if (sets.isEmpty()) {
            throw new InvalidAttributeException("No hay sets en el partido.");
        }
        return sets.get(sets.size() - 1);
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
        return Objects.equals(dateTimeStart, match.dateTimeStart) && Objects.equals(dateTimeEnd, match.dateTimeEnd) && Objects.equals(user1, match.user1) && Objects.equals(user2, match.user2) && Objects.equals(court, match.court);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateTimeStart, dateTimeEnd, user1, user2, court);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "Match{" +
                "dateTimeStart=" + dateTimeStart.format(formatter) +
                ", user1=" + user1 +
                ", user2=" + user2 +
                ", court=" + court +
                ", ganador=" + getGanador() +
                '}' + "\n";
    }

    public enum MatchStatus {
        IN_PROGRESS,
        FINISHED,
        NOT_STARTED
    }
}
