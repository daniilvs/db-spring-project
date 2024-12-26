package com.formulauno.ui;

import com.formulauno.AppException;
import com.formulauno.domain.Engine;
import com.formulauno.domain.Team;
import com.formulauno.service.CurrentLocaleService;
import com.formulauno.service.EngineService;
import com.formulauno.service.TeamService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

@ShellComponent
@RequiredArgsConstructor
public class Commands {
    private final EngineService engineService;
    private final TeamService teamService;
    private final IO io;
    private final CurrentLocaleService currentLocaleService;

    public enum State {
        MAIN_MENU("main menu"), PROCESSING_ENGINE("engine processing"), PROCESSING_TEAM("team processing");
        @Getter
        private final String title;

        State(String title) {
            this.title = title;
        }
    }

    @Getter
    private State state = State.MAIN_MENU;

    private Team handlingTeam;
    private Engine handlingEngine;

    @ShellMethod(value = "change current language", key = {"language", "lang"})
    @ShellMethodAvailability("availableInMainMenu")
    public void setLanguage(String lang) {
        try {
            currentLocaleService.set(lang.strip().toLowerCase());
        }
        catch (AppException e) {
            io.interPrintln(e.getMessage(), e.getParams());
        }
    }

    @ShellMethod(value = "show all teams", key = "teams-all")
    @ShellMethodAvailability("availableInMainMenu")
    public void showAllTeams() {
        var teams = teamService.findAll();
        if (teams.isEmpty())
            io.interPrintln("no-team-found");
        else
            io.println(teamsToString(teams));
    }

    @ShellMethod(value = "find team by its name", key = "team-find")
    @ShellMethodAvailability("availableInMainMenu")
    public void findTeamByName(@ShellOption(defaultValue = "") String name) {
        if (name.isEmpty()) {
            io.interPrint("print-team-name");
            name = io.readLine();
        }
        if (name.isBlank())
            io.interPrintln("operation-cancelled-by-empty-line");
        else {
            var teams = teamService.findByName(name);
            if (teams.isEmpty())
                io.interPrintln("no-team-found");
            else
                io.println(teamsToString(teams));
        }
    }


    @ShellMethod(value = "update team", key = "team-update")
    @ShellMethodAvailability("availableInMainMenu")
    public void updateTeam(@ShellOption(defaultValue = "") String name) {
        if (name.isEmpty()) {
            io.interPrint("print-title-part");
            name = io.readLine();
        }
        if (name.isBlank())
            io.interPrintln("operation-cancelled-by-empty-line");
        else {
            var team = teamService.findByName(name);
            if (team.isEmpty())
                io.interPrintln("no-team-found");
            else {
                handlingTeam = team.get();
                state = State.PROCESSING_TEAM;
                teamShow();
            }
        }
    }

    @ShellMethod(value = "update engine manufacturer", key = "engine-update")
    @ShellMethodAvailability("availableInMainMenu")
    public void updateEngine(@ShellOption(defaultValue = "") String manufacturer) {
        if (manufacturer.isEmpty()) {
            io.interPrint("print-engine-manufacturer");
            manufacturer = io.readLine();
        }
        if (manufacturer.isBlank())
            io.interPrintln("operation-cancelled-by-empty-line");
        else {
            var engine = engineService.findByManufacturer(manufacturer);
            if (engine.isEmpty())
                io.interPrintln("no-team-found");
            else {
                handlingEngine = engine.get();
                state = State.PROCESSING_ENGINE;
                engineShow();
            }
        }
    }

    @ShellMethod(value = "insert new team", key = "team-insert")
    @ShellMethodAvailability("availableInMainMenu")
    public void insertTeam() {
        handlingTeam = new Team(0, "New Team", "Bermuda Triangle", 1, 9999, new HashSet<>());
        state = State.PROCESSING_TEAM;
        teamShow();
    }

    @ShellMethod(value = "show handling engine", key = "engine-show")
    @ShellMethodAvailability("availableInUpdatingEngine")
    public void engineShow() {
        io.println(enginesToString(handlingEngine));
    }

    @ShellMethod(value = "insert new engine", key = "engine-insert")
    @ShellMethodAvailability("availableInMainMenu")
    public void insertEngine() {
        handlingEngine = new Engine(0, "New manufacturer", new HashSet<>());
        state = State.PROCESSING_ENGINE;
        engineShow();
    }

    @ShellMethod(value = "show handling team", key = "team-show")
    @ShellMethodAvailability("availableInUpdatingTeam")
    public void teamShow() {
        io.println(teamsToString(handlingTeam));
    }

    @ShellMethod(value = "set team's name", key = "set-name")
    @ShellMethodAvailability("availableInUpdatingTeam")
    public void setName(@ShellOption(defaultValue = "") String name) {
        if (name.isBlank()) {
            io.interPrint("set-name");
            name = io.readLine();
        }
        if (name.isBlank()) {
            io.interPrintln("Empty line entered, operation cancelled");
        } else {
            handlingTeam.setName(name);
//            teamService.update(handlingTeam);
            io.interPrintln("new-name-is", handlingTeam.getName());
        }
    }

    @ShellMethod(value = "set team's location", key = "set-location")
    @ShellMethodAvailability("availableInUpdatingTeam")
    public void setLocation(@ShellOption(defaultValue = "") String location) {
        if (location.isBlank()) {
            io.interPrint("set-location");
            location = io.readLine();
        }
        if (location.isBlank()) {
            io.interPrintln("Empty line entered, operation cancelled");
        } else {
            handlingTeam.setLocation(location);
//            teamService.update(handlingTeam);
            io.interPrintln("new-location-is", handlingTeam.getLocation());
        }
    }

