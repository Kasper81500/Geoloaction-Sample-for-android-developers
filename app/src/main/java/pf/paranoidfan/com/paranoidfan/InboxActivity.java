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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.Model.InboxModel;
import pf.paranoidfan.com.paranoidfan.Model.InboxResponse;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class InboxActivity extends AppCompatActivity {

    public static String TAG = InboxActivity.class.getSimpleName();

    ArrayList<InboxModel> mInboxList;
    ListView listView;
    InboxAdapter adapter;
    boolean delayLock = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        ImageButton contactButton = (ImageButton)findViewById(R.id.img_inbox);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InboxActivity.this, ContactListActivity.class);
                startActivity(intent);
            }
        });

        mInboxList = new ArrayList<InboxModel>();
        getInboxMessages();
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

    private void getInboxMessages(){
        
        String userId = MainTabActivity.userid;
        
        SvcApiService.getUserIdEndPoint().getInbox(userId, new SvcApiRestCallback<InboxResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(InboxResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);

                    if (responseStatus.equals("true")) {

                        for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
                            mInboxList.add(result.data[memberIdx]);
                        }

                        if(mInboxList.size() > 0){
                            listView = (ListView) findViewById(R.id.list_inbox);
                            adapter = new InboxAdapter(InboxActivity.this, mInboxList);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    InboxModel selectedInboxItem = mInboxList.get(position);
                                    if(selectedInboxItem.getMessage().equals("Meetme request")){
                                        try {
                                            MainTabActivity.meetMeRequest = true;
                                            MainTabActivity.newPosition = new LatLng(Double.parseDouble(selectedInboxItem.getLatitude()), Double.parseDouble(selectedInboxItem.getLongitude()));
                                            finish();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }else{
                                        int userGroupId = 0;
                                        if(selectedInboxItem.getUserGroupId() != null && !selectedInboxItem.getUserGroupId().isEmpty()){
                                            userGroupId = Integer.parseInt(selectedInboxItem.getUserGroupId());
                                        }

                                        if(userGroupId > 0){
                                            Log.d(TAG, "Enter User Group Message Page");
                                            Intent intent = new Intent(InboxActivity.this, MessagesActivity.class);
                                            intent.putExtra("receiverName", selectedInboxItem.getFullname());
                                            intent.putExtra("isGroup", true);
                                            intent.putExtra("groupId", selectedInboxItem.getUserGroupId());
                                            startActivity(intent);
                                        }else{
                                            if(selectedInboxItem.getGroupId() == 0){
                                                Log.d(TAG, "Enter Common Message Page");
                                                Intent intent = new Intent(InboxActivity.this, MessagesActivity.class);
                                                intent.putExtra("receiverId", selectedInboxItem.getReceiverId());
                                                intent.putExtra("receiverName",selectedInboxItem.getFullname());
                                                startActivity(intent);
                                            }else{
                                                Log.d(TAG, "Enter Group Chat Page");
                                                Intent intent = new Intent(InboxActivity.this, GroupChatActivity.class);
                                                intent.putExtra("groupId", "" + (selectedInboxItem.getGroupId()));
                                                startActivity(intent);
                                            }
                                        }

                                    }
                                }
                            });
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
