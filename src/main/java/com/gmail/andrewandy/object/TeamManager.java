package com.gmail.andrewandy.object;

import java.util.HashSet;
import java.util.Set;

public class TeamManager {

    private Set<Team> registeredTeams = new HashSet<>();

    public Set<Team> getRegisteredTeams() {
        return registeredTeams;
    }

    public void addTeam(Team team) {
        registeredTeams.add(team);
    }

    public void removeTeam(Team team) {
        registeredTeams.remove(team);
    }

}
