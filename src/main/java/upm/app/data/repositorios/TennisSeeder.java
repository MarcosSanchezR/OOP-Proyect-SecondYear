package upm.app.data.repositorios;


import upm.app.data.modelos.TennisCourt;
import upm.app.data.modelos.User;

import java.time.LocalDate;

public class TennisSeeder {
    private final UserRepository userRepository;
    private final CourtRepository courtRepository;

    public TennisSeeder(UserRepository userRepository, CourtRepository courtRepository){
        this.userRepository=userRepository;
        this.courtRepository=courtRepository;
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

        TennisCourt[] courts={
            new TennisCourt("Manolo Santana", "Arcilla", "Madrid"),
            new TennisCourt("Centre Court", "cesped", "Londres"),
            new TennisCourt("Philippe Chatrier", "Arcilla", "Paris"),
            new TennisCourt("Rod Laver Arena", "dura", "Melbourne")
        };
        for (int i=0; i<users.length; i++){
            courts[i]=this.courtRepository.create(courts[i]);
        }

    }
}
