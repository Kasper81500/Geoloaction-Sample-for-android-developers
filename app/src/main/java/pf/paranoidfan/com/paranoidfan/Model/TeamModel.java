package pf.paranoidfan.com.paranoidfan.Model;

/**
 * Created by KasperStar on 8/27/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamModel {

    @SerializedName("team_id")
    @Expose
    private String teamId;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("sport")
    @Expose
    private String sport;

    public SearchItem getSearchItem(){
        SearchItem searchItem = new SearchItem();
        searchItem.title = team;
        searchItem.tags = sport;
        searchItem.distance = null;
        searchItem.location = null;
        searchItem.pinType = "Fan";

        return searchItem;
    }
    /**
     *
     * @return
     * The teamId
     */
    public String getTeamId() {
        return teamId;
    }

    /**
     *
     * @param teamId
     * The team_id
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    /**
     *
     * @return
     * The team
     */
    public String getTeam() {
        return team;
    }

    /**
     *
     * @param team
     * The team
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     *
     * @return
     * The sport
     */
    public String getSport() {
        return sport;
    }

    /**
     *
     * @param sport
     * The sport
     */
    public void setSport(String sport) {
        this.sport = sport;
    }

}