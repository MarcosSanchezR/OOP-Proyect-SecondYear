package upm.app.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.app.data.modelos.Match;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.modelos.User;
import upm.app.data.repositorios.map.MatchRepositoryMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchRepositoryMapTest {

    private MatchRepositoryMap matchRepositoryMap;
    private Match match1;
    private Match match2;
    private User user1;
    private User user2;
    private TennisCourt court1;
    private TennisCourt court2;

    @BeforeEach
    void before() {
        matchRepositoryMap = new MatchRepositoryMap();
        user1 = new User("Marcos", LocalDate.of(2005, 11, 1), "03948573h", "666");
        user2 = new User("Daniel", LocalDate.of(2000, 12, 3), "03948573h", "666");
        court1 = new TennisCourt("Pista Central", "Arcilla", "Madrid");
        court2 = new TennisCourt("Pista 2", "Cesped", "Barcelona");
        match1 = new Match(LocalDateTime.of(2025, 11, 1, 12, 0, 0), user1, user2, court1);
        match2 = new Match(LocalDateTime.of(2025, 11, 1, 15, 0, 0), user2, user1, court2);
        matchRepositoryMap.create(match1);
        matchRepositoryMap.create(match2);
    }

    @Test
    void createTest() {
        assertNotNull(match1.getId());
        assertNotNull(match2.getId());
        assertEquals(1, match1.getId());
        assertEquals(2, match2.getId());
    }

    @Test
    void findByCourtTest() {
        List<Match> matchesInCourt1 = matchRepositoryMap.findByCourt("Pista Central");
        assertEquals(1, matchesInCourt1.size());
        assertEquals(court1, matchesInCourt1.get(0).getCourt());

        List<Match> matchesInCourt2 = matchRepositoryMap.findByCourt("Pista 2");
        assertEquals(1, matchesInCourt2.size());
        assertEquals(court2, matchesInCourt2.get(0).getCourt());

        List<Match> matchesInNoCourt = matchRepositoryMap.findByCourt("No existe");
        assertTrue(matchesInNoCourt.isEmpty());
    }

    @Test
    void findAllTest() {
        List<Match> matches = matchRepositoryMap.findAll();
        assertEquals(2, matches.size());
    }

    @Test
    void startMatchTest() {
        matchRepositoryMap.startMatch(match1);
        assertNotEquals(0, match1.getService());
        assertEquals(Match.MatchStatus.IN_PROGRESS, match1.getStatus());
    }

    @Test
    void scoreMatchTest() {
        matchRepositoryMap.startMatch(match1);
        matchRepositoryMap.scoreMatch(match1, 1);
        if (match1.getService() == 1) {
            assertEquals(15, match1.getSets().get(0).getGame().getService());
        } else {
            assertEquals(15, match1.getSets().get(0).getGame().getRest());
        }
    }

    @Test
    void readMatchTest() {
        Match matchTest = matchRepositoryMap.readMatch(match1);
        assertEquals(match1, matchTest);
    }

    @Test
    void moveMatchInHolidayTest() {
        LocalDateTime newDate = LocalDateTime.of(2025, 11, 2, 12, 0, 0);

        matchRepositoryMap.moveMatchInHoliday(match1, newDate);

        assertEquals(LocalDate.of(2025, 11, 2), match1.getDateTimeStart().toLocalDate());
    }

}
