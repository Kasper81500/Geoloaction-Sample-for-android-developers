package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.Helper.MapPinControl;
import pf.paranoidfan.com.paranoidfan.Helper.MeetMeType;
import pf.paranoidfan.com.paranoidfan.Model.FriendListResponse;
import pf.paranoidfan.com.paranoidfan.Model.FriendModel;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class FriendListActivity extends AppCompatActivity {
    public static String TAG = FriendListActivity.class.getSimpleName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    TextView contract;
    TextView friend;
    TextView addmeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                controlTabTextColor(1 << position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        contract = (TextView)this.findViewById(R.id.txt_contracts);
        contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupPageByPosiion(0);
            }
        });

        friend = (TextView)this.findViewById(R.id.txt_friends);
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupPageByPosiion(1);
            }
        });

        addmeTextView = (TextView)this.findViewById(R.id.txt_addme);
        addmeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupPageByPosiion(2);
            }
        });

        final ImageView backImage = (ImageView)this.findViewById(R.id.img_back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //default page settle
        setupPageByPosiion(1);
    }

    private void setupPageByPosiion(int position){
        mViewPager.setCurrentItem(position, true);
        controlTabTextColor(1 << position);
    }

    public void controlTabTextColor(int selected){
        contract.setTextColor((selected & (1<<0)) > 0 ? getResources().getColor(R.color.colorSignin):getResources().getColor(R.color.colorLightGrey) );
        friend.setTextColor((selected & (1<<1)) > 0 ? getResources().getColor(R.color.colorSignin):getResources().getColor(R.color.colorLightGrey) );
        addmeTextView.setTextColor((selected & (1<<2)) > 0 ? getResources().getColor(R.color.colorSignin):getResources().getColor(R.color.colorLightGrey) );
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        FriendListActivity mActivity;
        public SectionsPagerAdapter(FragmentManager fm, Activity activity) {
            super(fm);
            mActivity = (FriendListActivity)activity;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Log.d(mActivity.TAG, "page index = " + position);
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        ArrayList<FriendModel> friendList;
        ArrayList<FriendModel> contractList;
        ArrayList<FriendModel> addMeList;
        int SectionNumber;
        ListView friendListView;
        String[] headerlist = {"Invite to Paranoid Fan", "My Friends", "Who's Added Me"};
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_friendlist, container, false);
            SectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

            TextView listheader = (TextView)rootView.findViewById(R.id.list_header);
            listheader.setText(headerlist[SectionNumber]);

            friendListView = (ListView)rootView.findViewById(R.id.list_friend);

            contractList = new ArrayList<FriendModel>();
            friendList = new ArrayList<FriendModel>();
            addMeList = new ArrayList<FriendModel>();

            String userId = MainTabActivity.userid;

            Log.d("PlaceholderFragment", "section number = " + SectionNumber);

            switch (SectionNumber)
            {
                case 0:
                    readContacts();
                    break;
                case 1:
                    GetFriendList(userId);
                case 2:
                    GetAddMeList();
                    break;
            }

            return rootView;
        }

        private void GetFriendList(String userid){
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
                                FriendListAdapter adapter = new FriendListAdapter(getActivity(), friendList, MeetMeType.Friend);
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

        private void GetAddMeList(){

            String phone = MapPinControl.owner.getPhone();

            if(phone == null || phone.isEmpty())
                return;

            SvcApiService.getUserIdEndPoint().getAddedList(phone, new SvcApiRestCallback<FriendListResponse>() {
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
                                addMeList.add(result.data[memberIdx]);
                            }

                            if (addMeList.size() > 0) {
                                FriendListAdapter adapter = new FriendListAdapter(getActivity(), addMeList, MeetMeType.AddedMe);
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

        private void showAlertMessage(String title, String message){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
            ContentResolver cr = getActivity().getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        System.out.println("name : " + name + ", ID : " + id);

                        FriendModel contract = new FriendModel();
                        contract.setFullname(name);

                        // get the phone number
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phone = pCur.getString(
                                    pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            System.out.println("phone" + phone);
                            contract.setPhone(phone);
                        }

                        contractList.add(contract);

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

                if (contractList.size() > 0) {
                    FriendListAdapter adapter = new FriendListAdapter(getActivity(), contractList, MeetMeType.Contact);
                    friendListView.setAdapter(adapter);
                }

            }
        }
    }


}
