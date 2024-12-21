package upm.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.app.data.modelos.User;
import upm.app.data.repositorios.map.UserRepositoryMap;
import upm.app.services.UserService;
import upm.app.services.exceptions.DuplicateException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    private UserRepositoryMap userRepositoryMap;

    @BeforeEach
    void before() {
        userRepositoryMap = new UserRepositoryMap();
        userService = new UserService(userRepositoryMap);
    }

    @Test
    void createTest() {
        User user = new User("Marcos", LocalDate.of(2005, 11, 1), "03948573h", "666");
        User user2 = new User("Daniel", LocalDate.of(2001, 1, 11), "03948573h", "666");

        User userCreated = userService.create(user);

        assertNotNull(userCreated);
        assertEquals("Marcos", userCreated.getName());
        assertEquals("03948573h", userCreated.getDni());
        assertEquals(LocalDate.of(2005, 11, 1), userCreated.getBirthdate());
        assertNotNull(userCreated.getId());

        assertThrows(DuplicateException.class, () -> userService.create(user2));
    }

    @Test
    void deletedByDniTest() {
        User user = new User("Marcos", LocalDate.of(2005, 11, 1), "03948573h", "666");

        userService.create(user);
        userService.deleteByDni("03948573h");
        assertTrue(userRepositoryMap.findByDni("03948573h").isEmpty());

    }

    @Test
    void listAllTest() {
        User user = new User("Marcos", LocalDate.of(2005, 11, 1), "03948573h", "666");
        User user2 = new User("Daniel", LocalDate.of(2001, 1, 11), "03378573p", "66");

        userService.create(user);
        userService.create(user2);

        Stream<User> users = userService.listAll();
        List<User> userList = users.toList();

        assertEquals(2, userList.size());
        assertEquals("Marcos", userList.get(0).getName());
        assertEquals("Daniel", userList.get(1).getName());
    }


}
