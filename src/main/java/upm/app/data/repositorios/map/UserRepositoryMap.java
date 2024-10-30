package upm.app.data.repositorios.map;

import upm.app.data.modelos.User;
import upm.app.data.repositorios.UserRepository;

import java.util.*;

public class UserRepositoryMap extends RepositoryMap<User> implements UserRepository {

    @Override
    public Optional<User> findByDni(String dni){
        for (User user: this.findAll()){
            if (user.getDni().equalsIgnoreCase(dni)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}