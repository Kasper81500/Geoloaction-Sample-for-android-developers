package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import pf.paranoidfan.com.paranoidfan.Model.CommonRespose;
import pf.paranoidfan.com.paranoidfan.Model.GroupModel;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Model.TeamInfo;
import pf.paranoidfan.com.paranoidfan.Model.UserModel;
import pf.paranoidfan.com.paranoidfan.Model.UserResponse;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class GroupProfileActivity extends AppCompatActivity {

    public static String TAG = GroupProfileActivity.class.getSimpleName();
    public static ImageLoader imageLoader = null;

    GroupModel groupModel;
    ArrayList<UserModel> mUserList;
    Button joinBtn;
    Button leaveButton;
    Button inviteButton;
    Button messageButton;
    TextView memberCountText;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_profile);

        groupModel = (GroupModel)getIntent().getSerializableExtra("GroupModel");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        mUserList = new ArrayList<UserModel>();

        if(imageLoader == null) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(new FadeInBitmapDisplayer(300)).build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    this.getApplicationContext())
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache())
                    .discCacheSize(100 * 1024 * 1024).build();

            ImageLoader.getInstance().init(config);

            imageLoader = ImageLoader.getInstance();
        }

        ImageView teamProfileImage = (ImageView)findViewById(R.id.img_group_profile);

        if(groupModel.getGroupCoverPhoto() != null) {
            imageLoader.displayImage(groupModel.getGroupCoverPhoto(), teamProfileImage);
            int gridHeight = MainTabActivity.phoneWidth;
            teamProfileImage.getLayoutParams().height = gridHeight;
        }

        TextView teamNameText = (TextView)findViewById(R.id.txt_groupname);
        teamNameText.setText(groupModel.getGroupName());

        joinBtn = (Button)findViewById(R.id.btn_join);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMembership("Join");
            }
        });

        leaveButton = (Button)findViewById(R.id.btn_leave);
        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GroupProfileActivity.this);
                builder.setTitle(getText(R.string.group_profile_leave_message_title));
                builder.setMessage(getText(R.string.group_profile_leave_message));
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        GroupMembership("Leave");
                    }
                });

                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

            }
        });

        messageButton = (Button)findViewById(R.id.btn_message);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupProfileActivity.this, GroupChatActivity.class);
                intent.putExtra("groupId", groupModel.getGroupId());
                startActivity(intent);
            }
        });

        inviteButton = (Button)findViewById(R.id.btn_invite);
        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupProfileActivity.this, MeetMeListActivity.class);
                intent.putExtra("isGroupInvite", true);
                intent.putExtra("groupId", groupModel.getGroupId());
                intent.putExtra("meetMeText", "inviting you join " + groupModel.getGroupName() + " fan group.");
                startActivity(intent);
            }
        });

        memberCountText = (TextView)findViewById(R.id.txt_member_count);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_team);

        GetGroupMembers();

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


    private void GetGroupMembers(){
        String groupId = groupModel.getGroupId();
        SvcApiService.getUserIdEndPoint().getGroupMembers(groupId, new SvcApiRestCallback<UserResponse>() {
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

                        List<TeamInfo> teamList = new ArrayList<TeamInfo>();

                        for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
                            mUserList.add(result.data[memberIdx]);
                            TeamInfo team = new TeamInfo(result.data[memberIdx].getProfileAvatar(), result.data[memberIdx].getFullname());
                            teamList.add(team);
                        }

                        if (mUserList.size() > 0) {
                            if (checkContainMe()) {
                                joinBtn.setVisibility(View.GONE);
                                leaveButton.setVisibility(View.VISIBLE);
                            } else {
                                joinBtn.setVisibility(View.VISIBLE);
                                leaveButton.setVisibility(View.GONE);
                            }

                            memberCountText.setText(String.format("%d Member(s)", mUserList.size()));

                            GroupProfileTeamAdapter adapter = new GroupProfileTeamAdapter((Activity)GroupProfileActivity.this, teamList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);


                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertMessage("Error", "Invalid request");
                }
            }
        });
    }

    private void GroupMembership(final String membership){
        String userId = MainTabActivity.userid;
        String groupId = groupModel.getGroupId();

        SvcApiService.getUserIdEndPoint().groupMembership(userId, membership, groupId, new SvcApiRestCallback<CommonRespose>() {
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
                        if(membership.equals("Leave")) {
                            joinBtn.setVisibility(View.VISIBLE);
                            leaveButton.setVisibility(View.GONE);
                        }else if(membership.equals("Join")){
                            joinBtn.setVisibility(View.GONE);
                            leaveButton.setVisibility(View.VISIBLE);
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

    private boolean checkContainMe(){
        String userId = MainTabActivity.userid;
        for(int memberId = 0; memberId < mUserList.size(); memberId++) {
            if (mUserList.get(memberId).getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
}
