package upm.app;

import upm.app.data.repositorios.CourtRepository;
import upm.app.data.repositorios.MatchRepository;
import upm.app.data.repositorios.TennisSeeder;
import upm.app.data.repositorios.UserRepository;
import upm.app.data.repositorios.map.CourtRepositoryMap;
import upm.app.data.repositorios.map.MatchRepositoryMap;
import upm.app.data.repositorios.map.UserRepositoryMap;
import upm.app.gui.Controller;
import upm.app.gui.ListCourt;
import upm.app.gui.command.*;
import upm.app.services.CourtService;
import upm.app.services.MatchService;
import upm.app.services.UserService;


public class DependencyInjector {
    private static final DependencyInjector instance = new DependencyInjector();
    private final Controller controller;
    private final TennisSeeder tennisSeeder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CourtRepository courtRepository;
    private final CourtService courtService;
    private final MatchRepository matchRepository;
    private final MatchService matchService;

    public DependencyInjector() {
        this.userRepository = new UserRepositoryMap();
        this.courtRepository = new CourtRepositoryMap();
        this.matchRepository = new MatchRepositoryMap();
        this.tennisSeeder = new TennisSeeder(userRepository, courtRepository, matchRepository);
        this.tennisSeeder.seed();

        this.userService = new UserService(this.userRepository);
        this.courtService = new CourtService(this.courtRepository);
        this.matchService = new MatchService(this.matchRepository, courtRepository, userRepository);

        this.controller = new Controller();
        this.controller.add(new CreateUser(userService));
        this.controller.add(new CreateCourt(courtService));
        this.controller.add(new CreateMatch(matchService, userService, courtService));
        this.controller.add(new DeleteUser(userService));
        this.controller.add(new DeleteCourt(courtService));
        this.controller.add(new ListUser(userService));
        this.controller.add(new ListCourt(courtService));
        this.controller.add(new ListMatch(matchService));
        this.controller.add(new Login(userService, controller));
        this.controller.add(new Logout(controller));
        this.controller.add(new MoveMatches(matchService));
        this.controller.add(new ReadMatch(matchService, courtService));
        this.controller.add(new ScoreMatch(matchService, courtService));
        this.controller.add(new StartMatch(matchService, courtService));

    }

    public static DependencyInjector getInstance() {
        return instance;
    }

    public Controller getController() {
        return controller;
    }

    public TennisSeeder getTennisSeeder() {
        return tennisSeeder;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserService getUserService() {
        return userService;
    }

    public CourtRepository getCourtRepository() {
        return courtRepository;
    }

    public CourtService getCourtService() {
        return courtService;
    }

    public MatchRepository getMatchRepository() {
        return matchRepository;
    }

    public MatchService getMatchService() {
        return matchService;
    }
}
