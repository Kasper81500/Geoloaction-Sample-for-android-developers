package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.Model.MapPinModel;
import pf.paranoidfan.com.paranoidfan.Model.MapPinResponse;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class EventListActivity extends AppCompatActivity {
    
    public static String TAG = LeaderboardActivity.class.getSimpleName();
    ArrayList<MapPinModel> mEventList;
    ListView listView;
    EventListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        GetEventList();
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


    private void GetEventList(){
        String userId = MainTabActivity.userid;
        LatLng myPosition = MainTabActivity.myPosition;

        SvcApiService.getUserIdEndPoint().getCloseByEventsPins(userId, myPosition.latitude, myPosition.longitude, new SvcApiRestCallback<MapPinResponse>() {
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

                        mEventList = new ArrayList<MapPinModel>();

//                        for (int memberIdx = 0; memberIdx < result.data.length; memberIdx++) {
//                            mEventList.add(result.data[memberIdx]);
//                        }
//
//                        if (mEventList.size() > 0) {
//                            listView = (ListView) findViewById(R.id.list_event);
//                            adapter = new EventListAdapter(EventListActivity.this, mEventList);
//                            listView.setAdapter(adapter);
//                        }
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
