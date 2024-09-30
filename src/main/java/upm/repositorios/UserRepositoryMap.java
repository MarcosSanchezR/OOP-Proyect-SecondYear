package upm.repositorios;

import upm.modelos.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryMap implements InterfaceUserRepositoryMap {
    private final Map<Integer, User> map;
    private int id;

    public UserRepositoryMap() {
        this.map = new HashMap<>();
        this.id = 1;
    }

    @Override
    public User create(User user) {
        this.setId(user, this.id);
        this.map.put(this.id, user);
        this.id++;
        return user;

    }

    @Override
    public void deleteById(int id) {
        this.map.remove(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(map.values());
    }

    public void setId(User user, Integer id) {
        user.setId(id);
    }
}