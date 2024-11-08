package upm.app.data.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TennisCourtTest {
    private TennisCourt tennisCourt;

    @BeforeEach
    void before() {
        tennisCourt = new TennisCourt("Pista Central", "Arcilla", "Madrid");
    }

    @Test
    void testTennisCourtBuild() {
        assertEquals("Pista Central", tennisCourt.getName());
        assertEquals("Arcilla", tennisCourt.getSurfaceType());
        assertEquals("Madrid", tennisCourt.getLocation());
    }

    @Test
    void validSurfaceTypeTest() {
        tennisCourt.setSurfaceType("Cesped");
        assertEquals("Cesped", tennisCourt.getSurfaceType());

        tennisCourt.setSurfaceType("Dura");
        assertEquals("Dura", tennisCourt.getSurfaceType());

        assertThrows(InvalidAttributeException.class, ()->new TennisCourt("Pista Central", "agua", "Madrid"));

    }
}