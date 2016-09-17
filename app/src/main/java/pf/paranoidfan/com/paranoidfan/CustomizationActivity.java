package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pf.paranoidfan.com.paranoidfan.Model.CommonRespose;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Model.TeamModel;
import pf.paranoidfan.com.paranoidfan.Model.UserModel;
import pf.paranoidfan.com.paranoidfan.Model.UserModelResponse;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class CustomizationActivity extends AppCompatActivity implements View.OnClickListener {
    public static String TAG = CustomizationActivity.class.getSimpleName();
    public static String selectedTeam;
    public static boolean isSelected = false;

    double fullWidth;
    double curWidth;
    LinearLayout dynamicLayout;
    String[] teamList;
    int rowIndex = 0;
    LinearLayout rowLayout;

    boolean delayLock = false;
    UserModel user;
    List<String> userTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_home);
        setSupportActionBar(toolbar);

        userTags = new ArrayList<String>();

        dynamicLayout = (LinearLayout) findViewById(R.id.dynamic_team_layout);

        TextView searchText = (TextView)findViewById(R.id.search_view);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putStringArray("TeamList", teamList);
                Intent intent = new Intent(CustomizationActivity.this, SearchTeamActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        GetAllTeams();
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
    public void onResume(){
        super.onResume();
        if(isSelected){
            isSelected = false;
            setDynamicTeamLabel(selectedTeam);
            userTags.add(selectedTeam);
            UpdateUserTagsForUser(userTags);
        }
    }

    @Override
    public void onClick(View v) {
        String tagName = v.getTag().toString();
        if(tagName == null || tagName.isEmpty()){
            return;
        }
        userTags.remove(tagName);
        initDynamicLayout();
        for (int tagIdx = 0; tagIdx < userTags.size(); tagIdx++) {
            setDynamicTeamLabel(userTags.get(tagIdx));
        }

        UpdateUserTagsForUser(userTags);
    }
    private void initDynamicLayout(){
        rowIndex = 0;
        curWidth = 0;
        dynamicLayout.removeAllViews();
    }

    private void initDynamicRowLayout(){
        curWidth = 0;
        rowLayout = new LinearLayout(this);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        dynamicLayout.addView(rowLayout);

    }
    private void setDynamicTeamLabel(String team){

        fullWidth  = dynamicLayout.getWidth();

        if ((rowIndex % 2) == 0) {
            initDynamicRowLayout();
        }

        /*Make team item label*/
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout teamLayout = (LinearLayout) inflater.inflate(R.layout.cell_team_item, null);
        TextView dynamicTextView = (TextView)teamLayout.findViewById(R.id.txt_team);
        dynamicTextView.setText("  " + team);
        dynamicTextView.measure(0, 0);       //must call measure!
        double dynamicTextWidth = dynamicTextView.getMeasuredWidth() + 45;

        teamLayout.setTag(team);
        teamLayout.setOnClickListener(CustomizationActivity.this);

        curWidth += dynamicTextWidth;

        rowLayout.addView(teamLayout);

        rowIndex++;
    }

    private void GetAllTeams(){

        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().getAllTeams(new SvcApiRestCallback<TeamModel[]>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(TeamModel[] result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    int arraySize = result.length;
                    if(arraySize > 0) {
                        teamList = new String[arraySize];
                        for (int pinIdx = 0; pinIdx < arraySize; pinIdx++) {
                            teamList[pinIdx] = result[pinIdx].getTeam();
                        }

                        GetUserInfoById();

                        delayLock = false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }

    private void GetUserInfoById(){

        String userId = MainTabActivity.userid;

        SvcApiService.getUserIdEndPoint().getUserInfoByID(userId, new SvcApiRestCallback<UserModelResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(UserModelResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);

                    if (responseStatus.equals("true")) {
                        user = result.data;
                        String[] tagsList = user.getTeamTags().toString().split(",");
                        if (tagsList.length > 0) {
                            for (int tagIdx = 0; tagIdx < tagsList.length; tagIdx++) {
                                userTags.add(tagsList[tagIdx]);
                                setDynamicTeamLabel(tagsList[tagIdx]);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertMessage("Error", "Invalid request");
                }
            }
        });
    }

    private void UpdateUserTagsForUser(List<String> userTags){

        String userId = MainTabActivity.userid;
        String totalTags = "";
        for(int tagIdx = 0; tagIdx < userTags.size(); tagIdx++){
            totalTags += userTags.get(tagIdx) + ",";
        }

        SvcApiService.getUserIdEndPoint().updateUserTagsForUser(userId, totalTags, new SvcApiRestCallback<CommonRespose>() {
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

                    if (!responseStatus.equals("true")) {
                        showAlertMessage("Error", result.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertMessage("Error", "Invalid request");
                }
            }
        });
    }

    private void showAlertMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomizationActivity.this);
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
