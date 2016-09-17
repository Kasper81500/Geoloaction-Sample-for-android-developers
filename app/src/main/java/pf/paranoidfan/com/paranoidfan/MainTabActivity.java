package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import pf.paranoidfan.com.paranoidfan.Helper.ActionType;
import pf.paranoidfan.com.paranoidfan.Helper.MapPinControl;
import pf.paranoidfan.com.paranoidfan.Model.CityModel;
import pf.paranoidfan.com.paranoidfan.Model.CityResponse;
import pf.paranoidfan.com.paranoidfan.Model.MapPinModel;
import pf.paranoidfan.com.paranoidfan.Model.MapPinResponse;
import pf.paranoidfan.com.paranoidfan.Model.MapUserModel;
import pf.paranoidfan.com.paranoidfan.Model.MapUserResponse;
import pf.paranoidfan.com.paranoidfan.Model.PinTypeModel;
import pf.paranoidfan.com.paranoidfan.Model.SearchItem;
import pf.paranoidfan.com.paranoidfan.Model.StadiumModel;
import pf.paranoidfan.com.paranoidfan.Model.StadiumResponse;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Model.TeamModel;
import pf.paranoidfan.com.paranoidfan.Model.UserModelResponse;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class MainTabActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    public static String TAG = MainTabActivity.class.getSimpleName();
    public static LatLng myPosition;
    public static String userid;

    public static boolean meetMeRequest = false;
    public static LatLng newPosition;

    LatLngBounds bounds;

    GeomagneticField geoField;
    LocationListener locationListener;
    ArrayList<MapPinModel> allPins;
    ArrayList<CityModel> allCityList;
    ArrayList<StadiumModel> allStadiumList;
    ArrayList<TeamModel> allTeamList;
    ArrayList<TeamModel> allBarList;
    ArrayList<TeamModel> allSocialList;
    ArrayList<TeamModel> allFanList;
    ArrayList<MapUserModel> allUserList;

    ArrayList<SearchItem> allSearchList;

    Boolean delayLock = false;

    float defaultZoomLevel = 6.0f;
    float myLocationZoomLevel = 6.0f;
    float policeZoomLevel = 15.0f;
    float rickshawZoomLevel = 13.0f;
    float beerZoomLevel = 8.0f;

    int mapPinBeerCount = 0;
    int mapUserCount = 0;

    ArrayList<String> PolicePinTypeArray;
    ArrayList<String> RickshawPinTypeArray;
    ArrayList<String> BeerPinTypeArray;

    boolean mapDataDownloaded = false;

    String kSnippetCurrentUser = "CURRENT_USER";
    String kSnippetUser = "USER";
    String kSnippetStadium = "STADIUM";
    String kSnippetCity = "CITY";
    String kSnippetPin = "PIN";
    String kSnippetTicket = "TICKET";

    public static int phoneWidth = 0;
    private static int GOOGLE_API_CLIENT_ID = 0;

    private Map<Marker, MapPinModel> allMarkersMap = new HashMap<Marker, MapPinModel>();
    private Map<Marker, StadiumModel> allStadiumsMap = new HashMap<Marker, StadiumModel>();
    private Map<Marker, MapUserModel> allUsersMap = new HashMap<Marker, MapUserModel>();

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private ArrayList<Fragment> fragmentList;

    private ProgressDialog progress;

    Button userScore;

    SupportMapFragment mapFragment;

    AutoCompleteTextView searchText;
    Boolean searchBeerFlag = false;
    Boolean searchMapPinFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        fragmentList = new ArrayList<Fragment>();

        allPins = new ArrayList<MapPinModel>();
        allStadiumList = new ArrayList<StadiumModel>();
        allUserList = new ArrayList<MapUserModel>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageView socialButton = (ImageView)findViewById(R.id.btn_bottom_social);
        socialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(new SocialFragment(), "social");
            }
        });

        ImageView venueButton = (ImageView)findViewById(R.id.btn_bottom_venue);
        venueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(new VenueFragment(), "venue");
            }
        });

        ImageView connectButton = (ImageView)findViewById(R.id.btn_bottom_connect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(new ConnectFragment(), "connect");
            }
        });

        ImageView chatButton = (ImageView)findViewById(R.id.btn_bottom_chat);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (fragmentList.size() > 0){
                    closeFragment();
                }
                Intent intent = new Intent(MainTabActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        ImageView settingButton = (ImageView)findViewById(R.id.btn_bottom_setting);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (fragmentList.size() > 0){
                    closeFragment();
                }
                Intent intent = new Intent(MainTabActivity.this, SettingsActivity.class);
                startActivity(intent);

            }
        });

        ImageView geoCenter = (ImageView)findViewById(R.id.btn_geocenter);
        geoCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    if (location != null) {
                        myPosition = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, myLocationZoomLevel));
                    } else {
                        myPosition = new LatLng(32.712679f, -96.686155f);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, myLocationZoomLevel));
                    }
                } catch (SecurityException se) {
                    se.printStackTrace();
                }
            }
        });

        ImageView zoomIn = (ImageView)findViewById(R.id.btn_mapplus);
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn(), 500, null);

                Log.d(TAG, "Mapp Zoom in");
            }
        });

        ImageView zoomOut = (ImageView)findViewById(R.id.btn_mapminus);
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut(), 500, null);
                Log.d(TAG, "Mapp Zoom out");
            }
        });

        ImageView inbox = (ImageView)findViewById(R.id.btn_inbox);
        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainTabActivity.this, InboxActivity.class);
                startActivity(intent);
            }
        });

        Button inboxCount = (Button)findViewById(R.id.btn_inboxcount);
        inboxCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainTabActivity.this, EventListActivity.class);
                startActivity(intent);
            }
        });

        ImageView heartImage = (ImageView)findViewById(R.id.btn_heart);
        heartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainTabActivity.this, FavoriteListActivity.class);
                startActivity(intent);
            }
        });

        userScore = (Button)findViewById(R.id.btn_user_score);
        userScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainTabActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        initPinTypeArray();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userid = sharedPreferences.getString("USERID", "DEFAULT");

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        if (size.x < size.y)
            phoneWidth = size.x;
        else
            phoneWidth = size.y;

        GetUserInfoById();

        searchText = (AutoCompleteTextView)findViewById(R.id.search_view);
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(meetMeRequest){
            meetMeRequest = false;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newPosition, myLocationZoomLevel));
        }
    }

    private void initPinTypeArray(){
        PolicePinTypeArray = new ArrayList<String>();
        PolicePinTypeArray.add("Police");
        PolicePinTypeArray.add("Ticket");
        PolicePinTypeArray.add("Parking");
        PolicePinTypeArray.add("Restroom");
        PolicePinTypeArray.add("Food & Drinks");
        PolicePinTypeArray.add("Apparel");
        PolicePinTypeArray.add("Entry Exit");

        RickshawPinTypeArray = new ArrayList<String>();
        RickshawPinTypeArray.add("Rickshaw");
        RickshawPinTypeArray.add("Taxi");
        RickshawPinTypeArray.add("Medical Care");
        RickshawPinTypeArray.add("Meetup");
        RickshawPinTypeArray.add("Playing");

        BeerPinTypeArray = new ArrayList<String>();
        BeerPinTypeArray.add("Beer");
        BeerPinTypeArray.add("Tailgate");
        BeerPinTypeArray.add("Partying");
        BeerPinTypeArray.add("Game Showing");
        BeerPinTypeArray.add("Watch Party");
        BeerPinTypeArray.add("Celebrity");
        BeerPinTypeArray.add("Music");
        BeerPinTypeArray.add("Treasure");
        BeerPinTypeArray.add("Broadcast");
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Getting Current Location
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if(location != null) {
                myPosition = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, myLocationZoomLevel));
                mMap.addMarker(new MarkerOptions().position(myPosition));
            }else{
                myPosition = new LatLng(32.712679f, -96.686155f);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, myLocationZoomLevel));
                mMap.addMarker(new MarkerOptions().position(myPosition));
            }

            GetAllPinsWithLocation(userid, (float) myPosition.latitude, (float) myPosition.longitude);

            Log.d(TAG, "go to the my location a = " + myPosition.latitude + " b = " + myPosition.longitude);
            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    Log.d(TAG, "location changed a = " + cameraPosition.target.latitude + " b = " + cameraPosition.target.longitude);

                    bounds = mMap.getProjection().getVisibleRegion().latLngBounds;

                    /*When empty search textview, disable search status*/
                    if (searchText.getText().toString().isEmpty()) {
                        searchMapPinFlag = false;
                        searchBeerFlag = false;
                    }

                    if (!mapDataDownloaded) {
                        GetAllPinsWithLocation(userid, (float) cameraPosition.target.latitude, (float) cameraPosition.target.longitude);
                    }

                    if (!searchMapPinFlag) {
                        removeMapPinsByBound();
                        removeMapStadiumsByBound();
                        removeMapUsersByBound();

                        /*Put all pins onto the Map*/
                        for (int pinIdx = 0; pinIdx < allPins.size(); pinIdx++) {
                            double pinLatitude = Double.parseDouble(allPins.get(pinIdx).getMapPinLatitude());
                            double pinLongitude = Double.parseDouble(allPins.get(pinIdx).getMapPinLongitude());
                            LatLng pinLocation = new LatLng(pinLatitude, pinLongitude);

                            if (bounds.contains(pinLocation)) {
                                // Adding marker icon
                                makeMapPin(allPins.get(pinIdx), pinLocation);
                            }
                        }

                        /*Put all stadiums onto the Map*/
                        for (int pinIdx = 0; pinIdx < allStadiumList.size(); pinIdx++) {
                            double pinLatitude = Double.parseDouble(allStadiumList.get(pinIdx).getStadiumLatitude());
                            double pinLongitude = Double.parseDouble(allStadiumList.get(pinIdx).getStadiumLongitude());
                            LatLng pinLocation = new LatLng(pinLatitude, pinLongitude);

                            if (bounds.contains(pinLocation)) {
                                makeMapStadium(allStadiumList.get(pinIdx), pinLocation);
                            }
                        }

                        /*Put all users onto the Map*/
                        for (int pinIdx = 0; pinIdx < allUserList.size(); pinIdx++) {

                            double pinLatitude = 0;
                            double pinLongitude = 0;

                            if (allUserList.get(pinIdx).getCurrentLatitude() != null
                                    && !allUserList.get(pinIdx).getCurrentLatitude().isEmpty()
                                    && allUserList.get(pinIdx).getCurrentLongitude() != null
                                    && !allUserList.get(pinIdx).getCurrentLongitude().isEmpty()) {
                                try {
                                    pinLatitude = Double.parseDouble(allUserList.get(pinIdx).getCurrentLatitude());
                                    pinLongitude = Double.parseDouble(allUserList.get(pinIdx).getCurrentLongitude());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    pinLatitude = 0;
                                    pinLongitude = 0;
                                }
                            }

                            LatLng pinLocation = new LatLng(pinLatitude, pinLongitude);

                            if (bounds.contains(pinLocation)) {
                                makeMapUsers(allUserList.get(pinIdx), pinLocation);
                            }
                        }
                    }
                }
            });

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.getSnippet().equals(kSnippetPin)) {
                            MapPinModel pinModel = allMarkersMap.get(marker);
                            PinDetailFragment pinDetail = new PinDetailFragment();
                            pinDetail.setPinModel(pinModel);
                            goToFragment(pinDetail, "pinDetail");
                        }else if(marker.getSnippet().equals(kSnippetStadium)){
                            StadiumModel stadiumModel = allStadiumsMap.get(marker);
                            Intent intent = new Intent(MainTabActivity.this, ChatActivity.class);
                            intent.putExtra("Stadium", stadiumModel);
                            startActivity(intent);
                        }
                        return true;
                    }
                });

        }catch (SecurityException se){
            se.printStackTrace();
        }
    }

    private void removeMapPinsByBound(){
        float zoom = mMap.getCameraPosition().zoom;

        Map<Marker, MapPinModel> mapClone = new HashMap<Marker,MapPinModel>(allMarkersMap);
        Iterator iterator = mapClone.keySet().iterator();

        while(iterator.hasNext()) {
            Marker marker =(Marker)iterator.next();
            MapPinModel model = mapClone.get(marker);
            String pinType = model.getMapPinType();

            if(!bounds.contains(marker.getPosition())) {
                if(pinType.equals(PinTypeModel.Beer)){
                    if(mapPinBeerCount > 0) {
                        mapPinBeerCount--;
                        Log.d(TAG, "remove beer pin =" + marker.getSnippet());
                    }
                }
                allMarkersMap.remove(marker);
                marker.remove();
            }else if(searchStringFromList(pinType, PolicePinTypeArray) && zoom < policeZoomLevel)
            {
                allMarkersMap.remove(marker);
                marker.remove();
            }
             else if(searchStringFromList(pinType, RickshawPinTypeArray) && zoom < rickshawZoomLevel)
            {
                allMarkersMap.remove(marker);
                marker.remove();
            }
            else if(searchStringFromList(pinType, BeerPinTypeArray) && zoom < beerZoomLevel)
            {
                allMarkersMap.remove(marker);
                marker.remove();
            }
        }
    }

    private void removeMapStadiumsByBound(){
        float zoom = mMap.getCameraPosition().zoom;
        Map<Marker, StadiumModel> mapClone = new HashMap<Marker,StadiumModel>(allStadiumsMap);
        Iterator iterator = mapClone.keySet().iterator();
        while(iterator.hasNext()) {
            Marker marker =(Marker)iterator.next();
            if(!bounds.contains(marker.getPosition())) {
                allStadiumsMap.remove(marker);
                marker.remove();
            }else if(zoom < defaultZoomLevel){
                allStadiumsMap.remove(marker);
                marker.remove();
            }
        }
    }

    private void removeMapUsersByBound(){
        float zoom = mMap.getCameraPosition().zoom;
        Map<Marker, MapUserModel> mapClone = new HashMap<Marker,MapUserModel>(allUsersMap);
        Iterator iterator = mapClone.keySet().iterator();
        while(iterator.hasNext()) {
            Marker marker =(Marker)iterator.next();
            if(!bounds.contains(marker.getPosition())) {
                allUsersMap.remove(marker);
                marker.remove();
                if(mapUserCount > 0) {
                    Log.d(TAG, "remove user pin =" + marker.getTitle());
                    mapUserCount--;
                }
            }else if(zoom < defaultZoomLevel){
                allUsersMap.remove(marker);
                marker.remove();
            }
        }
    }

    private void updateMapObjectWithSearchPins(String team, String pinType){
        mMap.clear();
        allMarkersMap.clear();
        allStadiumsMap.clear();
        allUsersMap.clear();
        mapPinBeerCount = 0;
        mapUserCount = 0;

        searchMapPinFlag = true;
        if(pinType.equals(PinTypeModel.Beer)){
            searchBeerFlag = true;
        }

        for (int pinIdx = 0; pinIdx < allPins.size(); pinIdx++) {

            SearchItem searchItem = allPins.get(pinIdx).getSearchItem();

            if (searchItem.pinType.equals(pinType)) {

                String searchString = String.format("%s %s", searchItem.title, searchItem.tags);

                if(searchString.toLowerCase().contains(team.toLowerCase())){
                    Log.d(TAG, "team search = " + searchItem.title);
                    makeMapPin(allPins.get(pinIdx), searchItem.location);
                }
            }
        }
    }

    private void GetAllPinsWithLocation(final String userid, float latitude, float longitude){

        if(delayLock == true)
            return;

        delayLock = true;

        progress = ProgressDialog.show(this, "Please wait", getResources().getString(R.string.DownloadingAllMapInfo),
                true, false);

        SvcApiService.getUserIdEndPoint().getAllPinsForLocation(latitude, longitude, userid, new SvcApiRestCallback<MapPinResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(MapPinResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;
                    String responseMessage = result.message;

                    Log.d(TAG, "status=" + responseStatus);
                    Log.d(TAG, "message=" + responseMessage);
                    Log.d(TAG, "data=" + result.data.toString());

                    if (responseStatus.equals("true")) {
                        for (int pinIdx = 0; pinIdx < result.data.length; pinIdx++) {
                            allPins.add(result.data[pinIdx]);
                        }
                        mapDataDownloaded = true;
                    }

                    delayLock = false;
                    GetAllUsers();

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }

    private void GetAllUsers(){

        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().getAllUsers(userid, new SvcApiRestCallback<MapUserResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                showAlertMessage("Error", "Invalid request");
                delayLock = false;
                progress.dismiss();
            }

            @Override
            public void success(MapUserResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;
                    String responseMessage = result.message;

                    Log.d(TAG, "status=" + responseStatus);
                    Log.d(TAG, "message=" + responseMessage);
                    Log.d(TAG, "data=" + result.data.toString());

                    if (responseStatus.equals("true")) {

                        for (int pinIdx = 0; pinIdx < result.data.length; pinIdx++) {
                            allUserList.add(result.data[pinIdx]);
                        }
                    }
                    delayLock = false;
                    GetAllCities();

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                    progress.dismiss();
                }
            }
        });
    }

    private void GetAllCities(){

        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().getAllCities(new SvcApiRestCallback<CityResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                showAlertMessage("Error", "Invalid request");
                delayLock = false;
                progress.dismiss();
            }

            @Override
            public void success(CityResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);
                    Log.d(TAG, "data=" + result.data.toString());

                    if (responseStatus.equals("true")) {
                        allSearchList = new ArrayList<SearchItem>();
                        allCityList = new ArrayList<CityModel>();
                        for (int cityIdx = 0; cityIdx < result.data.length; cityIdx++) {
                            allCityList.add(result.data[cityIdx]);
                            allSearchList.add(result.data[cityIdx].getSearchItem());
                        }
                    }
                    delayLock = false;
                    GetAllStadium();

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                    progress.dismiss();
                }
            }
        });
    }

    private void GetAllStadium(){

        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().getAllStadium(myPosition.latitude, myPosition.longitude, new SvcApiRestCallback<StadiumResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                showAlertMessage("Error", "Invalid request");
                delayLock = false;
                progress.dismiss();
            }

            @Override
            public void success(StadiumResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);
                    Log.d(TAG, "data=" + result.data.toString());

                    if (responseStatus.equals("true")) {
                        for (int idx = 0; idx < result.data.length; idx++) {
                            allStadiumList.add(result.data[idx]);
                            allSearchList.add(result.data[idx].getSearchItem());
                        }
                    }
                    delayLock = false;
                    GetAllTeams();

                } catch (Exception e) {
                    progress.dismiss();
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
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
                progress.dismiss();
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(TeamModel[] result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    allTeamList = new ArrayList<TeamModel>();
                    allBarList = new ArrayList<TeamModel>();
                    allSocialList = new ArrayList<TeamModel>();
                    allFanList = new ArrayList<TeamModel>();

                    for (int idx = 0; idx < result.length; idx++) {
                        allTeamList.add(result[idx]);
                        allBarList.add(result[idx]);
                        allSocialList.add(result[idx]);
                        allFanList.add(result[idx]);
                    }

                    for (int idx = 0; idx < result.length; idx++) {
                        SearchItem item = result[idx].getSearchItem();
                        item.pinType = "Beer";
                        allSearchList.add(item);
                    }

                    for (int idx = 0; idx < result.length; idx++) {
                        SearchItem item = result[idx].getSearchItem();
                        item.pinType = "Watch Party";
                        allSearchList.add(item);
                    }

                    for (int idx = 0; idx < result.length; idx++) {
                        SearchItem item = result[idx].getSearchItem();
                        item.pinType = "Fan";
                        allSearchList.add(item);
                    }

                    if (allSearchList.size() > 0) {
                        MainTabSearchAdapter adapter = new MainTabSearchAdapter(MainTabActivity.this, R.layout.cell_dropdown_list_item, allSearchList);
                        searchText.setAdapter(adapter);
                        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                SearchItem item = (SearchItem) parent.getItemAtPosition(position);
                                Log.d(TAG, "selected search item = " + item.title);
                                if (item.location != null) {
                                    float zoomLevel = myLocationZoomLevel;

                                    if (item.pinType.equals("Stadium")) {
                                        zoomLevel = 16;
                                        searchMapPinFlag = false;
                                        searchBeerFlag = false;

                                    } else if (item.pinType.equals("City")) {
                                        zoomLevel = 13;
                                    }

                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(item.location, zoomLevel));

                                } else {
                                    if (item.pinType.equals("Beer")) {
                                        searchText.setText(String.format("%s Bars", item.title));
                                        updateMapObjectWithSearchPins(item.title, item.pinType);
                                    } else if (item.pinType.equals("Watch Party")) {
                                        searchText.setText(String.format("%s Watch Party", item.title));
                                        updateMapObjectWithSearchPins(item.title, item.pinType);
                                    } else if (item.pinType.equals("Fan")) {
                                        searchText.setText(String.format("%s Fans", item.title));
                                        updateMapObjectWithSearchPins(item.title, item.pinType);
                                    }
                                }

                                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

                            }
                        });
                    }

                    delayLock = false;
                    progress.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                    progress.dismiss();
                    delayLock = false;
                }
            }
        });
    }

    private void makeMapStadium(StadiumModel stadiumModel, LatLng latng){
        float zoom = mMap.getCameraPosition().zoom;
        if(zoom < defaultZoomLevel)
            return;

        if(allStadiumsMap.containsValue(stadiumModel)){
            Log.d(TAG, "already contained stadium =" + stadiumModel.getStadiumName());
            return;
        }

        MarkerOptions markerOptions = new MarkerOptions().position(latng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_venue));
        Marker marker = mMap.addMarker(markerOptions);
        marker.setSnippet(kSnippetStadium);
        allStadiumsMap.put(marker, stadiumModel);
    }

    private void makeMapUsers(final MapUserModel userModel, final LatLng latng){
        float zoom = mMap.getCameraPosition().zoom;
        if(zoom < defaultZoomLevel)
            return;

        if(mapUserCount >= 7){
            return;
        }

        if(allUsersMap.containsValue(userModel)){
            Log.d(TAG, "already contained map user =" + userModel.getFullname());
            return;
        }

        try {
            Bitmap bmImg = Ion.with(this)
                    .load(userModel.getProfileAvatar()).asBitmap().get();
            Bitmap optimized = Bitmap.createScaledBitmap(bmImg, 50, 50, true);

            MarkerOptions markerOptions = new MarkerOptions().position(latng);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(optimized));
            Marker marker = mMap.addMarker(markerOptions);
            marker.setSnippet(kSnippetUser);
            allUsersMap.put(marker, userModel);

            mapUserCount ++;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void makeMapPin(MapPinModel pinModel, LatLng latng){

        String pinType = pinModel.getMapPinType();
        String snippetType = kSnippetPin;
        MarkerOptions markerOptions = new MarkerOptions().position(latng);

        if(searchMapPinFlag){
            setMapPinForType(pinType, snippetType, pinModel, markerOptions);
        }
        else
        {
            float zoom = mMap.getCameraPosition().zoom;
            if (zoom < defaultZoomLevel)
                return;

            if (searchStringFromList(pinType, PolicePinTypeArray) && zoom > policeZoomLevel) {
                setMapPinForType(pinType, snippetType, pinModel, markerOptions);
            }

            if (searchStringFromList(pinType, RickshawPinTypeArray) && zoom > rickshawZoomLevel) {
                setMapPinForType(pinType, snippetType, pinModel, markerOptions);
            }

            if (searchStringFromList(pinType, BeerPinTypeArray) && zoom > beerZoomLevel) {
                setMapPinForType(pinType, snippetType, pinModel, markerOptions);
            }
        }
    }

    private boolean searchStringFromList(String searchString, ArrayList<String> list){
        for(String item : list){
            if(item.equals(searchString))
                return true;
        }
        return false;
    }

    private void setMapPinForType(String pinType, String snippetType, MapPinModel pinModel, MarkerOptions markerOptions){
        if(allMarkersMap.containsValue(pinModel)){
            Log.d(TAG, "already contained map pin type= " + pinType);
            return;
        }
        switch (pinType){
            case PinTypeModel.Apparel:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_apparel));
                break;
            case PinTypeModel.EntryExit:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_entry));
                break;
            case PinTypeModel.Restroom:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_restroom));
                break;
            case PinTypeModel.Rickshaw:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_rickshaw));
                break;
            case PinTypeModel.Beer:
                if(!searchBeerFlag) {
                    if(mapPinBeerCount >= 10)
                        return;
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_beer));
                }else{
                    MapPinControl.setBeerIconByTag(pinModel.getMapPinTags(), markerOptions);
                }
                mapPinBeerCount++;
                break;
            case PinTypeModel.Broadcast:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_broadcast));
                break;
            case PinTypeModel.Celebrity:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_celebrity));
                break;
            case PinTypeModel.FoodDrink:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_food));
                break;
            case PinTypeModel.GameShowing:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_showing));
                break;
            case PinTypeModel.MedicalCare:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_medical));
                break;
            case PinTypeModel.Meetup:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_meetme));
                break;
            case PinTypeModel.Music:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_music));
                break;
            case PinTypeModel.Note:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_note));
                break;
            case PinTypeModel.Parking:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_parking));
                break;
            case PinTypeModel.Partying:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_partying));
                break;
            case PinTypeModel.Playing:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_playing));
                break;
            case PinTypeModel.Tailgate:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_tailgate));
                break;
            case PinTypeModel.Taxi:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_taxi));
                break;
            case PinTypeModel.Ticket:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_ticket));
                break;
            case PinTypeModel.Treasure:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_treasure));
                break;
            case PinTypeModel.WatchParty:
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_watching));
                break;
            default:
                return;
        }

        Marker marker = mMap.addMarker(markerOptions);
        marker.setSnippet(snippetType);
        allMarkersMap.put(marker, pinModel);

        return;
    }


    private void GetUserInfoById(){

        SvcApiService.getUserIdEndPoint().getUserInfoByID(userid, new SvcApiRestCallback<UserModelResponse>() {
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
                        MapPinControl.owner = result.data;
                        userScore.setText(MapPinControl.owner.getProfilePoints());
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

    private void closeFragment(){
        mapFragment.getView().setClickable(true);
        Fragment fragment = fragmentList.get(fragmentList.size() - 1);
        getFragmentManager().beginTransaction().remove(fragment).commit();
        fragmentList.remove(fragment);
    }

    public void addFragment(Fragment newFragment, String fragType){
        getFragmentManager().beginTransaction().add(R.id.main_fragment, newFragment, fragType).commit();
        fragmentList.add(newFragment);
    }

    public void goToFragment(Fragment newFragment, String fragType){

        while (fragmentList.size() > 0){
            closeFragment();
        }

        mapFragment.getView().setClickable(false);

        getFragmentManager().beginTransaction().replace(R.id.main_fragment, newFragment).commit();
        fragmentList.clear();
        fragmentList.add(newFragment);
    }

    @Override
    public void onBackPressed() {

        if(fragmentList.size() > 0){
            closeFragment();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning");
            builder.setMessage("Do you want to close this app?");
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    MainActivity.action = ActionType.ClosingApp;
                    Intent intent = new Intent(MainTabActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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
    }
}