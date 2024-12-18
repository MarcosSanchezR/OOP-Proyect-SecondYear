package upm.app.data.repositorios.map;

import upm.app.data.modelos.Match;
import upm.app.data.modelos.User;
import upm.app.data.repositorios.MatchRepository;

import java.util.ArrayList;
import java.util.List;

public class MatchRepositoryMap extends RepositoryMap<Match> implements MatchRepository {

    @Override
    public List<Match> findByCourt(String name) {
        List<Match> matchesInCourt = new ArrayList<>();
        for (Match match : this.findAll()) {
            if (name.equalsIgnoreCase(match.getCourt().getName())) {
                matchesInCourt.add(match);
            }
        }
        return matchesInCourt;
    }

    @Override
    public void establishWinner(Match match, User winner) {
        match.setGanador(winner);
    }

    @Override
    public void startMatch(Match match) {
        match.altService();
        match.setStatus(Match.MatchStatus.IN_PROGRESS);
    }

    @Override
    public void scoreMatch(Match match, int winner) {
        match.punctuate(winner);
    }

    @Override
    public Match readMatch(Match match) {
        return match;
    }

    @Override
    public void moveMatchInHoliday() {

    }


}
