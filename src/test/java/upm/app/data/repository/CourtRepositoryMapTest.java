package upm.app.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.repositorios.map.CourtRepositoryMap;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

 class CourtRepositoryMapTest {
    private CourtRepositoryMap courtRepositoryMap;
    private TennisCourt court1;
    private TennisCourt court2;

    @BeforeEach
    void before() {
        courtRepositoryMap = new CourtRepositoryMap();
        court1 = new TennisCourt("Pista Central", "Arcilla", "Madrid");
        court2 = new TennisCourt("Pista Norte", "Cesped", "Barcelona");
    }

    @Test
    void createTest() {
        TennisCourt courtCreated = courtRepositoryMap.create(court1);
        assertNotNull(courtCreated.getId());
        assertEquals(1, courtCreated.getId());

        TennisCourt courtCreated2 = courtRepositoryMap.create(court2);
        assertEquals(2, courtCreated2.getId());
    }

    @Test
    void deleteByIdTest() {
        TennisCourt courtCreated = courtRepositoryMap.create(court1);
        courtRepositoryMap.deleteById(courtCreated.getId());
        List<TennisCourt> courts = courtRepositoryMap.findAll();
        assertTrue(courts.isEmpty());
    }

    @Test
    void findAllTest() {
        courtRepositoryMap.create(court1);
        courtRepositoryMap.create(court2);
        List<TennisCourt> courts = courtRepositoryMap.findAll();
        assertEquals(2, courts.size());
    }

    @Test
    void findByNameTest() {
        courtRepositoryMap.create(court1);
        courtRepositoryMap.create(court2);

        Optional<TennisCourt> courtFound = courtRepositoryMap.findByName("Pista Central");
        assertTrue(courtFound.isPresent());
        assertEquals("Madrid", courtFound.get().getLocation());

        Optional<TennisCourt> courtNotFound = courtRepositoryMap.findByName("Pista Sur");
        assertFalse(courtNotFound.isPresent());
    }
}
