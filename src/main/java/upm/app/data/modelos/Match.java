package upm.app.data.modelos;

import java.time.LocalDateTime;

public class Match extends Entity {
    private static final int DURATION = 2;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;
    private User user1;
    private User user2;
    private TennisCourt court;
    private User ganador;

    public Match(LocalDateTime dateTimeStart, User user1, User user2, TennisCourt court) {
        this.dateTimeStart = dateTimeStart;
        this.user1 = user1;
        this.user2 = user2;
        this.court = court;
        this.dateTimeEnd = dateTimeStart.plusHours(DURATION);
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
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

    @Override
    public String toString() {
        return "Match{" +
                "dateTimeStart=" + dateTimeStart +
                ", user1=" + user1 +
                ", user2=" + user2 +
                ", court=" + court +
                ", ganador=" + ganador +
                '}';
    }
}
