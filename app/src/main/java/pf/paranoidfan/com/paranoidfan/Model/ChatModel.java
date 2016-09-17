package pf.paranoidfan.com.paranoidfan.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KasperStar on 8/25/2016.
 */
public class ChatModel {

    @SerializedName("local_chat_id")
    @Expose
    private String localChatId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("chat_message")
    @Expose
    private String chatMessage;
    @SerializedName("chat_photo")
    @Expose
    private String chatPhoto;
    @SerializedName("chat_video_link")
    @Expose
    private String chatVideoLink;
    @SerializedName("chat_latitude")
    @Expose
    private String chatLatitude;
    @SerializedName("chat_longitude")
    @Expose
    private String chatLongitude;
    @SerializedName("chat_like_count")
    @Expose
    private String chatLikeCount;
    @SerializedName("chat_date_created")
    @Expose
    private String chatDateCreated;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("email_address")
    @Expose
    private String emailAddress;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("credit_card_token")
    @Expose
    private String creditCardToken;
    @SerializedName("bank_detail_token")
    @Expose
    private String bankDetailToken;
    @SerializedName("current_latitude")
    @Expose
    private String currentLatitude;
    @SerializedName("current_longitude")
    @Expose
    private String currentLongitude;
    @SerializedName("profile_avatar")
    @Expose
    private String profileAvatar;
    @SerializedName("map_avatar")
    @Expose
    private String mapAvatar;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;
    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    @SerializedName("twitter_user")
    @Expose
    private String twitterUser;
    @SerializedName("facebook_access_token")
    @Expose
    private String facebookAccessToken;
    @SerializedName("twitter_access_token")
    @Expose
    private String twitterAccessToken;
    @SerializedName("team_tags")
    @Expose
    private String teamTags;
    @SerializedName("groups")
    @Expose
    private String groups;
    @SerializedName("profile_points")
    @Expose
    private String profilePoints;
    @SerializedName("show_location")
    @Expose
    private String showLocation;
    @SerializedName("checkin_social")
    @Expose
    private String checkinSocial;
    @SerializedName("verification_code")
    @Expose
    private String verificationCode;
    @SerializedName("phone_verified")
    @Expose
    private String phoneVerified;
    @SerializedName("user_active")
    @Expose
    private String userActive;
    @SerializedName("verified")
    @Expose
    private String verified;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("date_registered")
    @Expose
    private String dateRegistered;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("liked")
    @Expose
    private Boolean liked;
    @SerializedName("image_width")
    @Expose
    private Integer imageWidth;
    @SerializedName("image_height")
    @Expose
    private Integer imageHeight;

    /**
     *
     * @return
     * The localChatId
     */
    public String getLocalChatId() {
        return localChatId;
    }

