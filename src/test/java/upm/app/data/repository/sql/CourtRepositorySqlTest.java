package upm.app.data.repository.sql;

import org.junit.jupiter.api.Test;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.repositorios.CourtRepository;
import upm.app.data.repositorios.mysql.CourtRespositorySql;
import upm.app.data.repositorios.mysql.RepositoryMysql;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourtRepositorySqlTest {
    Connection connection = new RepositoryMysql().createConnection();
    private final CourtRepository courtRepository = new CourtRespositorySql(connection);

    @Test
    void testCreateAndRead() {
        Optional<TennisCourt> dbCourt = courtRepository.findByName("pista1");
        dbCourt.ifPresent(court -> courtRepository.deleteById(court.getId()));

        TennisCourt court = courtRepository.create(new TennisCourt("pista1", "dura", "Madrid"));
        Optional<TennisCourt> dbCourt1 = courtRepository.read(court.getId());

        assertTrue(dbCourt1.isPresent());
        assertEquals("pista1", dbCourt1.get().getName());
        assertEquals("dura", dbCourt1.get().getSurfaceType());
        assertEquals("Madrid", dbCourt1.get().getLocation());
    }

    @Test
    void testDelete() {
        TennisCourt court = courtRepository.create(new TennisCourt("pista2", "cesped", "Barcelona"));
        courtRepository.deleteById(court.getId());

        assertFalse(courtRepository.read(court.getId()).isPresent());
    }

    @Test
    void testFindByName() {
        Optional<TennisCourt> dbCourt = courtRepository.findByName("pista1");
        assertTrue(dbCourt.isPresent());
        assertEquals("pista1", dbCourt.get().getName());
    }

    @Test
    void testListAll() {
        List<TennisCourt> list = this.courtRepository.findAll();
        assertFalse(list.isEmpty());
    }
}
