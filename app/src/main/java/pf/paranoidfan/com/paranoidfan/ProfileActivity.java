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
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Model.UserModel;
import pf.paranoidfan.com.paranoidfan.Model.UserModelResponse;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class ProfileActivity extends AppCompatActivity {
    public static String TAG = ProfileActivity.class.getSimpleName();
    String profileId;
    public static ImageLoader imageLoader = null;

    ImageView profileImage;
    TextView nameText;
    TextView pointText;
    TextView favoriteText;
    TextView reviewText;
    TextView balanceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profileId = extras.getString("PROFILE_ID");
            if(profileId == null || profileId.isEmpty()){
                profileId = MainTabActivity.userid;
            }
        }else {
            profileId = MainTabActivity.userid;
        }

        profileImage = (ImageView)findViewById(R.id.img_profile);
        nameText = (TextView)findViewById(R.id.txt_name);
        balanceText = (TextView)findViewById(R.id.txt_balance);
        pointText = (TextView)findViewById(R.id.txt_point);

        favoriteText = (TextView)findViewById(R.id.txt_favourite);
        favoriteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, FavoriteListActivity.class);
                intent.putExtra("PROFILE_ID", profileId);
                startActivity(intent);
            }
        });

        reviewText = (TextView)findViewById(R.id.txt_review);
        reviewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ReviewListActivity.class);
                intent.putExtra("PROFILE_ID", profileId);
                startActivity(intent);
            }
        });

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

        GetUserInfoById(profileId);
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

    private void GetUserInfoById(String profileId){

        SvcApiService.getUserIdEndPoint().getUserInfoByID(profileId, new SvcApiRestCallback<UserModelResponse>() {
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
                        UserModel user = result.data;

                        if(user.getProfileAvatar() != null)
                            imageLoader.displayImage(user.getProfileAvatar(), profileImage);

                        nameText.setText(user.getFullname());
                        pointText.setText(user.getProfilePoints());
                        balanceText.setText("Current Balance: " + user.getWalletBalance());
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
