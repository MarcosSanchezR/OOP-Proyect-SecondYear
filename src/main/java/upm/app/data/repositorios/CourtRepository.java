package upm.app.data.repositorios;

import upm.app.data.modelos.TennisCourt;

import java.util.Optional;

public interface CourtRepository extends GenericRepository<TennisCourt> {
    Optional<TennisCourt> findByName(String name);
}
