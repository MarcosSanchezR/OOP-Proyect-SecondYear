package upm.app.services;

import upm.app.data.modelos.Match;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.modelos.User;
import upm.app.data.repositorios.CourtRepository;
import upm.app.data.repositorios.MatchRepository;
import upm.app.data.repositorios.UserRepository;
import upm.app.services.exceptions.DuplicateException;
import upm.app.services.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class MatchService {
    private static final int DURATION = 2;
    private final MatchRepository matchRepository;
    private final CourtRepository courtRepository;
    private final UserRepository userRepository;

    public MatchService(MatchRepository matchRepository, CourtRepository courtRepository, UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.courtRepository = courtRepository;
        this.userRepository = userRepository;
    }

    public Match create(LocalDateTime start, String dni1, String dni2, String courtName) {
        if (this.courtRepository.findByName(courtName).isEmpty()) {
            throw new NotFoundException("No existe esa pista: " + courtName);
        }
        if (this.userRepository.findByDni(dni1).isEmpty()) {
            throw new NotFoundException("No existe ese usuario: " + dni1);
        }
        if (this.userRepository.findByDni(dni2).isEmpty()) {
            throw new NotFoundException("No existe ese usuario: " + dni2);
        }
        LocalDateTime end = start.plusHours(DURATION);
        List<Match> sameCourt = this.matchRepository.findByCourt(courtName);
        for (Match existingMatch : sameCourt) {
            LocalDateTime existingStart = existingMatch.getDateTimeStart();
            LocalDateTime existingEnd = existingMatch.getDateTimeEnd();

            if (start.isBefore(existingEnd) && start.plusHours(2).isAfter(existingStart)) {
                throw new DuplicateException("Ya hay un partido en esta pista en este momento. " + start + " - " + end + ", " + courtName);
            }
        }
        TennisCourt court = this.courtRepository.findByName(courtName).get();
        User user1 = this.userRepository.findByDni(dni1).get();
        User user2 = this.userRepository.findByDni(dni2).get();
        Match match = new Match(start, user1, user2, court);
        return this.matchRepository.create(match);
    }

    public void establishWinner(LocalDateTime dateTime, String name, String dni) {
        if (this.courtRepository.findByName(name).isEmpty()) {
            throw new NotFoundException("No existe esa pista: " + name);
        }
        List<Match> sameCourt = this.matchRepository.findByCourt(name);
        Match match = null;
        for (Match exitingMatch : sameCourt) {
            if (dateTime.equals(exitingMatch.getDateTimeStart())) {
                match = exitingMatch;
            }
        }
        if (match == null) {
            throw new NotFoundException("No se ha encontrado ningun partido con esa fecha en esa pista: " + dateTime + ", " + name);
        }
        if (!dni.equals(match.getUser1().getDni()) && !dni.equals(match.getUser2().getDni())) {
            throw new NotFoundException("No se ha encontrado ningun partido con esa fecha en esa pista con ese jugador: " + dateTime + ", " + name + ", " + dni);
        }
        if (dni.equals(match.getUser1().getDni())) {
            this.matchRepository.establishWinner(match, match.getUser1());
        } else {
            this.matchRepository.establishWinner(match, match.getUser2());
        }

    }

    public List<Match> listAll() {
        return this.matchRepository.findAll();
    }


}
