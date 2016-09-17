package pf.paranoidfan.com.paranoidfan.Service;

import pf.paranoidfan.com.paranoidfan.Model.AddChatModelResponse;
import pf.paranoidfan.com.paranoidfan.Model.AddGroupChatModelResponse;
import pf.paranoidfan.com.paranoidfan.Model.AddGroupMessageModelResponse;
import pf.paranoidfan.com.paranoidfan.Model.AddMapPinModel;
import pf.paranoidfan.com.paranoidfan.Model.AddPinDetailResponse;
import pf.paranoidfan.com.paranoidfan.Model.ChatResponse;
import pf.paranoidfan.com.paranoidfan.Model.CityResponse;
import pf.paranoidfan.com.paranoidfan.Model.CommonRespose;
import pf.paranoidfan.com.paranoidfan.Model.ForgotPasswordResponse;
import pf.paranoidfan.com.paranoidfan.Model.FriendListResponse;
import pf.paranoidfan.com.paranoidfan.Model.GroupChatResponse;
import pf.paranoidfan.com.paranoidfan.Model.GroupMessageResponse;
import pf.paranoidfan.com.paranoidfan.Model.GroupResponse;
import pf.paranoidfan.com.paranoidfan.Model.InboxResponse;
import pf.paranoidfan.com.paranoidfan.Model.LoginResponse;
import pf.paranoidfan.com.paranoidfan.Model.MapPinResponse;
import pf.paranoidfan.com.paranoidfan.Model.MapUserResponse;
import pf.paranoidfan.com.paranoidfan.Model.PhoneConfirmResponse;
import pf.paranoidfan.com.paranoidfan.Model.PhoneVerifyResponse;
import pf.paranoidfan.com.paranoidfan.Model.PinDetailResponse;
import pf.paranoidfan.com.paranoidfan.Model.SignupResponse;
import pf.paranoidfan.com.paranoidfan.Model.StadiumResponse;
import pf.paranoidfan.com.paranoidfan.Model.TeamModel;
import pf.paranoidfan.com.paranoidfan.Model.UserModelResponse;
import pf.paranoidfan.com.paranoidfan.Model.UserResponse;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;

public interface SvcApiEndPoint {
    @Multipart
    @POST("/user/login")
    void loginWithEmail(@Part("email") String email, @Part("password") String password, SvcApiRestCallback<LoginResponse> callback);

    @Multipart
    @POST("/user/forgotpassword")
    void loginWithEmail(@Part("email") String email, SvcApiRestCallback<ForgotPasswordResponse> callback);

    @Multipart
    @POST("/user/verifyphone")
    void verifyWithPhone(@Part("userid") String userid,@Part("phone") String phone, SvcApiRestCallback<PhoneVerifyResponse> callback);

    @Multipart
    @POST("/user/confirmphone")
    void confirmWithCode(@Part("userid") String userid,@Part("code") String code, SvcApiRestCallback<PhoneConfirmResponse> callback);

    @Multipart
    @POST("/user/signup")
    void signup(@Part("name") String name,@Part("email") String email,@Part("password") String password, @Part("dob") String dob, SvcApiRestCallback<SignupResponse> callback);

    @GET("/map/getallpins/{latitude}/{longitude}/{userid}")
    void getAllPinsForLocation(
            @Path("latitude") float latitude,
            @Path("longitude") float longitude,
            @Path("userid") String userid,
            SvcApiRestCallback<MapPinResponse> callback
    );

    @GET("/user/getallusers/{userid}")
    void getAllUsers(
            @Path("userid") String userid,
            SvcApiRestCallback<MapUserResponse> callback
    );

    @GET("/map/pinchat/getdetail/{pinID}/{userID}")
    void getPinChatDetailForID(
            @Path("pinID") String pinId,
            @Path("userID") String userId,
            SvcApiRestCallback<PinDetailResponse> callback
    );

    @Multipart
    @POST("/map/pinchat/new")
    void putPinChatReplyForUser(
            @Part("userid") int userId,
            @Part("reply_id") int replyId,
            @Part("pin_id") int pinId,
            @Part("message") String Message,
            @Part("photo") String photoString,
            @Part("latitude") double latitude,
            @Part("longitude") double longitude,
            SvcApiRestCallback<AddPinDetailResponse> callback
    );

    @Multipart
    @POST("/map/pinchat/new")
    void putPinChatWithVideoForUser(
            @Part("userid") int userId,
            @Part("reply_id") int replyId,
            @Part("pin_id") int pinId,
            @Part("message") String Message,
            @Part("photo") String photoString,
            @Part("Video") String video,
            @Part("latitude") double latitude,
            @Part("longitude") double longitude,
            SvcApiRestCallback<AddPinDetailResponse> callback
    );

    @Multipart
    @POST("/map/rating")
    void ratingForPin(
            @Part("userid") String userId,
            @Part("rating") int rating,
            @Part("pin_id") String pinId,
            SvcApiRestCallback<CommonRespose> callback
    );

