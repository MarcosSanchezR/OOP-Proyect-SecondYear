package upm.app.data.repositorios.map;

import upm.app.data.modelos.Match;
import upm.app.data.modelos.User;
import upm.app.data.repositorios.MatchRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class MatchRepositoryMap extends RepositoryMap<Match> implements MatchRepository {
    @Override
    public Optional<Match> findByDateTime(LocalDateTime dateTime) {
        return Optional.empty();
    }

    @Override
    public void establishWinner(LocalDateTime dateTime, String dni) {

    }
}
