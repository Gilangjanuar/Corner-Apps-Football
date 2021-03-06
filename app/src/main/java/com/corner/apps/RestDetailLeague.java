package com.corner.apps;

import com.corner.apps.ui.detailliga.League;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestDetailLeague {
    @SerializedName("leagues")
    @Expose
    private List<League> leagues = null;

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }


}
