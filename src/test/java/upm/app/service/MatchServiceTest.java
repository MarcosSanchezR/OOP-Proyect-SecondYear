package upm.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.app.data.modelos.Match;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.modelos.User;
import upm.app.data.repositorios.map.CourtRepositoryMap;
import upm.app.data.repositorios.map.MatchRepositoryMap;
import upm.app.data.repositorios.map.UserRepositoryMap;
import upm.app.services.MatchService;
import upm.app.services.exceptions.DuplicateException;
import upm.app.services.exceptions.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MatchServiceTest {

    private MatchService matchService;
    private User user1;
    private User user2;
    private TennisCourt court1;

    @BeforeEach
    void before() {
        MatchRepositoryMap matchRepositoryMap = new MatchRepositoryMap();
        CourtRepositoryMap courtRepositoryMap = new CourtRepositoryMap();
        UserRepositoryMap userRepositoryMap = new UserRepositoryMap();

        user1 = new User("Marcos", LocalDate.of(2005, 11, 1), "03948573h", "666");
        user2 = new User("Daniel", LocalDate.of(2000, 12, 3), "03378573p", "666");
        court1 = new TennisCourt("Pista Central", "Arcilla", "Madrid");
        TennisCourt court2 = new TennisCourt("Pista 2", "Cesped", "Barcelona");

        userRepositoryMap.create(user1);
        userRepositoryMap.create(user2);
        courtRepositoryMap.create(court1);
        courtRepositoryMap.create(court2);

        matchService = new MatchService(matchRepositoryMap, courtRepositoryMap, userRepositoryMap);
    }

    @Test
    void createTest() {
        Match match = matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03948573h", "03378573p", "Pista Central");

        assertNotNull(match.getId());
        assertEquals(user1, match.getUser1());
        assertEquals(user2, match.getUser2());
        assertEquals(court1, match.getCourt());
        assertEquals(LocalDateTime.of(2025, 11, 1, 15, 0, 0), match.getDateTimeEnd());

        assertThrows(NotFoundException.class, () -> matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03948573h", "03378573p", "Non Existent Court"));

        assertThrows(NotFoundException.class, () -> matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03948573h", "00000000x", "Pista Central"));
        assertThrows(NotFoundException.class, () -> matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "00000000x", "03378573p", "Pista Central"));

        assertThrows(DuplicateException.class, () -> matchService.create(LocalDateTime.of(2025, 11, 1, 13, 0, 0), "03948573h", "03378573p", "Pista Central"));
    }

    @Test
    void listAllMatchesTest() {
        matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03948573h", "03378573p", "Pista Central");
        matchService.create(LocalDateTime.of(2025, 11, 1, 15, 0, 0), "03378573p", "03948573h", "Pista 2");

        Stream<Match> matches = matchService.listAll();
        assertEquals(2, matches.toList().size());
    }

    @Test
    void startMatchTest() {
        Match match = matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03948573h", "03378573p", "Pista Central");

        matchService.startMatch(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "Pista Central");

        assertEquals(Match.MatchStatus.IN_PROGRESS, match.getStatus());
    }

    @Test
    void scoreMatchTest() {
        Match match = matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03948573h", "03378573p", "Pista Central");

        matchService.startMatch(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "Pista Central");
        matchService.scoreMatch(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "Pista Central", "service");

        if (match.getService() == 1) {
            assertEquals(15, match.getSets().get(0).getGame().getService());
        } else {
            assertEquals(15, match.getSets().get(0).getGame().getRest());
        }
    }

    @Test
    void readMatchTest() {
        Match match = matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03948573h", "03378573p", "Pista Central");
        Match matchTest = matchService.readMatch(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "Pista Central");

        assertEquals(match, matchTest);
    }

    @Test
    void moveMatchInHolidayTest() {
        LocalDate holiday = LocalDate.of(2025, 11, 1);

        matchService.create(LocalDateTime.of(2025, 11, 1, 9, 0, 0), "03948573h", "03378573p", "Pista Central");
        matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03378573p", "03948573h", "Pista Central");
        matchService.create(LocalDateTime.of(2025, 11, 2, 9, 0, 0), "03948573h", "03378573p", "Pista Central");

        matchService.moveMatchInHoliday(holiday);

        Match match1 = matchService.readMatch(LocalDateTime.of(2025, 11, 2, 9, 0, 0), "Pista Central");
        Match match2 = matchService.readMatch(LocalDateTime.of(2025, 11, 2, 12, 0, 0), "Pista Central");
        Match match3 = matchService.readMatch(LocalDateTime.of(2025, 11, 2, 15, 0, 0), "Pista Central");

        assertNotNull(match1);
        assertNotNull(match2);
        assertNotNull(match3);

        assertEquals(LocalDate.of(2025, 11, 2), match1.getDateTimeStart().toLocalDate());
        assertEquals(LocalDate.of(2025, 11, 2), match2.getDateTimeStart().toLocalDate());
        assertEquals(LocalDate.of(2025, 11, 2), match3.getDateTimeStart().toLocalDate());

    }


}
