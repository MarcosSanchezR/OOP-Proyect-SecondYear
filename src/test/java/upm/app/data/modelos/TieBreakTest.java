package upm.app.data.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TieBreakTest {
    private TieBreak tieBreak;

    @BeforeEach
    void setUp() {
        tieBreak = new TieBreak();
    }

    @Test
    void testInitialState() {
        assertEquals(0, tieBreak.getService());
        assertEquals(0, tieBreak.getRest());
    }

    @Test
    void testEasyTieBreak() {
        tieBreak.serviceWon();
        tieBreak.serviceWon();
        tieBreak.serviceWon();
        tieBreak.serviceWon();
        tieBreak.serviceWon();
        tieBreak.serviceWon();
        assertEquals(6, tieBreak.getService());
        tieBreak.serviceWon();
        assertEquals(7, tieBreak.getService());
        assertTrue(tieBreak.gameWon());
    }

    @Test
    void testComplicatedTieBreak() {
        tieBreak.serviceWon();
        tieBreak.serviceWon();
        tieBreak.serviceWon();
        tieBreak.serviceWon();
        tieBreak.serviceWon();
        tieBreak.serviceWon();
        assertEquals(6, tieBreak.getService());
        tieBreak.restWon();
        tieBreak.restWon();
        tieBreak.restWon();
        tieBreak.restWon();
        tieBreak.restWon();
        tieBreak.restWon();
        assertEquals(6, tieBreak.getRest());
        tieBreak.serviceWon();
        assertEquals(7, tieBreak.getService());
        assertFalse(tieBreak.gameWon());
        tieBreak.serviceWon();
        assertEquals(8, tieBreak.getService());
        assertTrue(tieBreak.gameWon());
    }


}
