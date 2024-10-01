package upm.repositorios;

import upm.modelos.User;

import java.util.List;
import java.util.Optional;

public interface InterfaceUserRepositoryMap {
    User create(User user);

    void deleteById(int id);

    List<User> findAll();

    public Optional<User> findByDni(String dni);
}

