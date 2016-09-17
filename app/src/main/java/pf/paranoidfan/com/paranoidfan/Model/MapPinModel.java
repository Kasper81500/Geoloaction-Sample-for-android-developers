package pf.paranoidfan.com.paranoidfan.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KasperStar on 8/10/2016.
 */
public class MapPinModel {
    @SerializedName("map_pin_id")
    @Expose
    private String mapPinId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("map_pin_type")
    @Expose
    private String mapPinType;
    @SerializedName("map_pin_title")
    @Expose
    private String mapPinTitle;
    @SerializedName("map_pin_detail")
    @Expose
    private String mapPinDetail;
    @SerializedName("map_option_label")
    @Expose
    private String mapOptionLabel;
    @SerializedName("map_option_value")
    @Expose
    private String mapOptionValue;
    @SerializedName("map_pin_photo")
    @Expose
    private String mapPinPhoto;
    @SerializedName("map_pin_cover_photo")
    @Expose
    private String mapPinCoverPhoto;
    @SerializedName("map_pin_permanent")
    @Expose
    private String mapPinPermanent;
    @SerializedName("map_pin_verified")
    @Expose
    private String mapPinVerified;
    @SerializedName("map_pin_tags")
    @Expose
    private String mapPinTags;
    @SerializedName("map_address")
    @Expose
    private String mapAddress;
    @SerializedName("map_pin_latitude")
    @Expose
    private String mapPinLatitude;
    @SerializedName("map_pin_longitude")
    @Expose
    private String mapPinLongitude;
    @SerializedName("map_pin_rating")
    @Expose
    private String mapPinRating;
    @SerializedName("twitter_post_id")
    @Expose
    private String twitterPostId;
    @SerializedName("facebook_post_id")
    @Expose
    private String facebookPostId;
    @SerializedName("map_pin_date_created")
    @Expose
    private String mapPinDateCreated;
    @SerializedName("map_pin_date_expire")
    @Expose
    private String mapPinDateExpire;
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
    @SerializedName("rated")
    @Expose
    private Boolean rated;
    @SerializedName("favorite")
    @Expose
    private Boolean favorite;
    @SerializedName("favorite_count")
    @Expose
    private String favoriteCount;
    @SerializedName("distance")
    @Expose
    private String distance;

    /*Search Item*/
    public SearchItem getSearchItem(){
        SearchItem searchItem = new SearchItem();
        searchItem.title = mapPinTitle;
        searchItem.tags = mapPinTags;
        searchItem.distance = distance;
        searchItem.location = new LatLng(Double.parseDouble(mapPinLatitude), Double.parseDouble(mapPinLongitude));
        searchItem.pinType = mapPinType;
        return searchItem;
    }

    /**
     *
     * @return
     * The mapPinId
     */
    public String getMapPinId() {
        return mapPinId;
    }

