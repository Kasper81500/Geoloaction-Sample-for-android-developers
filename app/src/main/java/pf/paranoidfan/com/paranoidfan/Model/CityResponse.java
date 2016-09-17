package pf.paranoidfan.com.paranoidfan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KasperStar on 9/5/2016.
 */
public class CityResponse {
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("data")
    @Expose
    public CityModel[] data;
}
