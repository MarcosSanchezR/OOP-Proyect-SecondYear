package upm.repositorios;

import upm.modelos.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryMap {
    private final Map<Integer, User> map;
    private int id;

    public UserRepositoryMap() {
        this.map = new HashMap<>();
        this.id = 1;
    }

    public User logUser(User user) {
        this.setId(user, this.id);
        this.map.put(this.id, user);
        this.id++;
        return user;

    }

    public void delteById(int id){
        this.map.remove(id);
    }


    public void setId(User user, Integer id) {
        user.setId(id);
    }

}