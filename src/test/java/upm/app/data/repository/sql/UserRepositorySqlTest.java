package upm.app.data.repository.sql;

import org.junit.jupiter.api.Test;
import upm.app.data.modelos.User;
import upm.app.data.repositorios.UserRepository;
import upm.app.data.repositorios.mysql.RepositoryMysql;
import upm.app.data.repositorios.mysql.UserRepositorySql;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

 class UserRepositorySqlTest {
    Connection connection=new RepositoryMysql().createConnection();
    private final UserRepository userRepository=new UserRepositorySql(connection);

    @Test
    void testCreateAndRead() {
        Optional<User> dbUser = userRepository.findByDni("02485905U");
        dbUser.ifPresent(user -> this.userRepository.deleteById(user.getId()));
        User user = this.userRepository.create(new User("user-1", LocalDate.of(2005, 11, 1), "02485905U", "666"));
        Optional<User> dbUser1 = this.userRepository.read(user.getId());
        assertTrue(dbUser1.isPresent());
        assertEquals("user-1", dbUser1.get().getName());
        assertEquals("02485905U", dbUser1.get().getDni() );
    }

    @Test
    void testDelete() {
        User user = this.userRepository.create(new User("user-2", LocalDate.of(2005, 11, 1), "12485905U", "666"));
        this.userRepository.deleteById(user.getId());
        assertFalse(this.userRepository.read(user.getId()).isPresent());
    }

    @Test
    void testFindByDni() {
        Optional<User> dbUser = userRepository.findByDni("02485905U");
        assertTrue(dbUser.isPresent());
        assertEquals("user-1", dbUser.get().getName());
    }

    @Test
    void testFindByMobileNotFound() {
        assertFalse(userRepository.findByDni("22485905U").isPresent());
    }

    @Test
    void testFindAll() {
        List<User> list = this.userRepository.findAll();
        assertFalse(list.isEmpty());
    }

}
