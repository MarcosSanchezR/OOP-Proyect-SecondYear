package upm.app.data.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class SetTest {
    private Set set;

    @BeforeEach
    void setUp(){
        this.set=new Set(new EstandarGame());
    }

    @Test
    void testInitialEstate(){
        assertEquals(0, set.getPlayer1());
        assertEquals(0, set.getPlayer2());
    }

    @Test
    void testEasyWin(){
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(1, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(2, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(3, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(4, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(5, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(6, set.getPlayer1());
        assertTrue(set.setWon());
        assertThrows(InvalidAttributeException.class, () -> set.player1Won());
    }

    @Test
     void testComplicatedWin(){
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(1, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(2, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(3, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(4, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(5, set.getPlayer1());
        set.player2Won();
        set.player2Won();
        set.player2Won();
        set.player2Won();
        assertEquals(1, set.getPlayer2());
        set.player2Won();
        set.player2Won();
        set.player2Won();
        set.player2Won();
        assertEquals(2, set.getPlayer2());
        set.player2Won();
        set.player2Won();
        set.player2Won();
        set.player2Won();
        assertEquals(3, set.getPlayer2());
        set.player2Won();
        set.player2Won();
        set.player2Won();
        set.player2Won();
        assertEquals(4, set.getPlayer2());
        set.player2Won();
        set.player2Won();
        set.player2Won();
        set.player2Won();
        assertEquals(5, set.getPlayer2());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(6, set.getPlayer1());
        assertFalse(set.setWon());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(7, set.getPlayer1());
        assertTrue(set.setWon());
    }

    @Test
     void testWinInTieBreak(){
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(1, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(2, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(3, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(4, set.getPlayer1());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(5, set.getPlayer1());
        set.player2Won();
        set.player2Won();
        set.player2Won();
        set.player2Won();
        assertEquals(1, set.getPlayer2());
        set.player2Won();
        set.player2Won();
        set.player2Won();
        set.player2Won();
        assertEquals(2, set.getPlayer2());
        set.player2Won();
        set.player2Won();
        set.player2Won();
        set.player2Won();
        assertEquals(3, set.getPlayer2());
        set.player2Won();
        set.player2Won();
        set.player2Won();
        set.player2Won();
        assertEquals(4, set.getPlayer2());
        set.player2Won();
        set.player2Won();
        set.player2Won();
        set.player2Won();
        assertEquals(5, set.getPlayer2());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertEquals(6, set.getPlayer1());
        assertFalse(set.setWon());
        set.player2Won();
        set.player2Won();
        set.player2Won();
        set.player2Won();
        assertEquals(6, set.getPlayer2());
        assertFalse(set.setWon());
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        set.player1Won();
        assertThrows(InvalidAttributeException.class, ()-> set.player1Won());
        assertEquals(7, set.getPlayer1());
        assertTrue(set.setWon());

    }

}
