package upm.app.services;

import upm.app.data.modelos.User;
import upm.app.data.repositorios.UserRepositoryMap;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepositoryMap userRepositoryMap;


    public UserService(UserRepositoryMap userRepositoryMap) {
        this.userRepositoryMap = userRepositoryMap;
    }

    public User create(User user) {
        if (this.userRepositoryMap.findByDni(user.getDni()).isPresent()) {
            throw new IllegalArgumentException("El dni ya existe, deberia ser único: " + user.getDni());
        }
        return this.userRepositoryMap.create(user);
    }

    public void deleteByDni(String dni) {
        Optional<User> userOptional = userRepositoryMap.findByDni(dni);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            this.userRepositoryMap.deleteById(user.getId());
        }
    }

    public List<User> listAll() {
        return this.userRepositoryMap.findAll();
    }
}
