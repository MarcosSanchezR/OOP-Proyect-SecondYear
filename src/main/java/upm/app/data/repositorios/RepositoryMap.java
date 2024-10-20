package upm.app.data.repositorios;

import upm.app.data.modelos.Entity;

import java.util.*;

public class RepositoryMap<T extends Entity >{
    private final Map<Integer, T> map;
    private Integer id;

    public RepositoryMap() {
        this.map = new HashMap<>();
        this.id = 1;
    }

    public T create(T entity) {
        entity.setId(this.id);
        this.map.put(this.id, entity);
        this.id++;
        return entity;
    }

    public void deleteById(Integer id) {
        this.map.remove(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(map.values());
    }

}
