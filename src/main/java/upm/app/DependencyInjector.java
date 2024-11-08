package upm.app;

import upm.app.console.CommandLineInterface;
import upm.app.console.ErrorHandler;
import upm.app.console.View;
import upm.app.data.repositorios.CourtRepository;
import upm.app.data.repositorios.MatchRepository;
import upm.app.data.repositorios.TennisSeeder;
import upm.app.data.repositorios.UserRepository;
import upm.app.data.repositorios.map.CourtRepositoryMap;
import upm.app.data.repositorios.map.MatchRepositoryMap;
import upm.app.data.repositorios.map.UserRepositoryMap;
import upm.app.services.CourtService;
import upm.app.services.MatchService;
import upm.app.services.UserService;


public class DependencyInjector {
    private final ErrorHandler errorHandler;
    private final View view;
    private final TennisSeeder tennisSeeder;
    private final CommandLineInterface commandLineInterface;
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
        tennisSeeder.seed();

        this.userService = new UserService(this.userRepository);
        this.courtService = new CourtService(this.courtRepository);
        this.matchService = new MatchService(this.matchRepository, courtRepository, userRepository);

        this.view = new View();
        this.commandLineInterface = new CommandLineInterface(userService, courtService, matchService, view);

        this.errorHandler = new ErrorHandler(this.commandLineInterface, this.view);
    }

    public void run() {
        this.errorHandler.handleErrors();
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public View getView() {
        return view;
    }

    public CommandLineInterface getCommandLineInterface() {
        return commandLineInterface;
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