    /**
     *
     * @param mapPinId
     * The map_pin_id
     */
    public void setMapPinId(String mapPinId) {
        this.mapPinId = mapPinId;
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
     * The mapPinType
     */
    public String getMapPinType() {
        return mapPinType;
    }

    /**
     *
     * @param mapPinType
     * The map_pin_type
     */
    public void setMapPinType(String mapPinType) {
        this.mapPinType = mapPinType;
    }

    /**
     *
     * @return
     * The mapPinTitle
     */
    public String getMapPinTitle() {
        return mapPinTitle;
    }

    /**
     *
     * @param mapPinTitle
     * The map_pin_title
     */
    public void setMapPinTitle(String mapPinTitle) {
        this.mapPinTitle = mapPinTitle;
    }

    /**
     *
     * @return
     * The mapPinDetail
     */
    public String getMapPinDetail() {
        return mapPinDetail;
    }

    /**
     *
     * @param mapPinDetail
     * The map_pin_detail
     */
    public void setMapPinDetail(String mapPinDetail) {
        this.mapPinDetail = mapPinDetail;
    }

    /**
     *
     * @return
     * The mapOptionLabel
     */
    public String getMapOptionLabel() {
        return mapOptionLabel;
    }

    /**
     *
     * @param mapOptionLabel
     * The map_option_label
     */
    public void setMapOptionLabel(String mapOptionLabel) {
        this.mapOptionLabel = mapOptionLabel;
    }

    /**
     *
     * @return
     * The mapOptionValue
     */
    public String getMapOptionValue() {
        return mapOptionValue;
    }

    /**
     *
     * @param mapOptionValue
     * The map_option_value
     */
    public void setMapOptionValue(String mapOptionValue) {
        this.mapOptionValue = mapOptionValue;
    }

    /**
     *
     * @return
     * The mapPinPhoto
     */
    public String getMapPinPhoto() {
        return mapPinPhoto;
    }

    /**
     *
     * @param mapPinPhoto
     * The map_pin_photo
     */
    public void setMapPinPhoto(String mapPinPhoto) {
        this.mapPinPhoto = mapPinPhoto;
    }

    /**
     *
     * @return
     * The mapPinCoverPhoto
     */
    public String getMapPinCoverPhoto() {
        return mapPinCoverPhoto;
    }

    /**
     *
     * @param mapPinCoverPhoto
     * The map_pin_cover_photo
     */
    public void setMapPinCoverPhoto(String mapPinCoverPhoto) {
        this.mapPinCoverPhoto = mapPinCoverPhoto;
    }

    /**
     *
     * @return
     * The mapPinPermanent
     */
    public String getMapPinPermanent() {
        return mapPinPermanent;
    }

    /**
     *
     * @param mapPinPermanent
     * The map_pin_permanent
     */
    public void setMapPinPermanent(String mapPinPermanent) {
        this.mapPinPermanent = mapPinPermanent;
    }

    /**
     *
     * @return
     * The mapPinVerified
     */
    public String getMapPinVerified() {
        return mapPinVerified;
    }

    /**
     *
     * @param mapPinVerified
     * The map_pin_verified
     */
    public void setMapPinVerified(String mapPinVerified) {
        this.mapPinVerified = mapPinVerified;
    }

    /**
     *
     * @return
     * The mapPinTags
     */
    public String getMapPinTags() {
        return mapPinTags;
    }

    /**
     *
     * @param mapPinTags
     * The map_pin_tags
     */
    public void setMapPinTags(String mapPinTags) {
        this.mapPinTags = mapPinTags;
    }

    /**
     *
     * @return
     * The mapAddress
     */
    public String getMapAddress() {
        return mapAddress;
    }

    /**
     *
     * @param mapAddress
     * The map_address
     */
    public void setMapAddress(String mapAddress) {
        this.mapAddress = mapAddress;
    }

    /**
     *
     * @return
     * The mapPinLatitude
     */
    public String getMapPinLatitude() {
        return mapPinLatitude;
    }

    /**
     *
     * @param mapPinLatitude
     * The map_pin_latitude
     */
    public void setMapPinLatitude(String mapPinLatitude) {
        this.mapPinLatitude = mapPinLatitude;
    }

    /**
     *
     * @return
     * The mapPinLongitude
     */
    public String getMapPinLongitude() {
        return mapPinLongitude;
    }

    /**
     *
     * @param mapPinLongitude
     * The map_pin_longitude
     */
    public void setMapPinLongitude(String mapPinLongitude) {
        this.mapPinLongitude = mapPinLongitude;
    }

    /**
     *
     * @return
     * The mapPinRating
     */
    public String getMapPinRating() {
        return mapPinRating;
    }

    /**
     *
     * @param mapPinRating
     * The map_pin_rating
     */
    public void setMapPinRating(String mapPinRating) {
        this.mapPinRating = mapPinRating;
    }

    /**
     *
     * @return
     * The twitterPostId
     */
    public String getTwitterPostId() {
        return twitterPostId;
    }

    /**
     *
     * @param twitterPostId
     * The twitter_post_id
     */
    public void setTwitterPostId(String twitterPostId) {
        this.twitterPostId = twitterPostId;
    }

    /**
     *
     * @return
     * The facebookPostId
     */
    public String getFacebookPostId() {
        return facebookPostId;
    }

    /**
     *
     * @param facebookPostId
     * The facebook_post_id
     */
    public void setFacebookPostId(String facebookPostId) {
        this.facebookPostId = facebookPostId;
    }

    /**
     *
     * @return
     * The mapPinDateCreated
     */
    public String getMapPinDateCreated() {
        return mapPinDateCreated;
    }

    /**
     *
     * @param mapPinDateCreated
     * The map_pin_date_created
     */
    public void setMapPinDateCreated(String mapPinDateCreated) {
        this.mapPinDateCreated = mapPinDateCreated;
    }

    /**
     *
     * @return
     * The mapPinDateExpire
     */
    public String getMapPinDateExpire() {
        return mapPinDateExpire;
    }

    /**
     *
     * @param mapPinDateExpire
     * The map_pin_date_expire
     */
    public void setMapPinDateExpire(String mapPinDateExpire) {
        this.mapPinDateExpire = mapPinDateExpire;
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
     * The rated
     */
    public Boolean getRated() {
        return rated;
    }

    /**
     *
     * @param rated
     * The rated
     */
    public void setRated(Boolean rated) {
        this.rated = rated;
    }

    /**
     *
     * @return
     * The favorite
     */
    public Boolean getFavorite() {
        return favorite;
    }

    /**
     *
     * @param favorite
     * The favorite
     */
    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    /**
     *
     * @return
     * The favoriteCount
     */
    public String getFavoriteCount() {
        return favoriteCount;
    }

    /**
     *
     * @param favoriteCount
     * The favorite_count
     */
    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
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

}
