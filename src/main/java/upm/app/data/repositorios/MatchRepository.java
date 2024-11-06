package upm.app.data.repositorios;

import upm.app.data.modelos.Match;
import upm.app.data.modelos.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchRepository extends GenericRepository<Match> {

    List<Match> findByCourt(String name);

    void establishWinner(Match match, User winner);
}
