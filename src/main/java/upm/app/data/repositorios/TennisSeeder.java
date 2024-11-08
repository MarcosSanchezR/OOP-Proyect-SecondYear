package upm.app.data.repositorios;


import upm.app.data.modelos.Match;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.modelos.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TennisSeeder {
    private final UserRepository userRepository;
    private final CourtRepository courtRepository;
    private final MatchRepository matchRepository;

    public TennisSeeder(UserRepository userRepository, CourtRepository courtRepository, MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.courtRepository = courtRepository;
        this.matchRepository = matchRepository;
    }

    public void seed() {
        User[] users = {
                new User("user1", LocalDate.of(2005, 11, 1), "12567385t", "666"),
                new User("user2", LocalDate.of(2005, 11, 2), "22567385t", "666"),
                new User("user3", LocalDate.of(2005, 11, 3), "32567385t", "666"),
                new User("user4", LocalDate.of(2005, 11, 4), "42567385t", "666")
        };
        for (int i = 0; i < users.length; i++) {
            users[i] = this.userRepository.create(users[i]);
        }

        TennisCourt[] courts = {
                new TennisCourt("pista1", "Arcilla", "Madrid"),
                new TennisCourt("pista2", "cesped", "Londres"),
                new TennisCourt("pista3", "Arcilla", "Paris"),
                new TennisCourt("pista4", "dura", "Melbourne")
        };
        for (int i = 0; i < courts.length; i++) {
            courts[i] = this.courtRepository.create(courts[i]);
        }

        Match[] matches = {
                new Match(LocalDateTime.of(2024, 11, 1, 12, 30, 0), users[0], users[1], courts[0]),
                new Match(LocalDateTime.of(2024, 11, 2, 12, 30, 0), users[2], users[1], courts[2]),
                new Match(LocalDateTime.of(2024, 11, 3, 12, 30, 0), users[2], users[3], courts[1]),
                new Match(LocalDateTime.of(2024, 11, 4, 12, 30, 0), users[0], users[3], courts[3])

        };
        for (int i = 0; i < matches.length; i++) {
            matches[i] = this.matchRepository.create(matches[i]);
        }

    }
}
