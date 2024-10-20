package upm.app.data.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    void before() {
        user = new User("marcos", LocalDate.of(2005, 11, 1), "02564017t");
    }

    @Test
    void testUserBuild() {
        assertEquals("marcos", user.getName());
        assertEquals(19, user.getAge());
        assertEquals("02564017t", user.getDni());
    }

    @Test
    void validDniTestTest() {
        assertTrue(user.validDni(user.getDni()));
    }


    @Test
    void setBirthdateTest() {
        assertThrows(IllegalArgumentException.class, () -> new User("marcos", LocalDate.of(2009, 11, 1), "025640170t"));
    }

    @Test
    void setIdTest() {
        assertThrows(IllegalArgumentException.class, () -> new User("marcos", LocalDate.of(2005, 11, 1), "025640170"));
    }

}
