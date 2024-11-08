package upm.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.app.data.modelos.Match;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.modelos.User;
import upm.app.data.repositorios.map.MatchRepositoryMap;
import upm.app.data.repositorios.map.CourtRepositoryMap;
import upm.app.data.repositorios.map.UserRepositoryMap;
import upm.app.services.MatchService;
import upm.app.services.exceptions.DuplicateException;
import upm.app.services.exceptions.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchServiceTest {

    private MatchService matchService;
    private MatchRepositoryMap matchRepositoryMap;
    private CourtRepositoryMap courtRepositoryMap;
    private UserRepositoryMap userRepositoryMap;
    private User user1;
    private User user2;
    private TennisCourt court1;
    private TennisCourt court2;

    @BeforeEach
    void before() {
        matchRepositoryMap = new MatchRepositoryMap();
        courtRepositoryMap = new CourtRepositoryMap();
        userRepositoryMap = new UserRepositoryMap();

        user1 = new User("Marcos", LocalDate.of(2005, 11, 1), "03948573h", "666");
        user2 = new User("Daniel", LocalDate.of(2000, 12, 3), "03378573p", "666");
        court1 = new TennisCourt("Pista Central", "Arcilla", "Madrid");
        court2 = new TennisCourt("Pista 2", "Cesped", "Barcelona");

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
        assertEquals(LocalDateTime.of(2025, 11, 1, 14, 0, 0), match.getDateTimeEnd());

        assertThrows(NotFoundException.class, () -> matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03948573h", "03378573p", "Non Existent Court"));

        assertThrows(NotFoundException.class, () -> matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03948573h", "00000000x", "Pista Central"));
        assertThrows(NotFoundException.class, () -> matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "00000000x", "03378573p", "Pista Central"));

        assertThrows(DuplicateException.class, () -> matchService.create(LocalDateTime.of(2025, 11, 1, 13, 0, 0), "03948573h", "03378573p", "Pista Central"));
    }

    @Test
    void establishWinnerTest() {
        Match match = matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03948573h", "03378573p", "Pista Central");

        matchService.establishWinner(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "Pista Central", "03948573h");

        assertEquals(user1, match.getGanador());

        assertThrows(NotFoundException.class, () -> matchService.establishWinner(LocalDateTime.of(2025, 11, 1, 13, 0, 0), "Pista Central", "03948573h"));

        assertThrows(NotFoundException.class, () -> matchService.establishWinner(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "Pista Central", "00000000x"));

    }

    @Test
    void listAllMatchesTest() {
        matchService.create(LocalDateTime.of(2025, 11, 1, 12, 0, 0), "03948573h", "03378573p", "Pista Central");
        matchService.create(LocalDateTime.of(2025, 11, 1, 15, 0, 0), "03378573p", "03948573h", "Pista 2");

        List<Match> matches = matchService.listAll();
        assertEquals(2, matches.size());
    }



}