    /**
     *
     * @param localChatId
     * The local_chat_id
     */
    public void setLocalChatId(String localChatId) {
        this.localChatId = localChatId;
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
     * The chatMessage
     */
    public String getChatMessage() {
        return chatMessage;
    }

    /**
     *
     * @param chatMessage
     * The chat_message
     */
    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    /**
     *
     * @return
     * The chatPhoto
     */
    public String getChatPhoto() {
        return chatPhoto;
    }

    /**
     *
     * @param chatPhoto
     * The chat_photo
     */
    public void setChatPhoto(String chatPhoto) {
        this.chatPhoto = chatPhoto;
    }

    /**
     *
     * @return
     * The chatVideoLink
     */
    public String getChatVideoLink() {
        return chatVideoLink;
    }

    /**
     *
     * @param chatVideoLink
     * The chat_video_link
     */
    public void setChatVideoLink(String chatVideoLink) {
        this.chatVideoLink = chatVideoLink;
    }

    /**
     *
     * @return
     * The chatLatitude
     */
    public String getChatLatitude() {
        return chatLatitude;
    }

    /**
     *
     * @param chatLatitude
     * The chat_latitude
     */
    public void setChatLatitude(String chatLatitude) {
        this.chatLatitude = chatLatitude;
    }

    /**
     *
     * @return
     * The chatLongitude
     */
    public String getChatLongitude() {
        return chatLongitude;
    }

    /**
     *
     * @param chatLongitude
     * The chat_longitude
     */
    public void setChatLongitude(String chatLongitude) {
        this.chatLongitude = chatLongitude;
    }

    /**
     *
     * @return
     * The chatLikeCount
     */
    public String getChatLikeCount() {
        return chatLikeCount;
    }

    /**
     *
     * @param chatLikeCount
     * The chat_like_count
     */
    public void setChatLikeCount(String chatLikeCount) {
        this.chatLikeCount = chatLikeCount;
    }

    /**
     *
     * @return
     * The chatDateCreated
     */
    public String getChatDateCreated() {
        return chatDateCreated;
    }

    /**
     *
     * @param chatDateCreated
     * The chat_date_created
     */
    public void setChatDateCreated(String chatDateCreated) {
        this.chatDateCreated = chatDateCreated;
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
     * The emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     *
     * @param emailAddress
     * The email_address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     *
     * @param birthday
     * The birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The creditCardToken
     */
    public String getCreditCardToken() {
        return creditCardToken;
    }

    /**
     *
     * @param creditCardToken
     * The credit_card_token
     */
    public void setCreditCardToken(String creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

    /**
     *
     * @return
     * The bankDetailToken
     */
    public String getBankDetailToken() {
        return bankDetailToken;
    }

    /**
     *
     * @param bankDetailToken
     * The bank_detail_token
     */
    public void setBankDetailToken(String bankDetailToken) {
        this.bankDetailToken = bankDetailToken;
    }

    /**
     *
     * @return
     * The currentLatitude
     */
    public String getCurrentLatitude() {
        return currentLatitude;
    }

    /**
     *
     * @param currentLatitude
     * The current_latitude
     */
    public void setCurrentLatitude(String currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    /**
     *
     * @return
     * The currentLongitude
     */
    public String getCurrentLongitude() {
        return currentLongitude;
    }

    /**
     *
     * @param currentLongitude
     * The current_longitude
     */
    public void setCurrentLongitude(String currentLongitude) {
        this.currentLongitude = currentLongitude;
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
     * The mapAvatar
     */
    public String getMapAvatar() {
        return mapAvatar;
    }

    /**
     *
     * @param mapAvatar
     * The map_avatar
     */
    public void setMapAvatar(String mapAvatar) {
        this.mapAvatar = mapAvatar;
    }

    /**
     *
     * @return
     * The walletBalance
     */
    public String getWalletBalance() {
        return walletBalance;
    }

    /**
     *
     * @param walletBalance
     * The wallet_balance
     */
    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    /**
     *
     * @return
     * The facebookId
     */
    public String getFacebookId() {
        return facebookId;
    }

    /**
     *
     * @param facebookId
     * The facebook_id
     */
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    /**
     *
     * @return
     * The twitterUser
     */
    public String getTwitterUser() {
        return twitterUser;
    }

    /**
     *
     * @param twitterUser
     * The twitter_user
     */
    public void setTwitterUser(String twitterUser) {
        this.twitterUser = twitterUser;
    }

    /**
     *
     * @return
     * The facebookAccessToken
     */
    public String getFacebookAccessToken() {
        return facebookAccessToken;
    }

    /**
     *
     * @param facebookAccessToken
     * The facebook_access_token
     */
    public void setFacebookAccessToken(String facebookAccessToken) {
        this.facebookAccessToken = facebookAccessToken;
    }

    /**
     *
     * @return
     * The twitterAccessToken
     */
    public String getTwitterAccessToken() {
        return twitterAccessToken;
    }

    /**
     *
     * @param twitterAccessToken
     * The twitter_access_token
     */
    public void setTwitterAccessToken(String twitterAccessToken) {
        this.twitterAccessToken = twitterAccessToken;
    }

    /**
     *
     * @return
     * The teamTags
     */
    public String getTeamTags() {
        return teamTags;
    }

    /**
     *
     * @param teamTags
     * The team_tags
     */
    public void setTeamTags(String teamTags) {
        this.teamTags = teamTags;
    }

    /**
     *
     * @return
     * The groups
     */
    public String getGroups() {
        return groups;
    }

    /**
     *
     * @param groups
     * The groups
     */
    public void setGroups(String groups) {
        this.groups = groups;
    }

    /**
     *
     * @return
     * The profilePoints
     */
    public String getProfilePoints() {
        return profilePoints;
    }

    /**
     *
     * @param profilePoints
     * The profile_points
     */
    public void setProfilePoints(String profilePoints) {
        this.profilePoints = profilePoints;
    }

    /**
     *
     * @return
     * The showLocation
     */
    public String getShowLocation() {
        return showLocation;
    }

    /**
     *
     * @param showLocation
     * The show_location
     */
    public void setShowLocation(String showLocation) {
        this.showLocation = showLocation;
    }

    /**
     *
     * @return
     * The checkinSocial
     */
    public String getCheckinSocial() {
        return checkinSocial;
    }

    /**
     *
     * @param checkinSocial
     * The checkin_social
     */
    public void setCheckinSocial(String checkinSocial) {
        this.checkinSocial = checkinSocial;
    }

    /**
     *
     * @return
     * The verificationCode
     */
    public String getVerificationCode() {
        return verificationCode;
    }

    /**
     *
     * @param verificationCode
     * The verification_code
     */
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    /**
     *
     * @return
     * The phoneVerified
     */
    public String getPhoneVerified() {
        return phoneVerified;
    }

    /**
     *
     * @param phoneVerified
     * The phone_verified
     */
    public void setPhoneVerified(String phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    /**
     *
     * @return
     * The userActive
     */
    public String getUserActive() {
        return userActive;
    }

    /**
     *
     * @param userActive
     * The user_active
     */
    public void setUserActive(String userActive) {
        this.userActive = userActive;
    }

    /**
     *
     * @return
     * The verified
     */
    public String getVerified() {
        return verified;
    }

    /**
     *
     * @param verified
     * The verified
     */
    public void setVerified(String verified) {
        this.verified = verified;
    }

    /**
     *
     * @return
     * The lastLogin
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     *
     * @param lastLogin
     * The last_login
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     *
     * @return
     * The dateRegistered
     */
    public String getDateRegistered() {
        return dateRegistered;
    }

    /**
     *
     * @param dateRegistered
     * The date_registered
     */
    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
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

    /**
     *
     * @return
     * The liked
     */
    public Boolean getLiked() {
        return liked;
    }

    /**
     *
     * @param liked
     * The liked
     */
    public void setLiked(Boolean liked) {
        this.liked = liked;
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

