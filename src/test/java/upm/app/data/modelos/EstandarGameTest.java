package upm.app.data.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstandarGameTest {
     private EstandarGame estandarGame;

     @BeforeEach
     void setUp() {
         estandarGame = new EstandarGame();

     }

    @Test
    void testInitialState() {
        assertEquals(0, estandarGame.getService());
        assertEquals(0, estandarGame.getRest());
    }

    @Test
    void testEasyWon(){
        estandarGame.serviceWon(); //15
        assertEquals(15, estandarGame.getService());
        estandarGame.serviceWon(); //30
        assertEquals(30, estandarGame.getService());
        estandarGame.serviceWon(); //40
        assertEquals(40, estandarGame.getService());
        estandarGame.serviceWon(); //win
        assertEquals(50, estandarGame.getService());
        assertTrue(estandarGame.gameWon());
    }

    @Test
    void testAdvantage(){
        estandarGame.serviceWon(); //15
        assertEquals(15, estandarGame.getService());
        estandarGame.restWon(); //15-15
        assertEquals(15, estandarGame.getService());
        assertEquals(15, estandarGame.getRest());
        estandarGame.serviceWon(); //30-15
        assertEquals(30, estandarGame.getService());
        assertEquals(15, estandarGame.getRest());
        estandarGame.restWon(); //30-30
        assertEquals(30, estandarGame.getService());
        assertEquals(30, estandarGame.getRest());
        estandarGame.serviceWon(); //40-30
        assertEquals(40, estandarGame.getService());
        assertEquals(30, estandarGame.getRest());
        estandarGame.restWon(); //40-40
        assertEquals(40, estandarGame.getService());
        assertEquals(40, estandarGame.getRest());
        estandarGame.serviceWon(); //50-40
        assertEquals(50, estandarGame.getService());
        assertEquals(40, estandarGame.getRest());
        assertFalse(estandarGame.gameWon());
        estandarGame.restWon(); //40-40
        assertEquals(40, estandarGame.getService());
        assertEquals(40, estandarGame.getRest());
        estandarGame.serviceWon(); //50-40
        assertEquals(50, estandarGame.getService());
        assertEquals(40, estandarGame.getRest());
        estandarGame.serviceWon(); //win
        assertEquals(60, estandarGame.getService());
        assertEquals(40, estandarGame.getRest());
        assertTrue(estandarGame.gameWon());
    }


}
