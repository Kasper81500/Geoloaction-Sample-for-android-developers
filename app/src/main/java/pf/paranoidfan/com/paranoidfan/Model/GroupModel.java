package pf.paranoidfan.com.paranoidfan.Model;

/**
 * Created by KasperStar on 8/26/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GroupModel implements Serializable {

    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("group_cover_photo")
    @Expose
    private String groupCoverPhoto;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("image_width")
    @Expose
    private Integer imageWidth;
    @SerializedName("image_height")
    @Expose
    private Integer imageHeight;

    /**
     *
     * @return
     * The groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     *
     * @param groupId
     * The group_id
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     *
     * @param groupName
     * The group_name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     *
     * @return
     * The groupCoverPhoto
     */
    public String getGroupCoverPhoto() {
        return groupCoverPhoto;
    }

    /**
     *
     * @param groupCoverPhoto
     * The group_cover_photo
     */
    public void setGroupCoverPhoto(String groupCoverPhoto) {
        this.groupCoverPhoto = groupCoverPhoto;
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
     * The dateCreated
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     *
     * @param dateCreated
     * The date_created
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     *
     * @return
     * The imageWidth
     */
    public Integer getImageWidth() {
        return imageWidth;
    }

    /**
     *
     * @param imageWidth
     * The image_width
     */
    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    /**
     *
     * @return
     * The imageHeight
     */
    public Integer getImageHeight() {
        return imageHeight;
    }

    /**
     *
     * @param imageHeight
     * The image_height
     */
    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

}