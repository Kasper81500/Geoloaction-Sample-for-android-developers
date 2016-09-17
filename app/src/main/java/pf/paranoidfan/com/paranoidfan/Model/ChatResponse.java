package pf.paranoidfan.com.paranoidfan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KasperStar on 8/25/2016.
 */
public class ChatResponse {
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("data")
    @Expose
    public ChatModel[] data;
}
