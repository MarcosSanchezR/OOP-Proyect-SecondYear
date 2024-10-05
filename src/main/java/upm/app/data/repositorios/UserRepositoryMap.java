package upm.app.data.repositorios;

import upm.app.data.modelos.User;

import java.util.*;

public class UserRepositoryMap implements InterfaceUserRepositoryMap {
    private final Map<Integer, User> map;
    private Integer id;

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

    @Override
    public Optional<User> findByDni(String dni) {
        for (User user : this.findAll()) {
            if (user.getDni().equalsIgnoreCase(dni)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Integer getId(User user) {
        return user.getId();
    }

    public void setId(User user, Integer id) {
        user.setId(id);
    }
}