package com.corner.apps;

import com.corner.apps.Entity.Team;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestTeam {
    @SerializedName("teams")
    @Expose
    private List<Team> teams = null;

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
