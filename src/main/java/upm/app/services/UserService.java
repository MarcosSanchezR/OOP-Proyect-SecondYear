package upm.app.services;

import upm.app.data.modelos.User;
import upm.app.data.repositorios.UserRepository;
import upm.app.services.exceptions.DuplicateException;
import upm.app.services.exceptions.NotFoundException;
import upm.app.services.exceptions.UnauthorizedException;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        if (this.userRepository.findByDni(user.getDni()).isPresent()) {
            throw new DuplicateException("El dni ya existe, deberia ser único: " + user.getDni());
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

    public User login(String dni, String password) {
        Optional<User> user = this.userRepository.findByDni(dni);
        if (user.isEmpty()) {
            throw new NotFoundException("No autorizado, dni o contraseña incorrectas");
        }
        if (!password.equals(user.get().getPassword())) {
            throw new UnauthorizedException("No autorizado, dni o contraseña incorrectas");
        }
        return user.get();
    }
}
