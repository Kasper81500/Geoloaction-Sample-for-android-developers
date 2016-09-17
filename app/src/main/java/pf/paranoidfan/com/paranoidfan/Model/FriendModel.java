package pf.paranoidfan.com.paranoidfan.Model;

/**
 * Created by KasperStar on 8/21/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FriendModel {

    @SerializedName("user_id")
    @Expose
    private String userId;
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
    private Object creditCardToken;
    @SerializedName("bank_detail_token")
    @Expose
    private Object bankDetailToken;
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
    private Object mapAvatar;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;
    @SerializedName("facebook_id")
    @Expose
    private Object facebookId;
    @SerializedName("twitter_user")
    @Expose
    private Object twitterUser;
    @SerializedName("facebook_access_token")
    @Expose
    private Object facebookAccessToken;
    @SerializedName("twitter_access_token")
    @Expose
    private Object twitterAccessToken;
    @SerializedName("team_tags")
    @Expose
    private String teamTags;
    @SerializedName("groups")
    @Expose
    private Object groups;
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
    public Object getCreditCardToken() {
        return creditCardToken;
    }

    /**
     *
     * @param creditCardToken
     * The credit_card_token
     */
    public void setCreditCardToken(Object creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

    /**
     *
     * @return
     * The bankDetailToken
     */
    public Object getBankDetailToken() {
        return bankDetailToken;
    }

    /**
     *
     * @param bankDetailToken
     * The bank_detail_token
     */
    public void setBankDetailToken(Object bankDetailToken) {
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
    public Object getMapAvatar() {
        return mapAvatar;
    }

    /**
     *
     * @param mapAvatar
     * The map_avatar
     */
    public void setMapAvatar(Object mapAvatar) {
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
    public Object getFacebookId() {
        return facebookId;
    }

    /**
     *
     * @param facebookId
     * The facebook_id
     */
    public void setFacebookId(Object facebookId) {
        this.facebookId = facebookId;
    }

    /**
     *
     * @return
     * The twitterUser
     */
    public Object getTwitterUser() {
        return twitterUser;
    }

    /**
     *
     * @param twitterUser
     * The twitter_user
     */
    public void setTwitterUser(Object twitterUser) {
        this.twitterUser = twitterUser;
    }

    /**
     *
     * @return
     * The facebookAccessToken
     */
    public Object getFacebookAccessToken() {
        return facebookAccessToken;
    }

    /**
     *
     * @param facebookAccessToken
     * The facebook_access_token
     */
    public void setFacebookAccessToken(Object facebookAccessToken) {
        this.facebookAccessToken = facebookAccessToken;
    }

    /**
     *
     * @return
     * The twitterAccessToken
     */
    public Object getTwitterAccessToken() {
        return twitterAccessToken;
    }

    /**
     *
     * @param twitterAccessToken
     * The twitter_access_token
     */
    public void setTwitterAccessToken(Object twitterAccessToken) {
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
    public Object getGroups() {
        return groups;
    }

    /**
     *
     * @param groups
     * The groups
     */
    public void setGroups(Object groups) {
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

}
