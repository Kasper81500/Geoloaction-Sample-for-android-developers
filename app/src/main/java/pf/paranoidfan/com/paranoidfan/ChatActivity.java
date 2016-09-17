package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import pf.paranoidfan.com.paranoidfan.Model.AddChatModelResponse;
import pf.paranoidfan.com.paranoidfan.Model.ChatModel;
import pf.paranoidfan.com.paranoidfan.Model.ChatResponse;
import pf.paranoidfan.com.paranoidfan.Model.StadiumModel;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class ChatActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
    public static String TAG = ChatActivity.class.getSimpleName();
    ListView chatList;
    boolean delayLock = false;
    List<ChatModel> chatDataList;
    public int phoneWidth = 0;
    public EditText editText;
    ChatListAdapter chatListAdapter;
    String encodedImageString = "";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    private Uri videoFileUri = null;
    String messageString = "";

    StadiumModel stadium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        stadium = (StadiumModel)getIntent().getSerializableExtra("Stadium");
        if(stadium != null){
            TextView titleText = (TextView)findViewById(R.id.toolbar_title);
            titleText.setText("Stadium");
            GetStadiumChats();
        }else{
            GetAllChatByLocation();
        }

        chatList = (ListView)findViewById(R.id.list_chat);
        chatDataList = new ArrayList<ChatModel>();

        editText = (EditText)findViewById(R.id.edittxt_message);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (size.x < size.y)
            phoneWidth = size.x;
        else
            phoneWidth = size.y;

        setupMessageSender();

        ImageView cameraView = (ImageView)findViewById(R.id.img_camera);
        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence camerType[] = new CharSequence[]{"Photo", "Video", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                builder.setTitle("Upload Media");
                builder.setItems(camerType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        if (which == 0) {
                            Intent chooseImageIntent = ImagePicker.getPickImageIntent(ChatActivity.this);
                            startActivityForResult(chooseImageIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        } else if (which == 1) {
                            recordVideo();
                        }
                    }
                });

                builder.show();
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void setupMessageSender(){
        Button sendButton = (Button)findViewById(R.id.btn_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();

                String userId = MainTabActivity.userid;
                String replyId = chatListAdapter.replyId;
                String photoString = encodedImageString;
                String latitude = "" + MainTabActivity.myPosition.latitude;
                String longitude = "" + MainTabActivity.myPosition.longitude;
                messageString = editText.getText().toString();
                editText.setText("");
                chatListAdapter.replyId = "0";

                if(videoFileUri != null){
                    new AddItemsAsyncTask().execute();
                }else if(photoString != null && !photoString.isEmpty()){
                    createReplyLocalChatForUser(
                            userId,
                            replyId,
                            messageString,
                            photoString,
                            latitude,
                            longitude);
                }else if(messageString != null && !messageString.isEmpty()) {
                    createReplyLocalChatForUser(
                            userId,
                            replyId,
                            messageString,
                            photoString,
                            latitude,
                            longitude);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != RESULT_OK){
            Log.d(TAG, "Failed to pick media file");
            return;
        }

        switch(requestCode) {

            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                byte[] bytes = baos.toByteArray();
                encodedImageString = Base64.encodeToString(bytes, Base64.DEFAULT);

            case CAMERA_CAPTURE_VIDEO_REQUEST_CODE:
                Toast.makeText(this,
                        "record video: " + videoFileUri, Toast.LENGTH_SHORT)
                        .show();
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void GetStadiumChats(){

        if(delayLock == true)
            return;

        String userId = MainTabActivity.userid;
        double latitude = Double.parseDouble(stadium.getStadiumLatitude());
        double longitude = Double.parseDouble(stadium.getStadiumLongitude());

        delayLock = true;

        SvcApiService.getUserIdEndPoint().getAllLocalChatByStadium(latitude, longitude, userId, new SvcApiRestCallback<ChatResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(ChatResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);
                    Log.d(TAG, "data=" + result.data.toString());

                    if (responseStatus.equals("true")) {
                        for (int pinIdx = result.data.length - 1; pinIdx >= 0; pinIdx--) {
                            chatDataList.add(result.data[pinIdx]);
                        }

                        chatListAdapter = new ChatListAdapter(ChatActivity.this, chatDataList);
                        chatList.setAdapter(chatListAdapter);
                    }

                    delayLock = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }

    private void GetAllChatByLocation(){

        if(delayLock == true)
            return;

        final String userId = MainTabActivity.userid;
        double latitude = MainTabActivity.myPosition.latitude;
        double longitude = MainTabActivity.myPosition.longitude;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().getAllLocalChatByLocation(latitude, longitude, userId, new SvcApiRestCallback<ChatResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(ChatResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);
                    Log.d(TAG, "data=" + result.data.toString());

                    if (responseStatus.equals("true")) {
                        for (int pinIdx = result.data.length - 1; pinIdx >= 0; pinIdx--) {
                            chatDataList.add(result.data[pinIdx]);
                        }

                        chatListAdapter = new ChatListAdapter(ChatActivity.this, chatDataList);
                        chatList.setAdapter(chatListAdapter);
                    }

                    delayLock = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }

    private void createReplyLocalChatForUser(String userId,
                                        String replyId,
                                        String message,
                                        String photoString,
                                        String latitude,
                                        String longitude)
    {
        if(delayLock == true)
            return;


        int useid = Integer.parseInt(userId);
        int replyid = Integer.parseInt(replyId);
        double latDouble = Double.parseDouble(latitude);
        double longDouble = Double.parseDouble(longitude);

        Log.d(TAG, "useid=" + useid +"replyid=" + replyid + "latDouble=" + latDouble +"longDouble=" + longDouble +"message=" + message );
        delayLock = true;

        SvcApiService.getUserIdEndPoint().createReplyLocalChatForUser(useid,
                replyid,
                message,
                photoString,
                latDouble,
                longDouble,
                new SvcApiRestCallback<AddChatModelResponse>()
                {
                    @Override
                    public void failure(SvcError svcError) {
                        Log.d(TAG, "Failed to Login " + svcError.toString());
                        delayLock = false;
                        showAlertMessage("Error", "Invalid request");
                        encodedImageString = "";
                    }

                    @Override
                    public void success(AddChatModelResponse result, Response response) {
                        Log.d(TAG, "Succeed to Server " + result.toString());
                        try {
                            String responseStatus = result.status;

                            Log.d(TAG, "status=" + responseStatus);

                            if (responseStatus.equals("true")) {
                                chatListAdapter.add(result.data);
                                chatListAdapter.notifyDataSetChanged();
                            }

                            encodedImageString = "";

                            delayLock = false;

                        } catch (Exception e) {
                            encodedImageString = "";
                            e.printStackTrace();
                            delayLock = false;
                        }
                    }
                });
    }

    private void showAlertMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    private static String HTTP_HEADER_AUTH_KEY = "SCta}*XTV1R6SCta}*XTV1R6";

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

    public void AddNewChatWithVideo(String sourceFileUri){
        String fileName = sourceFileUri;
        String userId = MainTabActivity.userid;
        String replyId = chatListAdapter.replyId;
        String latitude = "" + MainTabActivity.myPosition.latitude;
        String longitude = "" + MainTabActivity.myPosition.longitude;
        String message = messageString;

        chatListAdapter.replyId = "0";
        videoFileUri = null;

        Ion.with(ChatActivity.this)
                .load("http://54.67.44.136/api/localchat/new")
                .setHeader("Authkey", HTTP_HEADER_AUTH_KEY)
                .setMultipartParameter("userid", "userId")
                .setMultipartParameter("reply_id", replyId)
                .setMultipartParameter("userid", userId)
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
                                ChatModel model = MapPinControl.CreateChatModelFromJson(result.getAsJsonObject("data"));
                                chatListAdapter.add(model);
                                chatListAdapter.notifyDataSetChanged();
                            }
                        }catch (Exception ex){
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
