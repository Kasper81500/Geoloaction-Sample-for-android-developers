package pf.paranoidfan.com.paranoidfan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KasperStar on 8/16/2016.
 */
public class AddPinDetailResponse extends CommonRespose {
    @SerializedName("data")
    @Expose
    public PinDetailModel data;
}
