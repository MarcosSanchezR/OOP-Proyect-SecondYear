package upm.app.data.repositorios;

import upm.app.data.modelos.Match;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.modelos.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MatchRepository extends GenericRepository<Match> {
    Optional<Match> findByDateTime(LocalDateTime dateTime);
    Optional<Match> findByCourt(TennisCourt court);
}
