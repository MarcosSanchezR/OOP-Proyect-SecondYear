package upm.app.services;

import upm.app.data.modelos.User;
import upm.app.data.repositorios.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        if (this.userRepository.findByDni(user.getDni()).isPresent()) {
            throw new IllegalArgumentException("El dni ya existe, deberia ser único: " + user.getDni());
        }
        return this.userRepository.create(user);
    }

    public void deleteByDni(String dni) {
        Optional<User> userOptional = userRepository.findByDni(dni);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            this.userRepository.deleteById(user.getId());
        }
    }

    public List<User> listAll() {
        return this.userRepository.findAll();
    }
}
