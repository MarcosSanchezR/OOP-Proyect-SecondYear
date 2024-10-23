package upm.app.data.repositorios.map;

import upm.app.data.modelos.User;
import upm.app.data.repositorios.UserRepository;

import java.util.*;

public class UserRepositoryMap extends RepositoryMap<User> implements UserRepository {

    @Override
    public Optional<User> findByDni(String dni) {
        return Optional.empty();
    }
}