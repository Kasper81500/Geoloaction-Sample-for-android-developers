package pf.paranoidfan.com.paranoidfan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KasperStar on 8/8/2016.
 */
public class SignupResponse {
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("exist")
    @Expose
    public String exist;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("data")
    @Expose
    public UserInfo data;

    public class UserInfo {
        @SerializedName("userid")
        @Expose
        public String userid;
    }
}

