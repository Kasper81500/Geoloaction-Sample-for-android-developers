package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.Model.GroupModel;
import pf.paranoidfan.com.paranoidfan.Model.GroupResponse;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class GroupsActivity extends AppCompatActivity {
    public static String TAG = GroupsActivity.class.getSimpleName();

    public static boolean groupCreated = false;

    ArrayList<GroupModel> mGroupList;
    ListView listView;
    GroupsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        ImageView forwardImage = (ImageView)findViewById(R.id.img_forward);
        forwardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupsActivity.this, GroupSelectionActivity.class);
                startActivity(intent);
            }
        });

        GetMyGroups();
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
        if(groupCreated){
            groupCreated = false;
            GetMyGroups();
        }
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

                        mGroupList = new ArrayList<GroupModel>();

                        for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
                            mGroupList.add(result.data[memberIdx]);
                        }

                        if(mGroupList.size() > 0){
                            listView = (ListView) findViewById(R.id.list_groups);
                            adapter = new GroupsAdapter(GroupsActivity.this, mGroupList);
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
