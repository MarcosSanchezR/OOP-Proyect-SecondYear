package upm.app.data.repositorios.map;

import upm.app.data.modelos.TennisCourt;
import upm.app.data.repositorios.CourtRepository;

import java.util.Optional;

public class CourtRepositoryMap extends RepositoryMap<TennisCourt> implements CourtRepository {
    @Override
    public Optional<TennisCourt> findByName(String name) {
        for (TennisCourt court: this.findAll()){
            if (court.getName().equalsIgnoreCase(name)){
                return Optional.of(court);
            }
        }
        return Optional.empty();
    }
}
