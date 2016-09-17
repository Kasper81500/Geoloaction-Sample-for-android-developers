package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.Helper.MeetMeType;
import pf.paranoidfan.com.paranoidfan.Model.AddGroupMessageModelResponse;
import pf.paranoidfan.com.paranoidfan.Model.FriendListResponse;
import pf.paranoidfan.com.paranoidfan.Model.MeetMeModel;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class MeetMeListActivity extends AppCompatActivity {
    public static String TAG = MeetMeListActivity.class.getSimpleName();
    MeetMeListAdapter meetMeListAdapter;
    ListView meetmeListView;

    ArrayList<MeetMeModel> groupList;
    ArrayList<MeetMeModel> friendList;
    ArrayList<MeetMeModel> contractList;

    public static ArrayList<MeetMeModel> selectedGroupList;
    public static ArrayList<MeetMeModel> selectedFriendList;
    public static ArrayList<MeetMeModel> selectedContractList;

    boolean isShared = false;
    boolean showGroups = false;
    boolean isGroupInvite = false;

    String groupId = "";
    String meetMeText = "";
    String pinId = "";

    boolean isInvite = false;
    String team = "";
    String photo = "";
    String groupname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_me_list);

        isShared = getIntent().getBooleanExtra("isShare", false);
        pinId = getIntent().getStringExtra("pinId");
        groupList = (ArrayList<MeetMeModel>)getIntent().getSerializableExtra("groups");

        showGroups = getIntent().getBooleanExtra("showGroups", false);
        isGroupInvite = getIntent().getBooleanExtra("isGroupInvite", false);
        groupId = getIntent().getStringExtra("groupId");
        meetMeText = getIntent().getStringExtra("meetMeText");

        isInvite = getIntent().getBooleanExtra("isInvite", false);
        team = getIntent().getStringExtra("team");
        photo = getIntent().getStringExtra("photo");
        groupname = getIntent().getStringExtra("groupname");

        TextView toolbarTitle = (TextView)findViewById(R.id.toolbar_title);

        meetmeListView = (ListView)findViewById(R.id.list_meetme);

        friendList = new ArrayList<MeetMeModel>();
        contractList = new ArrayList<MeetMeModel>();

        if(isShared || showGroups || isInvite || isGroupInvite) {
            selectedGroupList = new ArrayList<MeetMeModel>();
            selectedFriendList = new ArrayList<MeetMeModel>();
            selectedContractList = new ArrayList<MeetMeModel>();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        meetMeListAdapter = new MeetMeListAdapter(this);

        readContacts();
        GetFriendList();

        ImageButton forwardButton = (ImageButton)findViewById(R.id.img_forward);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFanGroup();
            }
        });

        if(isGroupInvite || showGroups) {
            forwardButton.setVisibility(View.GONE);
            toolbarTitle.setText("Invite Friends");
        }else if(isShared){
            forwardButton.setVisibility(View.GONE);
            toolbarTitle.setText("Share with Friends");
        }else if(isInvite){
            toolbarTitle.setText("Invite Friends");
        }else{
            forwardButton.setVisibility(View.GONE);
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

    private void GetFriendList(){
        String userId = MainTabActivity.userid;
        SvcApiService.getUserIdEndPoint().getFriendListForUser(userId, new SvcApiRestCallback<FriendListResponse>() {
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
                        /*add friend list header*/
                        MeetMeModel header = new MeetMeModel(MeetMeType.Friend, "", "", "Paranoid Fans in my address book", "", true, false);
                        friendList.add(header);

                        /*add friend list item*/
                        for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
                            MeetMeModel item = new MeetMeModel(
                                MeetMeType.Friend,
                                result.data[memberIdx].getUserId(),
                                result.data[memberIdx].getProfileAvatar(),
                                result.data[memberIdx].getFullname(),
                                    result.data[memberIdx].getPhone(),
                                false,
                                false);

                            friendList.add(item);
                        }

                        meetMeListAdapter.isGroupInvite = isGroupInvite;
                        meetMeListAdapter.isInvite = isInvite;
                        meetMeListAdapter.isShare = isShared;
                        meetMeListAdapter.showGroups = showGroups;
                        meetMeListAdapter.pinId = pinId;
                        meetMeListAdapter.mMeetMeText = meetMeText;
                        meetMeListAdapter.groupId = groupId;

                        if(groupList != null && groupList.size() > 0)
                            meetMeListAdapter.addItems(groupList);

                        if(friendList != null && friendList.size() > 0)
                            meetMeListAdapter.addItems(friendList);

                        if(contractList != null && contractList.size() > 0)
                            meetMeListAdapter.addItems(contractList);

                        meetmeListView.setAdapter(meetMeListAdapter);
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


    public void readContacts(){
        ContentResolver cr = this.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        MeetMeModel header = new MeetMeModel(MeetMeType.Contact, "", "", "Contacts", "", true, false);
        contractList.add(header);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String phone = "";
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    System.out.println("name : " + name + ", ID : " + id);

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        System.out.println("phone" + phone);

                    }

                    MeetMeModel item = new MeetMeModel(MeetMeType.Contact, "0", "", name, phone, false, false);
                    contractList.add(item);

                    pCur.close();

                    // get email and type
                    Cursor emailCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCur.moveToNext()) {
                        // This would allow you get several email addresses
                        // if the email addresses were stored in an array
                        String email = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        String emailType = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                        System.out.println("Email " + email + " Email Type : " + emailType);
                    }
                    emailCur.close();

                    // Get note.......
                    String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] noteWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                    Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);
                    if (noteCur.moveToFirst()) {
                        String note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                        System.out.println("Note " + note);
                    }
                    noteCur.close();

                    //Get Postal Address....

                    String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] addrWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                    Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, null, null, null);
                    while(addrCur.moveToNext()) {
                        String poBox = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                        String street = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                        String city = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                        String state = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                        String postalCode = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                        String country = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                        String type = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                        // Do something with these....

                    }
                    addrCur.close();

                    // Get Instant Messenger.........
                    String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] imWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                    Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, imWhere, imWhereParams, null);
                    if (imCur.moveToFirst()) {
                        String imName = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                        String imType;
                        imType = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                    }
                    imCur.close();

                    // Get Organizations.........

                    String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] orgWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                    Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, orgWhere, orgWhereParams, null);
                    if (orgCur.moveToFirst()) {
                        String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                        String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                    }
                    orgCur.close();
                }
            }
        }
    }

    boolean delayLock = false;
    private void AddFanGroup(){

        if (delayLock == true)
            return;

        String useid = MainTabActivity.userid;
        final String groupName = groupname;
        String team = this.team;
        String photoString = photo;
        String friends = "";
        for(MeetMeModel item : selectedFriendList){
            friends += item.name + ",";
        }

        String contacts = "";
        for(MeetMeModel item : selectedContractList){
            contacts += item.name + ",";
        }

        Log.d("group name:", groupName);
        Log.d("team:", team);
        Log.d("photoString:", photoString);
        Log.d("friends:", friends);
        Log.d("contacts:", contacts);

        delayLock = true;

        SvcApiService.getUserIdEndPoint().addFanGroup(
                useid,
                groupName,
                team,
                contacts,
                friends,
                photoString,
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(MeetMeListActivity.this);
                                builder.setTitle("Success!");
                                builder.setMessage(String.format("Your group, %s, has been created. And your invites have been sent.", groupName));
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        GroupsActivity.groupCreated = true;
                                        finish();
                                    }
                                });
                                builder.show();
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
