package upm.app.services;

import upm.app.data.modelos.TennisCourt;
import upm.app.data.repositorios.CourtRepository;
import upm.app.services.exceptions.DuplicateException;

import java.util.List;
import java.util.Optional;

public class CourtService {
    private final CourtRepository courtRepository;

    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    public TennisCourt create(TennisCourt tennisCourt) {
        if (this.courtRepository.findByName(tennisCourt.getName()).isPresent()) {
            throw new DuplicateException("El nombre ya existe, deberia ser unico: " + tennisCourt.getName());
        }
        return this.courtRepository.create(tennisCourt);
    }

    public void deleteByName(String name) {
        Optional<TennisCourt> courtOptional = courtRepository.findByName(name);
        if (courtOptional.isPresent()) {
            TennisCourt court = courtOptional.get();
            this.courtRepository.deleteById(court.getId());
        }
    }

    public List<TennisCourt> listAll() {
        return this.courtRepository.findAll();
    }
}
