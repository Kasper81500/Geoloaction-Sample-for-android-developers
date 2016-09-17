package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pf.paranoidfan.com.paranoidfan.Helper.CustomDateTimePicker;
import pf.paranoidfan.com.paranoidfan.Helper.ImagePicker;
import pf.paranoidfan.com.paranoidfan.Helper.MapPinControl;
import pf.paranoidfan.com.paranoidfan.Helper.MeetMeType;
import pf.paranoidfan.com.paranoidfan.Model.AddMapPinModel;
import pf.paranoidfan.com.paranoidfan.Model.CommonRespose;
import pf.paranoidfan.com.paranoidfan.Model.GroupModel;
import pf.paranoidfan.com.paranoidfan.Model.GroupResponse;
import pf.paranoidfan.com.paranoidfan.Model.MapPinModel;
import pf.paranoidfan.com.paranoidfan.Model.MeetMeModel;
import pf.paranoidfan.com.paranoidfan.Model.PinTypeModel;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class CreatePinFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    public static String TAG = CreatePinFragment.class.getSimpleName();
    public String pinType = "Tailgate";

    String encodedImageString = "";

    ImageView ratingImage1;
    ImageView ratingImage2;
    ImageView ratingImage3;
    ImageView ratingImage4;
    ImageView ratingImage5;

    EditText pinTitleEditText;
    EditText pinTeamEditText;
    EditText pinDetailEditText;

    Button dateButton;

    AutoCompleteTextView mAutocompleteTextView;

    int score;

    boolean facebookButtonSelected = false;
    boolean twitterButtonSelected = false;

    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static boolean googleApiClientCreated = false;

    final int PICK_IMAGE_ID = 1;
    String selectedAddress = "";
    double selectedLatitude = 0;
    double selectedLongitude = 0;

    ImageView pinImage;
    ArrayList<GroupModel> mGroupList;

    ArrayList<MeetMeModel> selectedGroupList;
    ArrayList<MeetMeModel> selectedFriendList;
    ArrayList<MeetMeModel> selectedContractList;

    boolean showGroup = false;

    public static CreatePinFragment newInstance(String pinType) {
        CreatePinFragment fragment = new CreatePinFragment();

        Bundle args = new Bundle();
        args.putString("pinType", pinType);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_createpin, container, false);

        mGroupList = new ArrayList<GroupModel>();

        pinType = getArguments().getString("pinType");

        pinImage = (ImageView)view.findViewById(R.id.img_pin);
        setPinImageByType(pinType);

        final TextView pinTitle = (TextView)view.findViewById(R.id.txt_pintitle);
        pinTitle.setText(pinType);

        selectedGroupList = new ArrayList<MeetMeModel>();
        selectedFriendList = new ArrayList<MeetMeModel>();
        selectedContractList = new ArrayList<MeetMeModel>();

        ratingImage1 = (ImageView)view.findViewById(R.id.rating1);
        ratingImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0b1;
                setupRatingView(score);
                Log.d(TAG, "rated score = " + score);
            }
        });

        ratingImage2 = (ImageView)view.findViewById(R.id.rating2);
        ratingImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0b11;
                setupRatingView(score);
                Log.d(TAG, "rated score = " + score);
            }
        });

        ratingImage3 = (ImageView)view.findViewById(R.id.rating3);
        ratingImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0b111;
                setupRatingView(score);
                Log.d(TAG, "rated score = " + score);
            }
        });

        ratingImage4 = (ImageView)view.findViewById(R.id.rating4);
        ratingImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0b1111;
                setupRatingView(score);
                Log.d(TAG, "rated score = " + score);
            }
        });

        ratingImage5 = (ImageView)view.findViewById(R.id.rating5);
        ratingImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0b11111;
                setupRatingView(score);
                Log.d(TAG, "rated score = " + score);
            }
        });

        pinTitleEditText = (EditText)view.findViewById(R.id.edittxt_pintitle);
        pinTeamEditText = (EditText)view.findViewById(R.id.edittxt_pinteam);
        pinDetailEditText = (EditText)view.findViewById(R.id.edittext_comment);

        dateButton = (Button)view.findViewById(R.id.btn_datatime);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        dateButton.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        final Button groupButton = (Button)view.findViewById(R.id.btn_invite);
        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MeetMeModel> groupList = new ArrayList<MeetMeModel>();

                MeetMeModel header = new MeetMeModel(MeetMeType.Group, "", "", "My Groups", "", true, false);
                groupList.add(header);

                for(GroupModel item: mGroupList){
                    MeetMeModel model = new MeetMeModel(MeetMeType.Group, item.getGroupId(), item.getGroupCoverPhoto(), item.getGroupName(), "Group", false, false);
                    groupList.add(model);
                }

                Intent intent = new Intent(getActivity(), MeetMeListActivity.class);
                intent.putExtra("showGroups", true);
                showGroup = true;

                Bundle groupListBundle = new Bundle();
                groupListBundle.putSerializable("groups", groupList);
                intent.putExtras(groupListBundle);

                startActivity(intent);
            }
        });

        final Button facebookButton = (Button)view.findViewById(R.id.btn_facebook);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookButtonSelected = !facebookButtonSelected;
                if(facebookButtonSelected)
                    facebookButton.setBackgroundResource(R.color.colorSignup);
                else
                    facebookButton.setBackgroundResource(R.color.colorDarkGrey);
            }
        });

        final Button twitterButton = (Button) view.findViewById(R.id.btn_twitter);
        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitterButtonSelected = !twitterButtonSelected;
                if (twitterButtonSelected)
                    twitterButton.setBackgroundResource(R.color.colorSignup);
                else
                    twitterButton.setBackgroundResource(R.color.colorDarkGrey);
            }
        });

        if(googleApiClientCreated == false) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(Places.GEO_DATA_API)
                    .enableAutoManage((MainTabActivity) getActivity(), GOOGLE_API_CLIENT_ID, this)
                    .addConnectionCallbacks(this)
                    .build();
            googleApiClientCreated = true;
        }

        mAutocompleteTextView = (AutoCompleteTextView)view.findViewById(R.id.autotext_location);
        mAutocompleteTextView.setThreshold(3);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        final CustomDateTimePicker custom = new CustomDateTimePicker(getActivity(),
                new CustomDateTimePicker.ICustomDateTimeListener() {

                    @Override
                    public void onSet(Dialog dialog, Calendar calendarSelected,
                                      Date dateSelected, int year, String monthFullName,
                                      String monthShortName, int monthNumber, int date,
                                      String weekDayFullName, String weekDayShortName,
                                      int hour24, int hour12, int min, int sec,
                                      String AM_PM) {
                            dateButton.setText(calendarSelected
                                        .get(Calendar.DAY_OF_MONTH)
                                        + "/" + (monthNumber+1) + "/" + year
                                        + ", " + hour12 + ":" + min
                                        + " " + AM_PM);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
        /**
         * Pass Directly current time format it will return AM and PM if you set
         * false
         */
        custom.set24HourFormat(false);
        /**
         * Pass Directly current data and time to show when it pop up
         */
        custom.setDate(Calendar.getInstance());

        dateButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        custom.showDialog();
                    }
                });

        Button cameraView = (Button)view.findViewById(R.id.btn_camera);
        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
            }
        });

        Button submitButton = (Button)view.findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPinWithRatingForUser();
            }
        });

        GetMyGroups();
        return view;
    }

    private void setPinImageByType(String pinType){
        switch(pinType){
            case PinTypeModel.Tailgate:
                pinImage.setImageResource(R.mipmap.menu_tailgate);
                break;
            case PinTypeModel.Partying:
                pinImage.setImageResource(R.mipmap.menu_partying);
                break;
            case PinTypeModel.GameShowing:
                pinImage.setImageResource(R.mipmap.menu_showing);
                break;
            case PinTypeModel.WatchParty:
                pinImage.setImageResource(R.mipmap.menu_watching);
                break;
            case PinTypeModel.Playing:
                pinImage.setImageResource(R.mipmap.menu_playing);
                break;
            case PinTypeModel.Celebrity:
                pinImage.setImageResource(R.mipmap.menu_celebrity);
                break;
            case PinTypeModel.Music:
                pinImage.setImageResource(R.mipmap.menu_music);
                break;
            case PinTypeModel.Treasure:
                pinImage.setImageResource(R.mipmap.menu_treasure);
                break;
            case PinTypeModel.Meetup:
                pinImage.setImageResource(R.mipmap.menu_meetme);
                break;
            case PinTypeModel.FoodDrink:
                pinImage.setImageResource(R.mipmap.menu_food);
                break;
            case PinTypeModel.MedicalCare:
                pinImage.setImageResource(R.mipmap.menu_medical);
                break;
            case PinTypeModel.Apparel:
                pinImage.setImageResource(R.mipmap.menu_apparel);
                break;
            case PinTypeModel.Police:
                pinImage.setImageResource(R.mipmap.menu_police);
                break;
            case PinTypeModel.Ticket:
                pinImage.setImageResource(R.mipmap.menu_ticket);
                break;
            case PinTypeModel.Parking:
                pinImage.setImageResource(R.mipmap.menu_parking);
                break;
            case PinTypeModel.Beer:
                pinImage.setImageResource(R.mipmap.menu_beer);
                break;
            case PinTypeModel.Taxi:
                pinImage.setImageResource(R.mipmap.menu_taxi);
                break;
            case PinTypeModel.Broadcast:
                pinImage.setImageResource(R.mipmap.menu_boardcast);
                break;
            case PinTypeModel.Rickshaw:
                pinImage.setImageResource(R.mipmap.menu_rickshaw);
                break;
            case PinTypeModel.Restroom:
                pinImage.setImageResource(R.mipmap.menu_restroom);
                break;
            case PinTypeModel.Note:
                pinImage.setImageResource(R.mipmap.menu_note);
                break;
            case PinTypeModel.EntryExit:
                pinImage.setImageResource(R.mipmap.menu_entry);
                break;

        }
    }

    private void setupRatingView(int score){
        Log.d(TAG, "score = " + score);
        ratingImage1.setImageResource(((score & 1<<0 ) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratingImage2.setImageResource(((score & 1<<1) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratingImage3.setImageResource(((score & 1<<2) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratingImage4.setImageResource(((score & 1<<3) > 0) ? R.mipmap.ic_star_fill:R.mipmap.ic_star_empty);
        ratingImage5.setImageResource(((score & 1 << 4) > 0) ? R.mipmap.ic_star_fill : R.mipmap.ic_star_empty);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
            mAutocompleteTextView.setText(place.getName());
            selectedAddress = place.getAddress() + "";
            selectedLatitude = place.getLatLng().latitude;
            selectedLongitude = place.getLatLng().longitude;

            Log.d(TAG, "selected name = " + place.getName());
            Log.d(TAG, "selected address = " + place.getAddress());
            Log.d(TAG, "selectedLatitude = " + selectedLatitude);
            Log.d(TAG, "selectedLatitude = " + selectedLongitude);

        }
    };
    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(getActivity(),
                "Google Places API connection failed with error code:" + connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case PICK_IMAGE_ID:
                Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] bytes = baos.toByteArray();
                encodedImageString = Base64.encodeToString(bytes, Base64.DEFAULT);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
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

    boolean delayLock = false;
    private void AddPinWithRatingForUser(){

        if(delayLock == true)
            return;

        delayLock = true;

        String userId = MainTabActivity.userid;
        String pin = pinType;
        String rating = score + "";
        String address = selectedAddress;

        double latitude = 0;
        double longitude = 0;

        String title = pinTitleEditText.getText().toString();
        if(title.isEmpty()){
            showAlertMessage("Warning", "Title for the pin is required");
            return;
        }

        String detail = pinDetailEditText.getText().toString();

        String location = mAutocompleteTextView.getText().toString();
        if(location.isEmpty()){
            latitude = MainTabActivity.myPosition.latitude;
            longitude = MainTabActivity.myPosition.longitude;
        }else{
            latitude = selectedLatitude;
            longitude = selectedLongitude;
        }

        String photo = encodedImageString;

        String groups = "";
        String receivers = "";

        if(showGroup) {
            if (MeetMeListActivity.selectedGroupList.size() > 0) {
                for (MeetMeModel item : MeetMeListActivity.selectedGroupList) {
                    groups += item.name + ",";
                }
                MeetMeListActivity.selectedGroupList.clear();
            }


            if (MeetMeListActivity.selectedFriendList.size() > 0) {
                for (MeetMeModel item : MeetMeListActivity.selectedFriendList) {
                    receivers += item.name + ",";
                }
                MeetMeListActivity.selectedFriendList.clear();
            }
        }

        String tags = null;
        String dataTime = dateButton.getText().toString();
        String twitterPostId = "";
        String fbPostId = "";

        SvcApiService.getUserIdEndPoint().addPinWithRatingForUser(
                userId,
                pin,
                title,
                rating,
                detail,
                photo,
                groups,
                receivers,
                tags,
                latitude,
                longitude,
                address,
                dataTime,
                twitterPostId,
                fbPostId,
                new SvcApiRestCallback<AddMapPinModel>()
        {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(AddMapPinModel result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);
                    if (responseStatus.equals("true")) {
                        showAlertMessage("Success", result.message);

                        if(showGroup){
                            if(MeetMeListActivity.selectedContractList.size() > 0){
                                SendTextMessageForContact(result.data);
                                MeetMeListActivity.selectedContractList.clear();
                            }
                            showGroup = false;
                        }
                    } else {
                        showAlertMessage("Error", result.message);
                    }


                    delayLock = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }

    private void SendTextMessageForContact(MapPinModel model){

        String meetMeUrl = String.format("http://paranoidfan.com/meetme.php?latittude=%s&longitude=%s&type=%s&pid=%s",
                                        model.getMapPinLatitude(),
                                        model.getMapPinLongitude(),
                                        model.getMapPinType().replace(" ", "_"),
                                        model.getMapPinId());
        String inviteMessage = String.format("Your friend, %s, ", MapPinControl.owner.getFullname());

        switch(model.getMapPinType()){
            case PinTypeModel.Tailgate:
                inviteMessage += "has invited you to a tailgate via the Paranoid Fan app. Grab your stuff and get there now. Details here: ";
                break;
            case PinTypeModel.Partying:
                inviteMessage += "has just dropped a party pin on the Paranoid Fan map and wants you to come hang out! Details here: ";
                break;
            case PinTypeModel.GameShowing:
                inviteMessage += "says they are showing a game at their location and wants you to come watch it. Details here: ";
                break;
            case PinTypeModel.WatchParty:
                inviteMessage += "has invited you to a game watching party using the Paranoid Fan app. Details here: ";
                break;
            case PinTypeModel.Celebrity:
                inviteMessage += "just saw a celebrity at his location using the Paranoid Fan app. Details here: ";
                break;
            case PinTypeModel.Playing:
                inviteMessage += "is playing a game and has invited you to come hang out. Details here: ";
                break;
            case PinTypeModel.Music:
                inviteMessage += "wants you to come listen to music. Details here: ";
                break;
            case PinTypeModel.Meetup:
                inviteMessage += "has invited you to a meetup via the Paranoid Fan app. Details here: ";
                break;
            case PinTypeModel.Treasure:
                inviteMessage =  "Surprises are in order! Your friend, ";
                inviteMessage += String.format("%s just dropped a treasure chest on the Paranoid Fan map. Find out where it’s at. Details here: ", MapPinControl.owner.getFullname());
                break;
            default:
                inviteMessage += String.format("just dropped a %s pin on the Paranoid Fan map. Details here: ", model.getMapPinType());
                break;
        }

        for(MeetMeModel item: MeetMeListActivity.selectedContractList){
            String detailUrl = meetMeUrl + String.format("&un=%s&up=%s", item.name, item.phone);
            String sendMessage = String.format("%s %s ", inviteMessage, detailUrl);

            Log.d(TAG, sendMessage);

            SendTextMessage(item.phone, sendMessage);
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
                        for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
                            mGroupList.add(result.data[memberIdx]);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertMessage("Error", "Invalid request");
                }
            }
        });
    }

    private void SendTextMessage(String phone, String message){

        String userId = MainTabActivity.userid;

        SvcApiService.getUserIdEndPoint().sendTextMessage(userId, phone, message, new SvcApiRestCallback<CommonRespose>() {
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

                    if (responseStatus.equals("true")){
                        Log.d(TAG, "Invite message sent!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertMessage("Error", "Invalid request");
                }
            }
        });
    }
}
