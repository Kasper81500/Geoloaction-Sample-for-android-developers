package pf.paranoidfan.com.paranoidfan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KasperStar on 8/27/2016.
 */
public class TeamResponse {
    @SerializedName("data")
    @Expose
    public TeamModel[] data;
}
