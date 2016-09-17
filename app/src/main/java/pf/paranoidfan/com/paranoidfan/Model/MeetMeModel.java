package pf.paranoidfan.com.paranoidfan.Model;

import java.io.Serializable;

/**
 * Created by KasperStar on 8/20/2016.
 */
public class MeetMeModel implements Serializable {
    public MeetMeModel(String meetMeType, String pfUserId, String avatar, String name, String phone, boolean isHeader, boolean selected){
        this.meetMeType = meetMeType;
        this.pfUserId = pfUserId;
        this.avatar = avatar;
        this.name = name;
        this.phone = phone;
        this.isHeader = isHeader;
        this.selected = selected;
    }
    public String meetMeType;
    public String pfUserId;
    public String avatar;
    public String name;
    public String phone;
    public boolean isHeader;
    public boolean selected;
}
