package upm.app.services;

import upm.app.data.modelos.Match;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.modelos.User;
import upm.app.data.repositorios.CourtRepository;
import upm.app.data.repositorios.MatchRepository;
import upm.app.data.repositorios.UserRepository;
import upm.app.services.exceptions.DuplicateException;
import upm.app.services.exceptions.InvalidUse;
import upm.app.services.exceptions.NotFoundException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public class MatchService {
    private static final int DURATION = 3;
    private final MatchRepository matchRepository;
    private final CourtRepository courtRepository;
    private final UserRepository userRepository;

    public MatchService(MatchRepository matchRepository, CourtRepository courtRepository, UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.courtRepository = courtRepository;
        this.userRepository = userRepository;
    }

    private static Match getMatch(LocalDateTime start, String court, List<Match> sameCourt) {
        Match match = null;
        for (Match exitingMatch : sameCourt) {
            if (start.equals(exitingMatch.getDateTimeStart())) {
                match = exitingMatch;
            }
        }
        if (match == null) {
            throw new NotFoundException("No se ha encontrado ningun partido con esa fecha en esta pista: " + start + ", " + court);
        }
        return match;
    }

    public Match create(LocalDateTime start, String dni1, String dni2, String courtName) {
        TennisCourt court = this.courtRepository.findByName(courtName)
                .orElseThrow(() -> new NotFoundException("No existe esa pista por lo tanto no se puede crear el partido: " + courtName));

        User user1 = this.userRepository.findByDni(dni1)
                .orElseThrow(() -> new NotFoundException("No existe ese usuario: " + dni1));

        User user2 = this.userRepository.findByDni(dni2)
                .orElseThrow(() -> new NotFoundException("No existe ese usuario: " + dni2));

        LocalDateTime end = start.plusHours(DURATION);
        List<Match> sameCourt = this.matchRepository.findByCourt(courtName);
        for (Match existingMatch : sameCourt) {
            LocalDateTime existingStart = existingMatch.getDateTimeStart();
            LocalDateTime existingEnd = existingMatch.getDateTimeEnd();

            if (start.isBefore(existingEnd) && start.plusHours(2).isAfter(existingStart)) {
                throw new DuplicateException("Ya hay un partido en esta pista en este momento. " + start + " - " + end + ", " + courtName);
            }
        }
        Match match = new Match(start, user1, user2, court);
        return this.matchRepository.create(match);
    }

    public void establishWinner(LocalDateTime dateTime, String name, String dni) {
        if (this.courtRepository.findByName(name).isEmpty()) {
            throw new NotFoundException("No existe esa pista: " + name);
        }
        List<Match> sameCourt = this.matchRepository.findByCourt(name);
        Match match = getMatch(dateTime, name, sameCourt);
        if (!dni.equals(match.getUser1().getDni()) && !dni.equals(match.getUser2().getDni())) {
            throw new NotFoundException("No se ha encontrado ningun partido con esa fecha en esa pista con ese jugador: " + dateTime + ", " + name + ", " + dni);
        }
        if (dni.equals(match.getUser1().getDni())) {
            this.matchRepository.establishWinner(match, match.getUser1());
        } else {
            this.matchRepository.establishWinner(match, match.getUser2());
        }
    }

    public Stream<Match> listAll() {
        return this.matchRepository.findAll().stream();
    }

    public void startMatch(LocalDateTime start, String court) {
        if (this.courtRepository.findByName(court).isEmpty()) {
            throw new NotFoundException("No existe esta pista: " + court);
        }
        List<Match> sameCourt = this.matchRepository.findByCourt(court);
        Match match = getMatch(start, court, sameCourt);

        if (match.getStatus() == Match.MatchStatus.IN_PROGRESS || match.getStatus() == Match.MatchStatus.FINISHED) {
            throw new InvalidUse("Este partido ya ha empezado");
        }
        this.matchRepository.startMatch(match);
    }

    public void scoreMatch(LocalDateTime start, String court, String ganador) {
        if (this.courtRepository.findByName(court).isEmpty()) {
            throw new NotFoundException("No existe ese nombre de pista: " + court);
        }
        List<Match> sameCourt = this.matchRepository.findByCourt(court);
        Match match = getMatch(start, court, sameCourt);

        if (match.getStatus() == Match.MatchStatus.FINISHED) {
            throw new InvalidUse("Este partido ya ha finalizado su ganador es: " + match.getGanador().getName());
        }
        if (match.getStatus() != Match.MatchStatus.IN_PROGRESS) {
            throw new InvalidUse("Este partido no ha empezado");
        }
        int auxService = 0;
        if (ganador.equalsIgnoreCase("service")) {
            auxService = 1;
        } else if (ganador.equalsIgnoreCase("rest")) {
            auxService = 2;
        }
        this.matchRepository.scoreMatch(match, auxService);
    }

    public Match readMatch(LocalDateTime start, String court) {
        if (this.courtRepository.findByName(court).isEmpty()) {
            throw new NotFoundException("No existe esta pista: " + court);
        }
        List<Match> sameCourt = this.matchRepository.findByCourt(court);
        Match match = getMatch(start, court, sameCourt);
        for (Match exitingMatch : sameCourt) {
            if (start.equals(exitingMatch.getDateTimeStart())) {
                match = exitingMatch;
            }
        }
        return this.matchRepository.readMatch(match);
    }

    public void moveMatchInHoliday(LocalDate holiday) {
        Stream<Match> matches = listAll();
        Stream<Match> matches2=listAll();
        List<Match> matchesHoliday = matches
                .filter(match -> match.getDateTimeStart().toLocalDate().equals(holiday))
                .toList();
        for (Match match : matchesHoliday) {
            boolean isMoved = false;
            final LocalDate[] newDate = {holiday.plusDays(1)};
            while (!isMoved) {
                List<Match> matchesNewDate = getList(match, matches2, newDate);

                isMoved = tryRescheduleMatch(match, newDate[0], matchesNewDate);

                if (!isMoved) {
                    newDate[0] = newDate[0].plusDays(1);
                }
            }
        }
    }

    private static List<Match> getList(Match match, Stream<Match> matches2, LocalDate[] newDate) {
        return matches2
                .filter(m -> m.getDateTimeStart().toLocalDate().equals(newDate[0])
                        && m.getCourt().equals(match.getCourt()))
                .toList();
    }

    private boolean tryRescheduleMatch(Match match, LocalDate newDate, List<Match> matchesNewDate) {
        LocalTime initialTime = LocalTime.of(9, 0);

        while (initialTime.isBefore(LocalTime.of(21, 0))) {
            LocalDateTime potentialStart = newDate.atTime(initialTime);

            boolean collision = matchesNewDate.stream().
                    anyMatch(m -> Math.abs(Duration.between(potentialStart, m.getDateTimeStart()).toHours()) < DURATION);
            if (!collision) {
                matchRepository.moveMatchInHoliday(match, potentialStart);
                return true;
            } else {
                initialTime = initialTime.plusHours(DURATION);
            }
        }
        return false;
    }

}
