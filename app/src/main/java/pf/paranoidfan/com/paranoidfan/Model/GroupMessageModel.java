package pf.paranoidfan.com.paranoidfan.Model;

/**
 * Created by KasperStar on 8/30/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupMessageModel {

    @SerializedName("message_id")
    @Expose
    private String messageId;
    @SerializedName("user_group_id")
    @Expose
    private String userGroupId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("video_link")
    @Expose
    private String videoLink;
    @SerializedName("sticker")
    @Expose
    private String sticker;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("is_read")
    @Expose
    private String isRead;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("profile_avatar")
    @Expose
    private String profileAvatar;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("image_width")
    @Expose
    private Integer imageWidth;
    @SerializedName("image_height")
    @Expose
    private Integer imageHeight;

    /**
     *
     * @return
     * The messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     *
     * @param messageId
     * The message_id
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     *
     * @return
     * The userGroupId
     */
    public String getUserGroupId() {
        return userGroupId;
    }

    /**
     *
     * @param receiverId
     * The receiver_Id
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
    /**
     *
     * @return
     * The receiverId
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     *
     * @param userGroupId
     * The user_group_id
     */
    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }
    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     * The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *
     * @return
     * The videoLink
     */
    public String getVideoLink() {
        return videoLink;
    }

    /**
     *
     * @param videoLink
     * The video_link
     */
    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    /**
     *
     * @return
     * The sticker
     */
    public String getSticker() {
        return sticker;
    }

    /**
     *
     * @param sticker
     * The sticker
     */
    public void setSticker(String sticker) {
        this.sticker = sticker;
    }

    /**
     *
     * @return
     * The senderId
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     *
     * @param senderId
     * The sender_id
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     *
     * @return
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The isRead
     */
    public String getIsRead() {
        return isRead;
    }

    /**
     *
     * @param isRead
     * The is_read
     */
    public void setIsRead(String isRead) {
        this.isRead = isRead;
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
     * The profileAvatar
     */
    public String getProfileAvatar() {
        return profileAvatar;
    }

    /**
     *
     * @param profileAvatar
     * The profile_avatar
     */
    public void setProfileAvatar(String profileAvatar) {
        this.profileAvatar = profileAvatar;
    }

    /**
     *
     * @return
     * The fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     *
     * @param fullname
     * The fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
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