    @Multipart
    @POST("/user/sendreport")
    void reportChatWithID(
            @Part("userid") String userId,
            @Part("contentid") String chatId,
            @Part("type") String chatType,
            SvcApiRestCallback<CommonRespose> callback
    );

    @Multipart
    @POST("/user/addfavorite")
    void addFavorite(
            @Part("userid") String userId,
            @Part("pin_id") String pinId,
            SvcApiRestCallback<CommonRespose> callback
    );

    @GET("/user/getfriendlist/{userID}")
    void getFriendListForUser(
            @Path("userID") String userId,
            SvcApiRestCallback<FriendListResponse> callback
    );

    @Multipart
    @POST("/map/addpin")
    void addPinWithRatingForUser(
            @Part("userid") String userId,
            @Part("pin") String pin,
            @Part("title") String title,
            @Part("rating") String rating,
            @Part("detail") String detail,
            @Part("photo") String photo,
            @Part("groups") String groups,
            @Part("receivers") String receivers,
            @Part("tags") String tags,
            @Part("latitude") double latitude,
            @Part("longitude") double longitude,
            @Part("address") String address,
            @Part("date_time") String dataTime,
            @Part("twitter_post_id") String twitterPostId,
            @Part("fb_post_id") String fbPostId,
            SvcApiRestCallback<AddMapPinModel> callback
    );

    @GET("/user/getleaderboard")
    void getLeaderboard(
            SvcApiRestCallback<UserResponse> callback
    );

    @GET("/user/getallfavoritepins/{userID}")
    void getAllFavoritePins(
            @Path("userID") String userId,
            SvcApiRestCallback<MapPinResponse> callback
    );

    @GET("/user/getallreviews/{userID}")
    void getAllReviews(
            @Path("userID") String userId,
            SvcApiRestCallback<MapPinResponse> callback
    );

    @GET("/user/getuserbyid/{userID}")
    void getUserInfoByID(
            @Path("userID") String userId,
            SvcApiRestCallback<UserModelResponse> callback
    );

    @GET("/localchat/getall/{Latitude}/{Longitude}/local/{userID}")
    void getAllLocalChatByLocation(
            @Path("Latitude") double latitude,
            @Path("Longitude") double longitude,
            @Path("userID") String userId,
            SvcApiRestCallback<ChatResponse> callback
    );
    @GET("/localchat/getall/{Latitude}/{Longitude}/stadium/{userID}")
    void getAllLocalChatByStadium(
            @Path("Latitude") double latitude,
            @Path("Longitude") double longitude,
            @Path("userID") String userId,
            SvcApiRestCallback<ChatResponse> callback
    );

    @Multipart
    @POST("/localchat/like")
    void likeChatWithID(
            @Part("userid") String userId,
            @Part("id") String chatId,
            SvcApiRestCallback<CommonRespose> callback
    );

    @Multipart
    @POST("/localchat/new")
    void createReplyLocalChatForUser(
            @Part("userid") int userId,
            @Part("reply_id") int replyId,
            @Part("message") String Message,
            @Part("photo") String photoString,
            @Part("latitude") double latitude,
            @Part("longitude") double longitude,
            SvcApiRestCallback<AddChatModelResponse> callback
    );

    @GET("/user/mygroups/{userID}")
    void getMyGroups(
            @Path("userID") String userId,
            SvcApiRestCallback<GroupResponse> callback
    );

    @GET("/settings/getallteams")
    void getAllTeams(
            SvcApiRestCallback<TeamModel[]> callback
    );

    @GET("/settings/suggestedteams")
    void getSuggestedTeams(
            SvcApiRestCallback<TeamModel[]> callback
    );

    @Multipart
    @POST("/user/checkgroupname")
    void checkGroupName(
            @Part("userid") String userId,
            @Part("groupname") String groupname,
            SvcApiRestCallback<CommonRespose> callback
    );

    @GET("/user/groupmembers/{groupID}")
    void getGroupMembers(
            @Path("groupID") String groupId,
            SvcApiRestCallback<UserResponse> callback
    );

    @Multipart
    @POST("/user/group/membership")
    void groupMembership(
            @Part("userid") String userId,
            @Part("membership") String membership,
            @Part("groupid") String groupId,
            SvcApiRestCallback<CommonRespose> callback
    );

    @GET("/groupchat/{groupId}/{userId}")
    void getGroupChatForGroupId(
            @Path("groupId") String groupId,
            @Path("userId") String userId,
            SvcApiRestCallback<GroupChatResponse> callback
    );

    @Multipart
    @POST("/groupchat/new")
    void createNewGroupMessage(
            @Part("userid") int userId,
            @Part("groupid") int groupId,
            @Part("message") String Message,
            @Part("photo") String photoString,
            @Part("latitude") double latitude,
            @Part("longitude") double longitude,
            SvcApiRestCallback<AddGroupChatModelResponse> callback
    );

    @GET("/user/getinbox/{userId}")
    void getInbox(
            @Path("userId") String userId,
            SvcApiRestCallback<InboxResponse> callback
    );

    @GET("/usergroup/getmessages/{senderId}/{groupId}/{lastId}")
    void getUserGroupMessages(
            @Path("senderId") String senderId,
            @Path("groupId") String groupId,
            @Path("lastId") String lastId,
            SvcApiRestCallback<GroupMessageResponse> callback
    );

