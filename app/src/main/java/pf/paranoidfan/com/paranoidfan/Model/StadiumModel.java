package pf.paranoidfan.com.paranoidfan.Model;

/**
 * Created by KasperStar on 9/5/2016.
 */

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StadiumModel implements Serializable{

    @SerializedName("stadium_id")
    @Expose
    private String stadiumId;
    @SerializedName("stadium_name")
    @Expose
    private String stadiumName;
    @SerializedName("stadium_latitude")
    @Expose
    private String stadiumLatitude;
    @SerializedName("stadium_longitude")
    @Expose
    private String stadiumLongitude;
    @SerializedName("stadium_city")
    @Expose
    private String stadiumCity;
    @SerializedName("stadium_state")
    @Expose
    private String stadiumState;
    @SerializedName("stadium_team")
    @Expose
    private String stadiumTeam;
    @SerializedName("distance")
    @Expose
    private String distance;

    public SearchItem getSearchItem(){
        SearchItem searchItem = new SearchItem();
        searchItem.title = stadiumName;
        searchItem.tags = stadiumCity + "," + stadiumState + "," + stadiumTeam;
        searchItem.distance = distance;
        searchItem.location = new LatLng(Double.parseDouble(stadiumLatitude), Double.parseDouble(stadiumLongitude));
        searchItem.pinType = "Stadium";

        return searchItem;
    }

    /**
     *
     * @return
     * The stadiumId
     */
    public String getStadiumId() {
        return stadiumId;
    }

    /**
     *
     * @param stadiumId
     * The stadium_id
     */
    public void setStadiumId(String stadiumId) {
        this.stadiumId = stadiumId;
    }

    /**
     *
     * @return
     * The stadiumName
     */
    public String getStadiumName() {
        return stadiumName;
    }

    /**
     *
     * @param stadiumName
     * The stadium_name
     */
    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    /**
     *
     * @return
     * The stadiumLatitude
     */
    public String getStadiumLatitude() {
        return stadiumLatitude;
    }

    /**
     *
     * @param stadiumLatitude
     * The stadium_latitude
     */
    public void setStadiumLatitude(String stadiumLatitude) {
        this.stadiumLatitude = stadiumLatitude;
    }

    /**
     *
     * @return
     * The stadiumLongitude
     */
    public String getStadiumLongitude() {
        return stadiumLongitude;
    }

    /**
     *
     * @param stadiumLongitude
     * The stadium_longitude
     */
    public void setStadiumLongitude(String stadiumLongitude) {
        this.stadiumLongitude = stadiumLongitude;
    }

    /**
     *
     * @return
     * The stadiumCity
     */
    public String getStadiumCity() {
        return stadiumCity;
    }

    /**
     *
     * @param stadiumCity
     * The stadium_city
     */
    public void setStadiumCity(String stadiumCity) {
        this.stadiumCity = stadiumCity;
    }

    /**
     *
     * @return
     * The stadiumState
     */
    public String getStadiumState() {
        return stadiumState;
    }

    /**
     *
     * @param stadiumState
     * The stadium_state
     */
    public void setStadiumState(String stadiumState) {
        this.stadiumState = stadiumState;
    }

    /**
     *
     * @return
     * The stadiumTeam
     */
    public String getStadiumTeam() {
        return stadiumTeam;
    }

    /**
     *
     * @param stadiumTeam
     * The stadium_team
     */
    public void setStadiumTeam(String stadiumTeam) {
        this.stadiumTeam = stadiumTeam;
    }

    /**
     *
     * @return
     * The distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     * The distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

}