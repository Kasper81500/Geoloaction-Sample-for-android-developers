package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import pf.paranoidfan.com.paranoidfan.Helper.ImagePicker;
import pf.paranoidfan.com.paranoidfan.Model.CommonRespose;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class GroupSelectionActivity extends AppCompatActivity {

    public static String TAG = GroupSelectionActivity.class.getSimpleName();

    String encodedImageString = "";
    ImageView uploadImage;
    TextView uploadText;
    TextView selectedTeamText;
    EditText groupEditText;

    public static boolean teamSelected = false;
    public static String selectedTeam = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selection);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_home);
        setSupportActionBar(toolbar);

        uploadText = (TextView)findViewById(R.id.txt_uploadimage);

        uploadImage = (ImageView)findViewById(R.id.img_teamprofile);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(GroupSelectionActivity.this);
                startActivityForResult(chooseImageIntent, 0);
            }
        });

        groupEditText = (EditText)findViewById(R.id.edittxt_groupname);
        groupEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    selectedTeamText.setText("");
                }
            }
        });

        TextView groupSelection = (TextView)findViewById(R.id.txt_teamselection);
        groupSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupSelectionActivity.this, SelectTeamListActivity.class);
                startActivity(intent);
            }
        });

        TextView groupSkip = (TextView)findViewById(R.id.txt_groupskip);
        groupSkip.setVisibility(View.GONE);

        selectedTeamText = (TextView)findViewById(R.id.txt_selected_team);

        ImageButton forwardButton = (ImageButton)findViewById(R.id.img_forward);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = MainTabActivity.userid;
                String groupName = groupEditText.getText().toString();
                if(groupName != null && !groupName.isEmpty()){
                    checkGroupName(userId, groupName);
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(teamSelected){
            teamSelected = false;
            selectedTeamText.setText(selectedTeam);

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
        if(requestCode == 0) {
            Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
            byte[] bytes = baos.toByteArray();
            encodedImageString = Base64.encodeToString(bytes, Base64.DEFAULT);

            uploadImage.setImageBitmap(bitmap);
            uploadText.setVisibility(View.GONE);
        }
    }

    private void checkGroupName(String userId, final String groupname){

        SvcApiService.getUserIdEndPoint().checkGroupName(userId, groupname, new SvcApiRestCallback<CommonRespose>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(CommonRespose result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);

                    if (responseStatus.equals("true")) {
                        Intent intent = new Intent(GroupSelectionActivity.this, MeetMeListActivity.class);
                        intent.putExtra("team", selectedTeam);
                        intent.putExtra("photo", encodedImageString);
                        intent.putExtra("groupname", groupname);
                        intent.putExtra("isInvite", true);
                        startActivity(intent);

                        finish();

                        Log.d(TAG, "team:" + selectedTeam);
                        Log.d(TAG, "photo:" + encodedImageString);
                        Log.d(TAG, "groupname:" + groupname);

                    }else{
                        showAlertMessage("Warning", result.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertMessage("Error", "Invalid request");
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

}
