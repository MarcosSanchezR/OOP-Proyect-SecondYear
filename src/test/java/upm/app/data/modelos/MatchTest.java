package upm.app.data.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class MatchTest {

    private Match match;
    private User user1;
    private User user2;
    private TennisCourt court;

    @BeforeEach
    void before() {
        user1 = new User("Marcos", LocalDate.of(2005, 11, 1), "03948573h", "666");
        user2 = new User("Daniel", LocalDate.of(2000, 12, 3), "03948573h", "666");
        court = new TennisCourt("Pista Central", "Arcilla", "Madrid");
        match = new Match(LocalDateTime.of(2025, 11, 1, 12, 0, 0), user1, user2, court);
    }

    @Test
    void testMatchConstructor() {
        assertEquals(user1, match.getUser1());
        assertEquals(user2, match.getUser2());
        assertEquals(court, match.getCourt());
        assertEquals(LocalDateTime.of(2025, 11, 1, 12, 0, 0), match.getDateTimeStart());
        assertEquals(LocalDateTime.of(2025, 11, 1, 14, 0, 0), match.getDateTimeEnd());
    }

    @Test
    void testSetDateTimeStart() {
        LocalDateTime newStart = LocalDateTime.of(2025, 11, 2, 15, 0, 0);
        match.setDateTimeStart(newStart);
        assertEquals(newStart, match.getDateTimeStart());
        assertEquals(newStart.plusHours(2), match.getDateTimeEnd());

        LocalDateTime pastStart = LocalDateTime.of(2020, 11, 2, 15, 0, 0);
        assertThrows(InvalidAttributeException.class, () -> match.setDateTimeStart(pastStart));
    }
}
