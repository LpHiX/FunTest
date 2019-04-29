package me.i234.gangamlorder.funtest.object;

import me.i234.gangamlorder.funtest.utils.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class TeamManager {

    private List<Team> teams = new ArrayList<>();

    public List<Team> getTeams() {
        return teams;
    }

    public void createTeam(Team team) {
        teams.add(team);
        Common.log(Level.INFO, "*aCreated Team: &e " + team.getName());
    }

    public void deleteTeam(Team team) {
        teams.remove((team));
    }


}