    @GET("/user/getdirectmessages/{senderId}/{receiverId}/{lastId}")
    void getDirectMessages(
            @Path("senderId") String senderId,
            @Path("receiverId") String receiverId,
            @Path("lastId") String lastId,
            SvcApiRestCallback<GroupMessageResponse> callback
    );

    @Multipart
    @POST("/usergroup/newmessage")
    void createNewMessageForGroup(
            @Part("sender_id") int senderId,
            @Part("user_group_id") int userGroupId,
            @Part("message") String Message,
            @Part("photo") String photoString,
            @Part("sticker") String sticker,
            @Part("latitude") double latitude,
            @Part("longitude") double longitude,
            SvcApiRestCallback<AddGroupMessageModelResponse> callback
    );

    @Multipart
    @POST("/user/newmessage")
    void createNewMessage(
            @Part("sender_id") int senderId,
            @Part("receiver_id") int receiverId,
            @Part("message") String Message,
            @Part("photo") String photoString,
            @Part("sticker") String sticker,
            @Part("latitude") double latitude,
            @Part("longitude") double longitude,
            SvcApiRestCallback<AddGroupMessageModelResponse> callback
    );

    @Multipart
    @POST("/user/updateprofile")
    void updateProfileForUser(
            @Part("userid") String userId,
            @Part("name") String name,
            @Part("email") String email,
            @Part("dob") String birthday,
            SvcApiRestCallback<CommonRespose> callback
    );

    @Multipart
    @POST("/user/updatepassword")
    void changeUserPasswordWithUserID(
            @Part("userid") String userId,
            @Part("oldpassword") String oldPassword,
            @Part("password") String newPassword,
            SvcApiRestCallback<CommonRespose> callback
    );

    @Multipart
    @POST("/user/updatetags")
    void updateUserTagsForUser(
            @Part("userid") String userId,
            @Part("tags") String tags,
            SvcApiRestCallback<CommonRespose> callback
    );

    @GET("/user/getclosebyevents/{userID}/{latitude}/{longitude}")
    void getCloseByEventsPins(
            @Path("userID") String userId,
            @Path("latitude") double latitude,
            @Path("longitude") double longitude,
            SvcApiRestCallback<MapPinResponse> callback
    );

    @GET("/map/getallcities")
    void getAllCities(
            SvcApiRestCallback<CityResponse> callback
    );

    @GET("/map/getallstadium/{latitude}/{longitude}")
    void getAllStadium(
            @Path("latitude") double latitude,
            @Path("longitude") double longitude,
            SvcApiRestCallback<StadiumResponse> callback
    );

    @Multipart
    @POST("/share/pin")
    void sharePin(
            @Part("userid") String userId,
            @Part("groupid") String groupId,
            @Part("pfuserid") String pfUserId,
            @Part("phone") String phone,
            @Part("pinid") String pinId,
            SvcApiRestCallback<CommonRespose> callback
    );

    @Multipart
    @POST("/invite/meetme")
    void sendMeetMeRequest(
            @Part("userid") String userId,
            @Part("pfuserid") String pfUserId,
            @Part("phone") String phone,
            @Part("message") String message,
            SvcApiRestCallback<CommonRespose> callback
    );

    @Multipart
    @POST("/invite/group")
    void sendGroupInvite(
            @Part("groupid") String groupId,
            @Part("userid") String userId,
            @Part("pfuserid") String pfUserId,
            @Part("phone") String phone,
            @Part("message") String message,
            SvcApiRestCallback<CommonRespose> callback
    );

    @Multipart
    @POST("/map/pinchat/like")
    void likePinChatWithID(
            @Part("userid") String userId,
            @Part("id") String pinChatId,
            SvcApiRestCallback<CommonRespose> callback
    );

    @Multipart
    @POST("/user/sendtextmessage")
    void sendTextMessage(
            @Part("userid") String userId,
            @Part("phone") String phone,
            @Part("message") String message,
            SvcApiRestCallback<CommonRespose> callback
    );

    @Multipart
    @POST("/user/getaddedlist")
    void getAddedList(
            @Part("number") String phone,
            SvcApiRestCallback<FriendListResponse> callback
    );

    @Multipart
    @POST("/user/addnewfangroup")
    void addFanGroup(
            @Part("userid") String userId,
            @Part("groupname") String groupName,
            @Part("team") String team,
            @Part("contacts") String contacts,
            @Part("receivers") String receivers,
            @Part("photo") String photo,
            SvcApiRestCallback<AddGroupMessageModelResponse> callback
    );

    @Multipart
    @POST("/user/updateprofilephoto")
    void updateProfilePhoto(
            @Part("userid") String userId,
            @Part("photo") String photo,
            SvcApiRestCallback<CommonRespose> callback
    );

    @Multipart
    @POST("/usergroup/addnew")
    void addUserGroup(
            @Part("sender") String userId,
            @Part("receivers") String users,
            SvcApiRestCallback<CommonRespose> callback
    );

}
