package upm.services;

import upm.modelos.User;
import upm.repositorios.UserRepositoryMap;

import java.util.List;

public class UserService {
    private final UserRepositoryMap userRepositoryMap;


    public UserService(UserRepositoryMap userRepositoryMap) {
        this.userRepositoryMap = userRepositoryMap;
    }

    public User create (User user){
    if (this.userRepositoryMap.findByDni(user.getDni()).isPresent()){
        throw new IllegalArgumentException("El dni ya existe, deberia ser único: "+user.getDni());
    }
    return this.userRepositoryMap.create(user);
    }

    public void deleteById(int id){
        this.userRepositoryMap.deleteById(id);
    }

    public List<User> listAll(){
        return this.userRepositoryMap.findAll();
    }
}
