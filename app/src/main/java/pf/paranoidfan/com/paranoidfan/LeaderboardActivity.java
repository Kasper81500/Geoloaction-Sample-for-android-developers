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
import android.widget.ListView;

import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Model.UserModel;
import pf.paranoidfan.com.paranoidfan.Model.UserResponse;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class LeaderboardActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = LeaderboardActivity.class.getSimpleName();

    ArrayList<UserModel> mUserList;
    ListView listView;
    LeaderboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        mUserList = new ArrayList<UserModel>();
        GetLeaderboard();
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
    public void onClick(final View view){
        int position = (int)view.getTag();

        if((position & 0b1) > 0) {
            String profileId = mUserList.get(position >> 1).getUserId();
            Intent intent = new Intent(LeaderboardActivity.this, ReviewListActivity.class);
            intent.putExtra("PROFILE_ID", profileId);
            this.startActivity(intent);
        }else{
            String profileId = mUserList.get(position >> 1).getUserId();
            Intent intent = new Intent(LeaderboardActivity.this, FavoriteListActivity.class);
            intent.putExtra("PROFILE_ID", profileId);
            this.startActivity(intent);
        }
    }

    private void GetLeaderboard(){
        SvcApiService.getUserIdEndPoint().getLeaderboard(new SvcApiRestCallback<UserResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(UserResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);

                    if (responseStatus.equals("true")) {

                        for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
                            mUserList.add(result.data[memberIdx]);
                        }

                        if(mUserList.size() > 0){
                            listView = (ListView) findViewById(R.id.list_leaderboard);
                            adapter = new LeaderboardAdapter(LeaderboardActivity.this, mUserList);
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
