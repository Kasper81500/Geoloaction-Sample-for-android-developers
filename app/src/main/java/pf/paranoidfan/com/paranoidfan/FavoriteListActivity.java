package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.Model.MapPinModel;
import pf.paranoidfan.com.paranoidfan.Model.MapPinResponse;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class FavoriteListActivity extends AppCompatActivity implements View.OnClickListener{

    public static String TAG = LeaderboardActivity.class.getSimpleName();

    ArrayList<MapPinModel> mUserList;
    ListView listView;
    FavoriteListAdapter adapter;
    String profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritelist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        mUserList = new ArrayList<MapPinModel>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profileId = extras.getString("PROFILE_ID");
            if(profileId != null && !profileId.isEmpty()){
                GetFavoriteList(profileId);
                return;
            }
        }

        profileId = MainTabActivity.userid;
        GetFavoriteList(profileId);
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
    public void onClick(View v){
        int tag = (int)v.getTag();
        MapPinModel pin = mUserList.get(tag >> 1);

        if((tag & 0b1) > 0){

            String pageUrl = "http://paranoidfan.com/meetme.php?type=" + pin.getMapPinType().replace(" ", "_")
                    +  "&latittude=" + pin.getMapPinLatitude()
                    +  "&longitude=" + pin.getMapPinLongitude();
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, pageUrl);

            try {
                startActivity(Intent.createChooser(intent, "Select an action"));
            } catch (android.content.ActivityNotFoundException ex) {
                Log.d(TAG, "Sharing exception occured!");
            }

        }else {
            String urlString = String.format("http://maps.google.com/?saddr=%.0f,%.0f&daddr=%3$s,%4$s",
                    MainTabActivity.myPosition.latitude,
                    MainTabActivity.myPosition.longitude,
                    pin.getCurrentLatitude(),
                    pin.getCurrentLongitude()
            );
            Log.d(TAG, "Direction URL: " + urlString);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            startActivity(browserIntent);
        }
    }

    private void GetFavoriteList(String profileId){

        SvcApiService.getUserIdEndPoint().getAllFavoritePins(profileId, new SvcApiRestCallback<MapPinResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(MapPinResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);

                    if (responseStatus.equals("true")) {

                        for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
                            mUserList.add(result.data[memberIdx]);
                        }

                        if(mUserList.size() > 0){
                            listView = (ListView) findViewById(R.id.list_favorite);
                            adapter = new FavoriteListAdapter(FavoriteListActivity.this, mUserList);
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
