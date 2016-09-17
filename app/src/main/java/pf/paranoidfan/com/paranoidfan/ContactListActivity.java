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
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.Model.CommonRespose;
import pf.paranoidfan.com.paranoidfan.Model.FriendListResponse;
import pf.paranoidfan.com.paranoidfan.Model.FriendModel;
import pf.paranoidfan.com.paranoidfan.Model.MeetMeModel;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class ContactListActivity extends AppCompatActivity {

    public static String TAG = ContactListActivity.class.getSimpleName();

    ArrayList<FriendModel> friendList;
    ArrayList<MeetMeModel> selectedFriends;
    ListView friendListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        ImageButton inviteButton = (ImageButton)findViewById(R.id.img_forward);
        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserGroup(selectedFriends);
            }
        });

        friendListView = (ListView)findViewById(R.id.list_friends);
        friendList = new ArrayList<FriendModel>();
        selectedFriends = new ArrayList<MeetMeModel>();
        GetFriendList();

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

    private void GetFriendList(){

        String userid = MainTabActivity.userid;

        SvcApiService.getUserIdEndPoint().getFriendListForUser(userid, new SvcApiRestCallback<FriendListResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(FriendListResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);

                    if (responseStatus.equals("true")) {
                        for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
                            friendList.add(result.data[memberIdx]);
                        }

                        if (friendList.size() > 0) {
                            ContactListAdapter adapter = new ContactListAdapter(ContactListActivity.this, friendList);
                            friendListView.setAdapter(adapter);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertMessage("Error", "Invalid request");
                }
            }
        });
    }

    private void AddUserGroup(final ArrayList<MeetMeModel> friends){

        String userid = MainTabActivity.userid;

        String groupUsers = "";
        for (MeetMeModel item : friends){
            if(item.selected)
                groupUsers += item.pfUserId + ",";
        }

        SvcApiService.getUserIdEndPoint().addUserGroup(userid, groupUsers, new SvcApiRestCallback<CommonRespose>() {
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
                        String groupUserNames = "";
                        for (MeetMeModel item : friends) {
                            if (item.selected)
                                groupUserNames += item.name + ",";
                        }
                        groupUserNames = groupUserNames.replaceAll(",$", "");

                        Intent intent = new Intent(ContactListActivity.this, MessagesActivity.class);
                        intent.putExtra("isGroup", true);
                        intent.putExtra("receiverName", groupUserNames);
                        intent.putExtra("groupId", result.message);
                        startActivity(intent);
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
