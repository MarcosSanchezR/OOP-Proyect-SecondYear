package upm.app.data.repositorios;

import upm.app.data.modelos.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User>{
     Optional<User> findByDni(String dni);
}
