package com.gmail.andrewandy.object;

import me.i234.gangamlorder.funtest.FunTest;
import me.i234.gangamlorder.funtest.utils.Common;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.HashSet;

public class Team {

    private static Collection<Entity> members = new HashSet<>();
    private String name;
    private Collection<Team> allies = new HashSet<>();
    private String permission;

    Team() {
        super();
    }

    public Team(String name) {
        this();
        this.name = Common.colorize(name);
    }

    public Team(String name, Collection<Entity> teamMembers, Collection<Team> allies) {
        this();
        this.name = name;
        members = teamMembers;
        this.allies = allies;
    }

    public static Team getTeamFromEntity(Entity entity) {
        for (Team team : FunTest.getManager().getRegisteredTeams())
            for (Entity e : team.getMembers()) {
                if (!e.equals(entity)) {
                    continue;
                }
                return team;
            }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public Collection<Entity> getMembers() {
        return members;
    }

    public Collection<Team> getAllies() {
        return allies;
    }

    public void addAlly(Team team) {
        this.allies.add(team);
    }

    public void removeAlly(Team team) {
        this.allies.remove(team);
    }

    public void addMember(Entity member) {
        members.add(member);
    }

    public void removeMember(Entity member) {
        members.remove(member);
    }
}