    @ShellMethod(value = "set team's date since debut", key = "set-since")
    @ShellMethodAvailability("availableInUpdatingTeam")
    public void setSince(@ShellOption(defaultValue = "") String since) {
        if (since.isBlank()) {
            io.interPrint("set-date-since");
            since = io.readLine();
        }
        if (since.isBlank()) {
            io.interPrintln("Empty line entered, operation cancelled");
        } else {
            int date = Integer.parseInt(since);
            if (date > 0) {
                handlingTeam.setSince(date);
//                teamService.update(handlingTeam);
                io.interPrintln("new-since-is", handlingTeam.getSince());
            }
            else io.interPrintln("Year can't be negative");
        }
    }

    @ShellMethod(value = "set team's date till retirement", key = "set-till")
    @ShellMethodAvailability("availableInUpdatingTeam")
    public void setTill(@ShellOption(defaultValue = "") String till) {
        if (till.isBlank()) {
            io.interPrint("set-date-till");
            till = io.readLine();
        }
        if (till.isBlank()) {
            io.interPrintln("Empty line entered, operation cancelled");
        } else {
            int date = Integer.parseInt(till);
            if (date > 0) {
                handlingTeam.setSince(date);
//                teamService.update(handlingTeam);
                io.interPrintln("new-till-is", handlingTeam.getTill());
            }
            else io.interPrintln("Year can't be negative");
        }
    }

    @ShellMethod(value = "remove engine manufacturer", key = "remove-engine")
    @ShellMethodAvailability("availableInUpdatingEngine")
    public void removeEngine(@ShellOption(defaultValue = "") String manufacturer) {
        if (manufacturer.isBlank()) {
            io.interPrint("remove-manufacturer");
            manufacturer = io.readLine();
        }
        if (manufacturer.isBlank()) {
            io.interPrintln("Empty line entered, operation cancelled");
        } else {
            engineService.remove(handlingEngine);
        }
    }


    @ShellMethod(value = "cancel current operation", key = "cancel")
    @ShellMethodAvailability({"availableInUpdatingEngine", "availableInUpdatingTeam"})
    public void cancel() {
        io.interPrintln("operation-cancelled");
        state = State.MAIN_MENU;
    }


    @ShellMethod(value = "perform current operation", key = "perform")
    @ShellMethodAvailability("availableInUpdatingTeam")
    public void perform() {
        try {
            if (checkHandlingTeam()) {
                teamService.update(handlingTeam);
                io.interPrintln("operation-successful");
                state = State.MAIN_MENU;
            }
        } catch (AppException e) {
            io.interPrintln(e.getMessage(), e.getParams());
        }
    }

    private boolean checkHandlingEngine() {
        if (handlingEngine.getManufacturer().isBlank()) {
            throw new AppException("engine.check.manufacturer-must-not-be-empty");
        }
        return true;
    }

    private boolean checkHandlingTeam() {
        if (handlingTeam.getName().isBlank()) {
            throw new AppException("team.check.name-must-not-be-empty");
        }
        if (handlingTeam.getLocation().isBlank()) {
            throw new AppException("team.check.location-must-not-be-empty");
        }
        if (handlingTeam.getSince() == 0) {
            throw new AppException("team.check.since.-must-be-set");
        }
        if (handlingTeam.getTill() == 0) {
            throw new AppException("team.check.till.-must-be-set");
        }
        return true;
    }

    private Availability availableInMainMenu() {
        return state == State.MAIN_MENU ? Availability.available()
                : Availability.unavailable("available in " + State.MAIN_MENU.getTitle() +
                " only, you now in " + state.getTitle());
    }

    private Availability availableInUpdatingEngine() {
        return state == State.PROCESSING_ENGINE ? Availability.available()
                : Availability.unavailable("available in " + State.PROCESSING_ENGINE.getTitle() +
                " only, you now in " + state.getTitle());
    }

    private Availability availableInUpdatingTeam() {
        return state == State.PROCESSING_TEAM ? Availability.available()
                : Availability.unavailable("available in " + State.PROCESSING_TEAM.getTitle() +
                " only, you now in " + state.getTitle());
    }

    private String enginesToString(Collection<Engine> engines) {
        return engines.stream().map(Engine::getManufacturer).collect(joining("\n"));
    }

    private String teamsToString(Collection<Team> teams) {
        return teams.stream()
                .map(this::teamsToString)
                .collect(joining("====================\n"));
    }

    private String teamsToString(Optional<Team> teams) {
        return teams.map(this::teamsToString).orElse("");
    }

    private String teamsToString(Team team) {
        var empty = "<" + io.inter("team.empty") + ">";
        var name = team.getName().isBlank() ? empty : team.getName();
        var location = team.getLocation().isEmpty() ? empty : team.getLocation();
        var since = team.getSince() == 0 ? empty : Integer.toString(team.getSince());
        var till = team.getTill() == 0 ? empty : Integer.toString(team.getTill());

        return io.inter("team.name") + ": " + name + "\n" +
                io.inter("team.location") + ": " + location + "\n" +
                io.inter("team.since") + ": " + since + "\n" +
                io.inter("team.till") + ": " + till + "\n";
    }

    private String enginesToString(Engine engine) {
        var empty = "<" + io.inter("engine.empty") + ">";
        var manufacturer = engine.getManufacturer().isBlank() ? empty : engine.getManufacturer();

        return io.inter("engine.manufacturer") + ": " + manufacturer + "\n";
    }
}
