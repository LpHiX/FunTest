package me.i234.gangamlorder.funtest.object;

import me.i234.gangamlorder.funtest.FunTest;
import me.i234.gangamlorder.funtest.utils.Common;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class Team {

    private Set<Entity> members = new HashSet<>();
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public Team(String name, Set<Entity> members) {
        this.name = name;
        this.members = members;
    }

    public static Team getTeamByName(String name) {
        for (Team team : FunTest.getTeamManager().getTeams()) {
            if (!ChatColor.stripColor(team.getName()).equals(ChatColor.stripColor(name))) {
                continue;
            }
            return team;
        }
        return null;
    }

    public static Team getTeamByEntity(Entity entity) {
        for (Team team : FunTest.getTeamManager().getTeams()) {
            if (!team.getMembers().contains(entity)) {
                continue;
            }
            return team;
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public Set<Entity> getMembers() {
        return this.members;
    }

    public boolean hasMember(Entity entity) {
        return (members.contains(entity));
    }

    public void addMember(Entity entity) {
        this.members.add(entity);
        Common.log(Level.INFO, "&5Added entity to " + name + " 's team: &e" + entity.getType().toString());
    }

    public void removeMember(Entity entity) {
        this.members.remove(entity);
        Common.log(Level.INFO, "&5oRemoved entity to " + name + " 's team: &e" + entity.getType().toString());
    }


}
