package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import pf.paranoidfan.com.paranoidfan.Model.AddGroupChatModelResponse;
import pf.paranoidfan.com.paranoidfan.Model.GroupChatModel;
import pf.paranoidfan.com.paranoidfan.Model.GroupChatResponse;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class GroupChatActivity extends AppCompatActivity {

    public static String TAG = ChatActivity.class.getSimpleName();
    boolean delayLock = false;
    List<GroupChatModel> chatDataList;
    ListView chatList;
    GroupChatAdapter adapter;
    public EditText editText;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    private Uri videoFileUri = null;
    String messageString = "";
    String encodedImageString = "";
    String groupId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        chatList = (ListView)findViewById(R.id.list_group_chat);
        chatDataList = new ArrayList<GroupChatModel>();

        editText = (EditText)findViewById(R.id.edittxt_message);

        groupId = getIntent().getStringExtra("groupId");
        String userId = MainTabActivity.userid;
        GetGroupChatForGroupId(groupId, userId);

        setupMessageSender();

        ImageView cameraView = (ImageView)findViewById(R.id.img_camera);
        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence camerType[] = new CharSequence[]{"Photo", "Video", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(GroupChatActivity.this);
                builder.setTitle("Upload Media");
                builder.setItems(camerType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        if (which == 0) {
                            Intent chooseImageIntent = ImagePicker.getPickImageIntent(GroupChatActivity.this);
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
                String photoString = encodedImageString;
                String latitude = "" + MainTabActivity.myPosition.latitude;
                String longitude = "" + MainTabActivity.myPosition.longitude;
                messageString = editText.getText().toString();
                editText.setText("");

                if (videoFileUri != null) {
                    new AddItemsAsyncTask().execute();
                } else if (photoString != null && !photoString.isEmpty()) {
                    createNewGroupMessage(
                            userId,
                            groupId,
                            messageString,
                            photoString,
                            latitude,
                            longitude);
                } else if (messageString != null && !messageString.isEmpty()) {
                    createNewGroupMessage(
                            userId,
                            groupId,
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

    private void GetGroupChatForGroupId(final String groupid, final String userid){

        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().getGroupChatForGroupId(groupid, userid, new SvcApiRestCallback<GroupChatResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(GroupChatResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);
                    Log.d(TAG, "data=" + result.data.toString());

                    if (responseStatus.equals("true")) {
                        for (int pinIdx = result.data.length - 1; pinIdx >= 0; pinIdx--) {
                            chatDataList.add(result.data[pinIdx]);
                        }

                        adapter = new GroupChatAdapter(GroupChatActivity.this, chatDataList);
                        chatList.setAdapter(adapter);
                    }

                    delayLock = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }
    private void createNewGroupMessage(String userId,
                                         String groupId,
                                         String message,
                                         String photoString,
                                         String latitude,
                                         String longitude)
    {
        if(delayLock == true)
            return;


        int useid = Integer.parseInt(userId);
        int groupid = Integer.parseInt(groupId);
        double latDouble = Double.parseDouble(latitude);
        double longDouble = Double.parseDouble(longitude);

        Log.d(TAG, "useid=" + useid +"groupid=" + groupid + "latDouble=" + latDouble +"longDouble=" + longDouble +"message=" + message );
        delayLock = true;

        SvcApiService.getUserIdEndPoint().createNewGroupMessage(useid,
                groupid,
                message,
                photoString,
                latDouble,
                longDouble,
                new SvcApiRestCallback<AddGroupChatModelResponse>() {
                    @Override
                    public void failure(SvcError svcError) {
                        Log.d(TAG, "Failed to Login " + svcError.toString());
                        delayLock = false;
                        showAlertMessage("Error", "Invalid request");
                        encodedImageString = "";
                    }

                    @Override
                    public void success(AddGroupChatModelResponse result, Response response) {
                        Log.d(TAG, "Succeed to Server " + result.toString());
                        try {
                            String responseStatus = result.status;

                            Log.d(TAG, "status=" + responseStatus);

                            if (responseStatus.equals("true")) {
                                adapter.add(result.data);
                                adapter.notifyDataSetChanged();
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

        String userId = MainTabActivity.userid;
        String latitude = "" + MainTabActivity.myPosition.latitude;
        String longitude = "" + MainTabActivity.myPosition.longitude;
        String message = messageString;

        videoFileUri = null;

        Ion.with(GroupChatActivity.this)
                .load("http://54.67.44.136/api/groupchat/new")
                .setHeader("Authkey", HTTP_HEADER_AUTH_KEY)
                .setMultipartParameter("userid", userId)
                .setMultipartParameter("groupid", groupId)
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
                                GroupChatModel model = MapPinControl.CreateGroupChatModelFromJson(result.getAsJsonObject("data"));
                                adapter.add(model);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                });
    }
}
