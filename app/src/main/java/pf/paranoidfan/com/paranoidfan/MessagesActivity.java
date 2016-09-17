package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.EmoticonsGridAdapter.KeyClickListener;
import pf.paranoidfan.com.paranoidfan.Helper.ImagePicker;
import pf.paranoidfan.com.paranoidfan.Helper.MapPinControl;
import pf.paranoidfan.com.paranoidfan.Model.AddGroupMessageModelResponse;
import pf.paranoidfan.com.paranoidfan.Model.GroupMessageModel;
import pf.paranoidfan.com.paranoidfan.Model.GroupMessageResponse;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class MessagesActivity extends AppCompatActivity implements KeyClickListener {
    public static String TAG = GroupsActivity.class.getSimpleName();

    /*For emoji keyboard*/
    private static final int NO_OF_EMOTICONS = 36;
    private View popUpView;
    private EmotChatListAdapter mAdapter;
    private LinearLayout emoticonsCover;
    private PopupWindow popupWindow;
    private int keyboardHeight;
    private LinearLayout parentLayout;
    private boolean isKeyBoardVisible;
    private Bitmap[] emoticons;

    ArrayList<GroupMessageModel> messageList;
    ListView listView;
    MessagesAdapter adapter;

    public boolean isGroup = false;
    public String groupId = "";
    public String receiverName = "";
    public String receiverId = "";

    boolean delayLock = false;
    String encodedImageString = "";
    String selectedStickName = "";

    EditText content;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    private Uri videoFileUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        isGroup = getIntent().getBooleanExtra("isGroup", false);
        groupId = getIntent().getStringExtra("groupId");
        receiverId = getIntent().getStringExtra("receiverId");
        receiverName = getIntent().getStringExtra("receiverName");

        TextView title = (TextView)findViewById(R.id.toolbar_title);
        title.setText(receiverName);

        content = (EditText)findViewById(R.id.edittxt_message);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        Button sendButton = (Button)findViewById(R.id.btn_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();
                createNewMessage();
            }
        });

        messageList = new ArrayList<GroupMessageModel>();
        GetAllMessages();

        parentLayout = (LinearLayout) findViewById(R.id.list_parent);
        emoticonsCover = (LinearLayout) findViewById(R.id.footer_for_emoticons);
        popUpView = getLayoutInflater().inflate(R.layout.emoticons_popup, null);

        // Defining default height of keyboard which is equal to 230 dip
        final float popUpheight = getResources().getDimension(
                R.dimen.keyboard_height);
        changeKeyboardHeight((int) popUpheight);

        // Showing and Dismissing pop up on clicking emoticons button
        ImageView emoticonsButton = (ImageView) findViewById(R.id.img_line_smiley);
        emoticonsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!popupWindow.isShowing()) {

                    popupWindow.setHeight((int) (keyboardHeight));

                    if (isKeyBoardVisible) {
                        emoticonsCover.setVisibility(LinearLayout.GONE);
                    } else {
                        emoticonsCover.setVisibility(LinearLayout.VISIBLE);
                    }
                    popupWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0);

                } else {
                    popupWindow.dismiss();
                }

            }
        });

        readEmoticons();
        enablePopUpView();
        checkKeyboardHeight(parentLayout);

        ImageView galleryView = (ImageView)findViewById(R.id.img_line_gallery);
        galleryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(MessagesActivity.this);
                startActivityForResult(chooseImageIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        ImageView cameraView = (ImageView)findViewById(R.id.img_line_camera);
        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordVideo();
            }
        });

    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
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

    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File videosFolder = new File(Environment.getExternalStorageDirectory(), "VideoToUpload");
        videosFolder.mkdirs();
        File video = new File(videosFolder, "video_001.mp4");
        videoFileUri = Uri.fromFile(video);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoFileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    private void GetAllMessages(){

        String userId = MainTabActivity.userid;
        String lastId = "0";

        if(isGroup) {

            SvcApiService.getUserIdEndPoint().getUserGroupMessages(userId, groupId, lastId, new SvcApiRestCallback<GroupMessageResponse>() {
                @Override
                public void failure(SvcError svcError) {
                    Log.d(TAG, "Failed to Login " + svcError.toString());
                    showAlertMessage("Error", "Invalid request");
                }

                @Override
                public void success(GroupMessageResponse result, Response response) {
                    Log.d(TAG, "Succeed to Login " + result.toString());
                    try {
                        String responseStatus = result.status;

                        Log.d(TAG, "status=" + responseStatus);

                        if (responseStatus.equals("true")) {

                            for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
                                messageList.add(result.data[memberIdx]);
                            }

                            if (messageList.size() > 0) {
                                listView = (ListView) findViewById(R.id.list_messages);
                                adapter = new MessagesAdapter(MessagesActivity.this, messageList);
                                listView.setAdapter(adapter);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlertMessage("Error", "Invalid request");
                    }
                }
            });
        }else{
            SvcApiService.getUserIdEndPoint().getDirectMessages(userId, receiverId, lastId, new SvcApiRestCallback<GroupMessageResponse>() {
                @Override
                public void failure(SvcError svcError) {
                    Log.d(TAG, "Failed to Login " + svcError.toString());
                    showAlertMessage("Error", "Invalid request");
                }

                @Override
                public void success(GroupMessageResponse result, Response response) {
                    Log.d(TAG, "Succeed to Login " + result.toString());
                    try {
                        String responseStatus = result.status;

                        Log.d(TAG, "status=" + responseStatus);

                        if (responseStatus.equals("true")) {
                            for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
                                messageList.add(result.data[memberIdx]);
                            }

                            if (messageList.size() > 0) {
                                listView = (ListView) findViewById(R.id.list_messages);
                                adapter = new MessagesAdapter(MessagesActivity.this, messageList);
                                listView.setAdapter(adapter);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlertMessage("Error", "Invalid request");
                    }
                }
            });
        }
    }

    private void createNewMessage()
    {
        if(isGroup) {

            String message = content.getText().toString();
            content.setText("");

            if (delayLock == true)
                return;

            int useid = Integer.parseInt( MainTabActivity.userid);
            int groupid = Integer.parseInt(groupId);
            String photoString = encodedImageString;
            double latDouble = MainTabActivity.myPosition.latitude;
            double longDouble = MainTabActivity.myPosition.longitude;
            final String sticker = selectedStickName;

            if((photoString == null || photoString.isEmpty())
                    &&(message == null || message.isEmpty())
                    &&(sticker == null || sticker.isEmpty()))
            {
                    Log.d(TAG, "blank fields");
                    return;
            }

            encodedImageString = "";
            selectedStickName = "";

            Log.d(TAG, "useid=" + useid + "replyid=" + groupid + "latDouble=" + latDouble + "longDouble=" + longDouble + "message=" + message);
            delayLock = true;

            SvcApiService.getUserIdEndPoint().createNewMessageForGroup(useid,
                    groupid,
                    message,
                    photoString,
                    sticker,
                    latDouble,
                    longDouble,
                    new SvcApiRestCallback<AddGroupMessageModelResponse>() {
                        @Override
                        public void failure(SvcError svcError) {
                            Log.d(TAG, "Failed to Login " + svcError.toString());
                            delayLock = false;
                            showAlertMessage("Error", "Invalid request");
                        }

                        @Override
                        public void success(AddGroupMessageModelResponse result, Response response) {
                            Log.d(TAG, "Succeed to Server " + result.toString());
                            try {
                                String responseStatus = result.status;

                                Log.d(TAG, "status=" + responseStatus);

                                if (responseStatus.equals("true")) {
                                    messageList.add(result.data);
                                    adapter.notifyDataSetChanged();
                                }

                                delayLock = false;

                            } catch (Exception e) {
                                e.printStackTrace();
                                delayLock = false;
                            }
                        }
                    });
        }else{
            String message = content.getText().toString();
            content.setText("");

            if (delayLock == true)
                return;

            int useid = Integer.parseInt( MainTabActivity.userid);
            int receiverid = Integer.parseInt(receiverId);
            String photoString = encodedImageString;
            double latDouble = MainTabActivity.myPosition.latitude;
            double longDouble = MainTabActivity.myPosition.longitude;
            String sticker = selectedStickName;

            if((photoString == null || encodedImageString.isEmpty())
                    &&(message == null || message.isEmpty())
                    &&(sticker == null || sticker.isEmpty()))
            {
                Log.d(TAG, "blank fields");
                return;
            }

            encodedImageString = "";
            selectedStickName = "";

            Log.d(TAG, "useid=" + useid + "receiverId=" + receiverId + "latDouble=" + latDouble + "longDouble=" + longDouble + "message=" + message);
            delayLock = true;

            SvcApiService.getUserIdEndPoint().createNewMessage(useid,
                    receiverid,
                    message,
                    photoString,
                    sticker,
                    latDouble,
                    longDouble,
                    new SvcApiRestCallback<AddGroupMessageModelResponse>() {
                        @Override
                        public void failure(SvcError svcError) {
                            Log.d(TAG, "Failed to Login " + svcError.toString());
                            delayLock = false;
                            showAlertMessage("Error", "Invalid request");
                        }

                        @Override
                        public void success(AddGroupMessageModelResponse result, Response response) {
                            Log.d(TAG, "Succeed to Server " + result.toString());
                            try {
                                String responseStatus = result.status;

                                Log.d(TAG, "status=" + responseStatus);

                                if (responseStatus.equals("true")) {
                                    messageList.add(result.data);
                                    adapter.notifyDataSetChanged();
                                }
                                delayLock = false;

                            } catch (Exception e) {
                                e.printStackTrace();
                                delayLock = false;
                            }
                        }
                    });
        }
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

    /*For emoji keyboard*/
    /**
     * Reading all emoticons in local cache
     */
    private void readEmoticons () {

        emoticons = new Bitmap[NO_OF_EMOTICONS];
        for (short i = 0; i < NO_OF_EMOTICONS; i++) {
            emoticons[i] = getImage(MapPinControl.stickImageString[i]);
        }
    }

    /**
     * Overriding onKeyDown for dismissing keyboard on key down
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * Checking keyboard height and keyboard visibility
     */
    int previousHeightDiffrence = 0;
    private void checkKeyboardHeight(final View parentLayout) {

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        parentLayout.getWindowVisibleDisplayFrame(r);

                        int screenHeight = parentLayout.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);

                        if (previousHeightDiffrence - heightDifference > 50) {
                            popupWindow.dismiss();
                        }

                        previousHeightDiffrence = heightDifference;
                        if (heightDifference > 100) {

                            isKeyBoardVisible = true;
                            changeKeyboardHeight(heightDifference);

                        } else {

                            isKeyBoardVisible = false;

                        }

                    }
                });
    }

    /**
     * change height of emoticons keyboard according to height of actual
     * keyboard
     *
     * @param height
     *            minimum height by which we can make sure actual keyboard is
     *            open or not
     */
    private void changeKeyboardHeight(int height) {

        if (height > 100) {
            keyboardHeight = height;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, keyboardHeight);
            emoticonsCover.setLayoutParams(params);
        }

    }

    /**
     * Defining all components of emoticons keyboard
     */
    private void enablePopUpView() {

        ViewPager pager = (ViewPager) popUpView.findViewById(R.id.emoticons_pager);
        pager.setOffscreenPageLimit(3);

        ArrayList<String> paths = new ArrayList<String>();

        for (short i = 0; i < NO_OF_EMOTICONS; i++) {
            paths.add(MapPinControl.stickImageString[i]);
        }

        EmoticonsPagerAdapter adapter = new EmoticonsPagerAdapter(MessagesActivity.this, paths, this);
        pager.setAdapter(adapter);

        // Creating a pop window for emoticons keyboard
        popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT,
                (int) keyboardHeight, false);

        TextView backSpace = (TextView) popUpView.findViewById(R.id.back);
        backSpace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                content.dispatchKeyEvent(event);
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                emoticonsCover.setVisibility(LinearLayout.GONE);
            }
        });
    }

    /**
     * For loading smileys from assets
     */
    private Bitmap getImage(String path) {
        Drawable drawable = MapPinControl.getStickImageFromResource(path, getApplicationContext());
        Bitmap temp = ((BitmapDrawable) drawable).getBitmap();
        return temp;
    }

    @Override
    public void keyClickedIndex(final String index) {
        selectedStickName = index.replace("stick_", "");
        createNewMessage();
    }

}
