package upm.app.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.app.data.modelos.User;
import upm.app.data.repositorios.map.UserRepositoryMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

 class UserRepositoryMapTest {

    private UserRepositoryMap userRepositoryMap;
    private User user1;
    private User user2;


    @BeforeEach
    void before() {
        userRepositoryMap = new UserRepositoryMap();
        user1 = new User("Marcos", LocalDate.of(2005, 11, 1), "02485905U", "666");
        user2 = new User("Daniel", LocalDate.of(2000, 12, 3), "00000000I", "666");
    }

    @Test
    void createTest() {
        User userCreated = userRepositoryMap.create(user1);
        assertNotNull(userCreated.getId());
        assertEquals(1, userCreated.getId());

        User userCreated2 = userRepositoryMap.create(user2);
        assertEquals(2, userCreated2.getId());
    }

    @Test
    void deleteByIdTest() {
        User userCreated = userRepositoryMap.create(user1);
        userRepositoryMap.deleteById(userCreated.getId());
        List<User> users = userRepositoryMap.findAll();
        assertTrue(users.isEmpty());
    }

    @Test
    void findAllTest() {
        userRepositoryMap.create(user1);
        userRepositoryMap.create(user2);
        List<User> users = userRepositoryMap.findAll();
        assertEquals(2, users.size());
    }

    @Test
    void findByDniTest() {
        userRepositoryMap.create(user1);
        userRepositoryMap.create(user2);

        Optional<User> userFounded = userRepositoryMap.findByDni("02485905U");
        assertTrue(userFounded.isPresent());
        assertEquals("Marcos", userFounded.get().getName());

        Optional<User> userNotFounded = userRepositoryMap.findByDni("02485335U");
        assertFalse(userNotFounded.isPresent());
    }

}
