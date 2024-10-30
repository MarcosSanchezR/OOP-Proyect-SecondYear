package upm.app.data.repositorios;


import upm.app.data.modelos.User;

import java.time.LocalDate;

public class TenisSeeder {
    private final UserRepository userRepository;

    public TenisSeeder(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public void seed(){
        User[] users={
                new User("user1", LocalDate.of(2005, 11, 1), "12567385t", "666"),
                new User("user2", LocalDate.of(2005, 11, 2), "22567385t", "666"),
                new User("user3", LocalDate.of(2005, 11, 3), "32567385t", "666"),
                new User("user4", LocalDate.of(2005, 11, 4), "42567385t", "666")
        };
        for (int i=0; i<users.length; i++){
            users[i]=this.userRepository.create(users[i]);
        }

    }
}
