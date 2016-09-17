package pf.paranoidfan.com.paranoidfan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InboxModel {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("profile_avatar")
    @Expose
    private String profileAvatar;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("message_id")
    @Expose
    private String messageId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("is_read")
    @Expose
    private String isRead;
    @SerializedName("user_group_id")
    @Expose
    private String userGroupId;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;

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
     * The receiverId
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     *
     * @param receiverId
     * The receiver_id
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
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
     * The userGroupId
     */
    public String getUserGroupId() {
        return userGroupId;
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
     * The groupId
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     *
     * @param groupId
     * The group_id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

}