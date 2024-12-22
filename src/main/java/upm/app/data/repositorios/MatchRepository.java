package upm.app.data.repositorios;

import upm.app.data.modelos.Match;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends GenericRepository<Match> {

    List<Match> findByCourt(String name);

    void startMatch(Match match);

    void scoreMatch(Match match, int winner);

    Match readMatch(Match match);

    void moveMatchInHoliday(Match match, LocalDateTime newDate);

}
