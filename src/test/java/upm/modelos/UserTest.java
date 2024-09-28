package upm.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    void before(){
        user=new User("marcos", LocalDate.of(2005,11,1), "02564017t", "01masaro05@gmail.com", 1);
    }

    @Test
    void testUserBuild(){
        assertEquals("marcos", user.getName());
        assertEquals(19, user.getAge());
        assertEquals("02564017t", user.getDni());
        assertEquals("01masaro05@gmail.com", user.getEmail());
    }

    @Test
    void isIdTestTest(){
        assertTrue(user.isId(user.getDni()));
    }

    @Test
    void changeNameTest(){
        user.changeName("david");
        assertEquals("david",user.getName());
    }

    @Test
    void changeEmailTest(){
        user.changeEmail("09poo@gmail.com");
        assertEquals("09poo@gmail.com", user.getEmail());
    }

    @Test
    void setEmailTest(){
        assertThrows(IllegalArgumentException.class,()->new User("marcos", LocalDate.of(2005,11,1), "025640170t", "01masaro05@mailcom",1));
    }

    @Test
    void setBirthdateTest(){
        assertThrows(IllegalArgumentException.class,()->new User("marcos", LocalDate.of(2009,11,1), "025640170t", "01masaro05@gmail.com",1));
    }

    @Test
    void setIdTest(){
        assertThrows(IllegalArgumentException.class,()->new User("marcos", LocalDate.of(2005,11,1), "025640170", "01masaro05@gmail.com",1));
    }

}
