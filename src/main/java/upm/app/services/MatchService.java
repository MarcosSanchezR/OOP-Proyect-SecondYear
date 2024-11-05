package upm.app.services;

import upm.app.data.modelos.Match;
import upm.app.data.repositorios.MatchRepository;
import upm.app.services.exceptions.DuplicateException;

import java.time.LocalDateTime;
import java.util.List;

public class MatchService {
    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Match create(Match match){
        if (this.matchRepository.findByDateTime(match.getDateTime()).isPresent() && this.matchRepository.findByCourt(match.getCourt()).isPresent()){
            throw new DuplicateException("Ya hay un partido en esta pista en este momento. "+match.getDateTime()+ ", "+ match.getCourt());
        }
        return this.matchRepository.create(match);
    }

    public void establishWinner();

    public List<Match> listAll(){ return this.matchRepository.findAll(); }


}
