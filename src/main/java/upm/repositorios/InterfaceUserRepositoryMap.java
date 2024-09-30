package upm.repositorios;

import upm.modelos.User;

import java.util.List;

public interface InterfaceUserRepositoryMap {
User create(User user);

void deleteById(int id);

List<User> findAll();
}
