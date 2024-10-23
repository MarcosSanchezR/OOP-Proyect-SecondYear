package upm.app.data.repositorios;

import upm.app.data.modelos.Entity;

import java.util.List;

public interface GenericRepository <T extends Entity> {
    T create (T entity);
    void deleteById(Integer id);
    List<T> findAll();
}
