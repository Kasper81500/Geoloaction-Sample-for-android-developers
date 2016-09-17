package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pf.paranoidfan.com.paranoidfan.Helper.ImagePicker;
import pf.paranoidfan.com.paranoidfan.Helper.MapPinControl;
import pf.paranoidfan.com.paranoidfan.Helper.MeetMeType;
import pf.paranoidfan.com.paranoidfan.Model.AddPinDetailResponse;
import pf.paranoidfan.com.paranoidfan.Model.CommonRespose;
import pf.paranoidfan.com.paranoidfan.Model.GroupModel;
import pf.paranoidfan.com.paranoidfan.Model.GroupResponse;
import pf.paranoidfan.com.paranoidfan.Model.MapPinModel;
import pf.paranoidfan.com.paranoidfan.Model.MeetMeModel;
import pf.paranoidfan.com.paranoidfan.Model.PinDetailModel;
import pf.paranoidfan.com.paranoidfan.Model.PinDetailResponse;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class PinDetailFragment extends Fragment implements AbsListView.OnScrollListener {

    public static String TAG = PinDetailFragment.class.getSimpleName();
    MapPinModel mPinModel;

    View fragmentView;

    ListView chatList;
    PinDetailListAdapter pinDetailListAdapter;

    ImageView ratingImage1;
    ImageView ratingImage2;
    ImageView ratingImage3;
    ImageView ratingImage4;
    ImageView ratingImage5;

    int score;
    ImageView ratedImage1;
    ImageView ratedImage2;
    ImageView ratedImage3;
    ImageView ratedImage4;
    ImageView ratedImage5;

    LinearLayout ratingview;

    ImageView favourteImage;

    public EditText editText;

    ImageView cameraImage;

    boolean favouriteImageClicked = false;

    boolean delayLock = false;

    List<PinDetailModel> pinDetails;

    String encodedImageString;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    private Uri videoFileUri = null;
    String messageString = "";

    ArrayList<GroupModel> mGroupList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pindetail, container, false);

        fragmentView = view;

        mGroupList = new ArrayList<GroupModel>();

        chatList = (ListView)view.findViewById(R.id.list_chat);
        chatList.setOnScrollListener(this);

        pinDetails = new ArrayList<PinDetailModel>();

        editText = (EditText) fragmentView.findViewById(R.id.edittxt_message);

        ratedImage1 = (ImageView)view.findViewById(R.id.rate1);
        ratedImage2 = (ImageView)view.findViewById(R.id.rate2);
        ratedImage3 = (ImageView)view.findViewById(R.id.rate3);
        ratedImage4 = (ImageView)view.findViewById(R.id.rate4);
        ratedImage5 = (ImageView)view.findViewById(R.id.rate5);

        ratingImage1 = (ImageView)view.findViewById(R.id.rating1);
        ratingImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0b1;
                setupRatingView(score);
                Log.d(TAG, "rated score = " + score);
            }
        });

        ratingImage2 = (ImageView)view.findViewById(R.id.rating2);
        ratingImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0b11;
                setupRatingView(score);
                Log.d(TAG, "rated score = " + score);
            }
        });

        ratingImage3 = (ImageView)view.findViewById(R.id.rating3);
        ratingImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0b111;
                setupRatingView(score);
                Log.d(TAG, "rated score = " + score);
            }
        });

        ratingImage4 = (ImageView)view.findViewById(R.id.rating4);
        ratingImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0b1111;
                setupRatingView(score);
                Log.d(TAG, "rated score = " + score);
            }
        });

        ratingImage5 = (ImageView)view.findViewById(R.id.rating5);
        ratingImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0b11111;
                setupRatingView(score);
                Log.d(TAG, "rated score = " + score);
            }
        });

        ratingview = (LinearLayout) view.findViewById(R.id.rating_view);
        ImageView closeImage = (ImageView)view.findViewById(R.id.img_close);
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingview.setVisibility(View.GONE);
                int bitScore = 0;
                while ((score & 1 << bitScore) > 0){
                    bitScore++;
                }
                Log.d(TAG, "rating score = " + bitScore);
                RatingForPin(MainTabActivity.userid, bitScore, mPinModel.getMapPinId());
            }
        });

        TextView title = (TextView)view.findViewById(R.id.txt_title);
        title.setText(mPinModel.getMapPinTitle());

        TextView distance = (TextView)view.findViewById(R.id.txt_distance);
        distance.setText(mPinModel.getDistance());
        ImageView directionView = (ImageView)view.findViewById(R.id.img_direction);
        if(mPinModel.getDistance() != null && !mPinModel.getDistance().isEmpty()){
            directionView.setVisibility(View.VISIBLE);
            directionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String urlString = String.format("http://maps.google.com/?saddr=%.0f,%.0f&daddr=%3$s,%4$s",
                            MainTabActivity.myPosition.latitude,
                            MainTabActivity.myPosition.longitude,
                            mPinModel.getCurrentLatitude(),
                            mPinModel.getCurrentLongitude()
                            );
                    Log.d(TAG, "Direction URL: " + urlString);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                    startActivity(browserIntent);
                }
            });
        }else{
            directionView.setVisibility(View.GONE);
        }

        directionView.setImageResource(R.mipmap.ic_direction);

        ImageView verifyImage = (ImageView)view.findViewById(R.id.img_verify);
        if(mPinModel.getVerified().equals("Yes")){
            verifyImage.setVisibility(View.VISIBLE);
        }else{
            verifyImage.setVisibility(View.GONE);
        }

        String mapPinTags = mPinModel.getMapPinTags();

        //-----------Map Image setting part
        ImageView pinImage = (ImageView)view.findViewById(R.id.img_pin);
        MapPinControl.SetMapPinImageByType(pinImage, mPinModel.getMapPinType(), mPinModel.getMapPinTags());

        //-----------

        if(mapPinTags != null){
            TextView team = (TextView)view.findViewById(R.id.txt_team);
            team.setText("Team:" + mapPinTags);
        }

        TextView address = (TextView)view.findViewById(R.id.txt_address);
        String mapPinAddress = mPinModel.getMapAddress();
        if(mapPinAddress != null){
            address.setText(mapPinAddress);
        }else{
            address.setText("");
        }

        setupMessageSender();

        setupRating();

        String userId = MainTabActivity.userid;
        final String pinId = mPinModel.getMapPinId();
        GetAllPinsWithLocation(pinId, userId);

        /*Favourite button control*/
        favourteImage = (ImageView)fragmentView.findViewById(R.id.img_heart);
        favourteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFavorite(MainTabActivity.userid, mPinModel.getMapPinId());
            }
        });
        setupFavourite();

        cameraImage = (ImageView)view.findViewById(R.id.img_camera);
        cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence camerType[] = new CharSequence[]{"Photo", "Video", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Upload Media");
                builder.setItems(camerType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        if (which == 0) {
                            Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
                            startActivityForResult(chooseImageIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        } else if (which == 1) {
                            recordVideo();
                        }
                    }
                });

                builder.show();
            }
        });
        ImageView inviteImage = (ImageView)view.findViewById(R.id.img_invite);
        inviteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<MeetMeModel> groupList = new ArrayList<MeetMeModel>();

                MeetMeModel header = new MeetMeModel(MeetMeType.Group, "", "", "My Groups", "", true, false);
                groupList.add(header);

                for(GroupModel item: mGroupList){
                    MeetMeModel model = new MeetMeModel(MeetMeType.Group, item.getGroupId(), item.getGroupCoverPhoto(), item.getGroupName(), "Group", false, false);
                    groupList.add(model);
                }

                Intent intent = new Intent(getActivity(), MeetMeListActivity.class);

                intent.putExtra("isShare", true);
                intent.putExtra("pinId", pinId);

                Bundle groupListBundle = new Bundle();
                groupListBundle.putSerializable("groups", groupList);
                intent.putExtras(groupListBundle);

                startActivity(intent);
            }
        });

        ImageView shareImage = (ImageView)view.findViewById(R.id.img_share);
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pageUrl = "http://paranoidfan.com/meetme.php?type=" + mPinModel.getMapPinType().replace(" ", "_")
                        +  "&latittude=" + mPinModel.getMapPinLatitude()
                        +  "&longitude=" + mPinModel.getMapPinLongitude();
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, pageUrl);

                try {
                    startActivity(Intent.createChooser(intent, "Select an action"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Log.d(TAG, "Sharing exception occured!");
                }
            }
        });

        GetMyGroups();

        return view;
    }

    private void setupRatingView(int score){
        Log.d(TAG, "score = " + score);
        ratingImage1.setImageResource(((score & 1<<0 ) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratingImage2.setImageResource(((score & 1<<1) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratingImage3.setImageResource(((score & 1<<2) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratingImage4.setImageResource(((score & 1<<3) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratingImage5.setImageResource(((score & 1 << 4) > 0) ? R.mipmap.ic_star_fill : R.mipmap.ic_star_empty);
    }

    private void setupRatedView(int score){
        Log.d(TAG, "score = " + score);
        ratedImage1.setImageResource(((score & 1<<0) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratedImage2.setImageResource(((score & 1<<1) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratedImage3.setImageResource(((score & 1 <<2) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratedImage4.setImageResource(((score & 1<<3) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratedImage5.setImageResource(((score & 1 << 4) > 0) ? R.mipmap.ic_star_fill : R.mipmap.ic_star_empty);
    }

    public void setPinModel(MapPinModel pinModel){
        mPinModel = pinModel;
    }

    private void setupRating(){
        boolean rated = mPinModel.getRated();
        if(rated){
            ratingview.setVisibility(View.GONE);
            Log.d(TAG, "map pin rating score = " + mPinModel.getMapPinRating());
            int score = Integer.parseInt(mPinModel.getMapPinRating());

            int bitscore = 0;
            for(int bitPlus = 0; bitPlus < score; bitPlus++){
                bitscore |= (1<<bitPlus);
            }

            setupRatedView(bitscore);
        }
    }

    private void setupFavourite(){
        boolean isFavourite = mPinModel.getFavorite();
        TextView favouriteText = (TextView)fragmentView.findViewById(R.id.txt_favourite);

        if(isFavourite){
            favourteImage.setImageResource(R.mipmap.ic_heart_red);
        }else{
            favourteImage.setImageResource(R.mipmap.ic_grey_heart);
        }

        int favouriteCount = 0;
        if(mPinModel.getFavoriteCount() != null && !mPinModel.getFavoriteCount().isEmpty())
            favouriteCount = Integer.parseInt(mPinModel.getFavoriteCount());

        Log.d(TAG, "favourite count = " + favouriteCount);

        if(favouriteCount > 0){
            favouriteText.setText(""+favouriteCount);
        }else{
            favouriteText.setText("");
        }
    }
    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void setupMessageSender(){
        Button sendButton = (Button)fragmentView.findViewById(R.id.btn_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();

                String userId = MainTabActivity.userid;
                String replyId = pinDetailListAdapter.replyId;
                String photoString = encodedImageString;
                String latitude = "" + MainTabActivity.myPosition.latitude;
                String longitude = "" + MainTabActivity.myPosition.longitude;
                messageString = editText.getText().toString();
                String pinId = mPinModel.getMapPinId();
                editText.setText("");

                pinDetailListAdapter.replyId = "0";

                if(videoFileUri != null){
                    new AddItemsAsyncTask().execute();
                }else if(photoString != null && !photoString.isEmpty()){
                    putPinChatReplyForUser(
                            userId,
                            replyId,
                            pinId,
                            messageString,
                            photoString,
                            latitude,
                            longitude);
                }else if(messageString != null && !messageString.isEmpty()) {
                    putPinChatReplyForUser(
                            userId,
                            replyId,
                            pinId,
                            messageString,
                            photoString,
                            latitude,
                            longitude);
                }
            }
        });
    }

    private void putPinChatReplyForUser(
                                        String userId,
                                        String replyId,
                                        String pinId,
                                        String message,
                                        String photoString,
                                        String latitude,
                                        String longitude)
    {
        if(delayLock == true)
            return;


        int useid = Integer.parseInt(userId);
        int replyid = Integer.parseInt(replyId);
        int pinid = Integer.parseInt(pinId);
        double latDouble = Double.parseDouble(latitude);
        double longDouble = Double.parseDouble(longitude);

        Log.d(TAG, "useid=" + useid +"replyid=" + replyid +"pinid=" + pinid +"latDouble=" + latDouble +"longDouble=" + longDouble +"message=" + message );
        delayLock = true;

        SvcApiService.getUserIdEndPoint().putPinChatReplyForUser(useid,
                replyid,
                pinid,
                message,
                photoString,
                latDouble,
                longDouble, new SvcApiRestCallback<AddPinDetailResponse>() {
                    @Override
                    public void failure(SvcError svcError) {
                        Log.d(TAG, "Failed to Login " + svcError.toString());
                        delayLock = false;
                        showAlertMessage("Error", "Invalid request");
                    }

                    @Override
                    public void success(AddPinDetailResponse result, Response response) {
                        Log.d(TAG, "Succeed to Server " + result.toString());
                        try {
                            String responseStatus = result.status;

                            Log.d(TAG, "status=" + responseStatus);

                            if (responseStatus.equals("true")) {
                                pinDetailListAdapter.add(result.data);
                                pinDetailListAdapter.notifyDataSetChanged();
                            }

                            encodedImageString = "";

                            delayLock = false;

                        } catch (Exception e) {
                            e.printStackTrace();
                            delayLock = false;
                        }
                    }
                });
    }

    private void GetAllPinsWithLocation(final String pinid, final String userid){

        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().getPinChatDetailForID(pinid, userid, new SvcApiRestCallback<PinDetailResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(PinDetailResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;


                    Log.d(TAG, "status=" + responseStatus);
                    Log.d(TAG, "data=" + result.data.toString());

                    if (responseStatus.equals("true")) {
                        for (int pinIdx = 0; pinIdx < result.data.length; pinIdx++) {
                            pinDetails.add(result.data[pinIdx]);
                            Log.d(TAG, "-----------------pin detail: " + result.data[pinIdx].getPinChatMessage());
                        }

                        pinDetailListAdapter = new PinDetailListAdapter((MainTabActivity) getActivity(), PinDetailFragment.this, pinDetails);
                        chatList.setAdapter(pinDetailListAdapter);
                    }

                    delayLock = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }

    private void GetMyGroups(){

        String userId = MainTabActivity.userid;

        SvcApiService.getUserIdEndPoint().getMyGroups(userId, new SvcApiRestCallback<GroupResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(GroupResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);

                    if (responseStatus.equals("true")) {
                        for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
                            mGroupList.add(result.data[memberIdx]);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertMessage("Error", "Invalid request");
                }
            }
        });
    }

    private void AddFavorite(final String userid, final String pinid){

        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().addFavorite(userid, pinid, new SvcApiRestCallback<CommonRespose>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(CommonRespose result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);
                    if (responseStatus.equals("true")) {
                        if (!favouriteImageClicked) {
                            favouriteImageClicked = true;
                            mPinModel.setFavorite(true);
                            int favouriteCount = Integer.parseInt(mPinModel.getFavoriteCount());
                            favouriteCount++;
                            mPinModel.setFavoriteCount("" + favouriteCount);
                            setupFavourite();
                        } else {
                            favouriteImageClicked = false;
                            mPinModel.setFavorite(false);
                            int favouriteCount = Integer.parseInt(mPinModel.getFavoriteCount());
                            favouriteCount--;
                            mPinModel.setFavoriteCount("" + favouriteCount);
                            setupFavourite();
                        }
                    }

                    delayLock = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }

    private void RatingForPin(final String userId, final int rating, final String pinId){

        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().ratingForPin(userId, rating, pinId, new SvcApiRestCallback<CommonRespose>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(CommonRespose result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);
                    if (responseStatus.equals("true")) {

                        mPinModel.setRated(true);
                        ratingview.setVisibility(View.GONE);

                        setupRatedView(score);

                    } else {
                        showAlertMessage("Error", result.message);
                    }

                    delayLock = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }

    private void showAlertMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File videosFolder = new File(Environment.getExternalStorageDirectory(), "VideoToUpload");
        videosFolder.mkdirs();
        File video = new File(videosFolder, "pf_movie.mov");
        videoFileUri = Uri.fromFile(video);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoFileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != getActivity().RESULT_OK){
            Log.d(TAG, "Failed to pick media file");
            return;
        }

        // if the result is capturing Image
        switch(requestCode) {

            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                byte[] bytes = baos.toByteArray();
                encodedImageString = Base64.encodeToString(bytes, Base64.DEFAULT);

            case CAMERA_CAPTURE_VIDEO_REQUEST_CODE:
                Toast.makeText(getActivity(),
                        "record video: " + videoFileUri, Toast.LENGTH_SHORT)
                        .show();
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public class AddItemsAsyncTask extends AsyncTask<Integer, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... args) {
            try {
                AddNewChatWithVideo(videoFileUri.getPath());

            }catch (Exception e){
                videoFileUri = null;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {

        }
    }

    private static String HTTP_HEADER_AUTH_KEY = "SCta}*XTV1R6SCta}*XTV1R6";
    public void AddNewChatWithVideo(String sourceFileUri){
        String fileName = sourceFileUri;
        String userId = MainTabActivity.userid;
        String replyId = pinDetailListAdapter.replyId;
        String latitude = "" + MainTabActivity.myPosition.latitude;
        String longitude = "" + MainTabActivity.myPosition.longitude;
        String message = messageString;
        String pinId = mPinModel.getMapPinId();

        videoFileUri = null;
        pinDetailListAdapter.replyId = "0";

        Ion.with(getActivity())
                .load("http://54.67.44.136/api/map/pinchat/new")
                .setHeader("Authkey", HTTP_HEADER_AUTH_KEY)
                .setMultipartParameter("userid", userId)
                .setMultipartParameter("reply_id", replyId)
                .setMultipartParameter("pin_id", pinId)
                .setMultipartParameter("message", message)
                .setMultipartParameter("photo", "")
                .setMultipartParameter("latitude", latitude)
                .setMultipartParameter("longitude", longitude)
                .setMultipartFile("video", "quicktime/mov", new File(sourceFileUri))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // print the String that was downloaded
                        System.out.println(result.toString());
                        try {
                            String status = result.get("status").getAsString();
                            if (status.equals("true")) {
                                PinDetailModel model = MapPinControl.CreatePinDetailModelFromJson(result.getAsJsonObject("data"));
                                pinDetailListAdapter.add(model);
                                pinDetailListAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        int firstVisibleRow = chatList.getFirstVisiblePosition();
        int lastVisibleRow = chatList.getLastVisiblePosition();

        for(int i=firstVisibleRow;i<=lastVisibleRow;i++)
        {
            System.out.println(i + "=" + chatList.getItemAtPosition(i));
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState){

    };

}
