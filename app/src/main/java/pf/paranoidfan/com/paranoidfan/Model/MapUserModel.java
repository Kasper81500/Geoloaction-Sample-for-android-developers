package pf.paranoidfan.com.paranoidfan.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KasperStar on 8/12/2016.
 */
public class MapUserModel {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("email_address")
    @Expose
    private String emailAddress;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("phone_verified")
    @Expose
    private String phoneVerified;
    @SerializedName("birthday")
    @Expose
    private Object birthday;
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
    @SerializedName("profile_points")
    @Expose
    private String profilePoints;
    @SerializedName("team_tags")
    @Expose
    private Object teamTags;
    @SerializedName("groups")
    @Expose
    private Object groups;
    @SerializedName("show_location")
    @Expose
    private String showLocation;
    @SerializedName("credit_card_token")
    @Expose
    private Object creditCardToken;
    @SerializedName("bank_detail_token")
    @Expose
    private Object bankDetailToken;
    @SerializedName("checkin_social")
    @Expose
    private String checkinSocial;
    @SerializedName("date_registered")
    @Expose
    private String dateRegistered;
    @SerializedName("cc_added")
    @Expose
    private String ccAdded;
    @SerializedName("bank_added")
    @Expose
    private String bankAdded;

    /*Search Item*/
    public SearchItem getSearchItem(){
        SearchItem searchItem = new SearchItem();
        searchItem.title = fullname;
        searchItem.tags = (String)teamTags;
        searchItem.distance = null;
        searchItem.location = new LatLng(Double.parseDouble(currentLatitude), Double.parseDouble(currentLongitude));
        searchItem.pinType = "";

        return searchItem;
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
     * The phone
     */
    public Object getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(Object phone) {
        this.phone = phone;
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
     * The birthday
     */
    public Object getBirthday() {
        return birthday;
    }

    /**
     *
     * @param birthday
     * The birthday
     */
    public void setBirthday(Object birthday) {
        this.birthday = birthday;
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
     * The teamTags
     */
    public Object getTeamTags() {
        return teamTags;
    }

    /**
     *
     * @param teamTags
     * The team_tags
     */
    public void setTeamTags(Object teamTags) {
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
     * The ccAdded
     */
    public String getCcAdded() {
        return ccAdded;
    }

    /**
     *
     * @param ccAdded
     * The cc_added
     */
    public void setCcAdded(String ccAdded) {
        this.ccAdded = ccAdded;
    }

    /**
     *
     * @return
     * The bankAdded
     */
    public String getBankAdded() {
        return bankAdded;
    }

    /**
     *
     * @param bankAdded
     * The bank_added
     */
    public void setBankAdded(String bankAdded) {
        this.bankAdded = bankAdded;
    }
}
