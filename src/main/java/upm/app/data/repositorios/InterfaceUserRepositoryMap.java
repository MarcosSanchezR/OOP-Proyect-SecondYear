package upm.app.data.repositorios;

import upm.app.data.modelos.User;

import java.util.List;
import java.util.Optional;

public interface InterfaceUserRepositoryMap {
    User create(User user);

    void deleteById(int id);

    List<User> findAll();

    Optional<User> findByDni(String dni);
}

