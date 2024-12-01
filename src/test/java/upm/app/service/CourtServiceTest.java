package upm.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.repositorios.map.CourtRepositoryMap;
import upm.app.services.CourtService;
import upm.app.services.exceptions.DuplicateException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourtServiceTest {
    private CourtService courtService;
    private CourtRepositoryMap courtRepositoryMap;

    @BeforeEach
    void before() {
        courtRepositoryMap = new CourtRepositoryMap();
        courtService = new CourtService(courtRepositoryMap);
    }

    @Test
    void createTest() {
        TennisCourt court1 = new TennisCourt("Pista Central", "Arcilla", "Madrid");
        TennisCourt court2 = new TennisCourt("Pista Central", "Cesped", "Barcelona");

        TennisCourt createdCourt = courtService.create(court1);
        assertNotNull(createdCourt);
        assertEquals("Pista Central", createdCourt.getName());
        assertEquals("Arcilla", createdCourt.getSurfaceType());

        assertThrows(DuplicateException.class, () -> courtService.create(court2));
    }

    @Test
    void deleteByNameTest() {
        TennisCourt court = new TennisCourt("Pista Central", "Arcilla", "Madrid");

        courtService.create(court);
        courtService.deleteByName("Pista Central");

        assertTrue(courtRepositoryMap.findByName("Pista Central").isEmpty());
    }

    @Test
    void listAllTest() {
        TennisCourt court1 = new TennisCourt("Pista Central", "Arcilla", "Madrid");
        TennisCourt court2 = new TennisCourt("Pista Norte", "Cesped", "Barcelona");

        courtService.create(court1);
        courtService.create(court2);

        List<TennisCourt> courts = courtService.listAll();

        assertEquals(2, courts.size());
        assertEquals("Pista Central", courts.get(0).getName());
        assertEquals("Pista Norte", courts.get(1).getName());
    }

}