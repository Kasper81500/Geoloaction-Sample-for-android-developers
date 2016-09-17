package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ViewSwitcher;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import pf.paranoidfan.com.paranoidfan.Model.CommonRespose;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class AvatarActivity extends AppCompatActivity {

    public static String TAG = AvatarActivity.class.getSimpleName();

    List<String> boyPhotos;
    List<String> girlPhotos;
    List<String> circles;
    List<String> selectedCircles;
    List<String> boyColorPhotos;
    List<String> girlColorPhotos;

    private int currBoyImage = 0;
    private int currGirlImage = 0;

    private boolean isBoySelected = true;

    private List<Boolean> skinColors;

    View blueView;
    View darkGreenView;
    View yellowView;
    View orangeView;
    View maroonView;
    View redView;
    View purpleView;
    View blackView;
    View whiteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_home);
        setSupportActionBar(toolbar);

        boyPhotos = new ArrayList<>();
        for(int pIdx = 0; pIdx < 27; pIdx++ )
            boyPhotos.add("boy_skin_" + pIdx);

        girlPhotos = new ArrayList<>();
        for(int pIdx = 0; pIdx < 26; pIdx++ )
            girlPhotos.add("gril_skin_" + pIdx);

        circles = new ArrayList<>();
        circles.add("circle_blue");
        circles.add("circle_dark_green");
        circles.add("circle_yellow");
        circles.add("circle_orange");
        circles.add("circle_maroon");
        circles.add("circle_red");
        circles.add("circle_purple");
        circles.add("circle_black");
        circles.add("circle_white");

        selectedCircles = new ArrayList<>();
        selectedCircles.add("circle_blue_stroke");
        selectedCircles.add("circle_dark_green_stroke");
        selectedCircles.add("circle_yellow_stroke");
        selectedCircles.add("circle_orange_stroke");
        selectedCircles.add("circle_maroon_stroke");
        selectedCircles.add("circle_red_stroke");
        selectedCircles.add("circle_purple_stroke");
        selectedCircles.add("circle_black_stroke");
        selectedCircles.add("circle_white_stroke");

        boyColorPhotos = new ArrayList<>();
        boyColorPhotos.add("boy_blue");
        boyColorPhotos.add("boy_dark_green");
        boyColorPhotos.add("boy_yellow");
        boyColorPhotos.add("boy_orange");
        boyColorPhotos.add("boy_maroon");
        boyColorPhotos.add("boy_red");
        boyColorPhotos.add("boy_purple");
        boyColorPhotos.add("boy_black");
        boyColorPhotos.add("boy_white");

        girlColorPhotos = new ArrayList<>();
        girlColorPhotos.add("girl_blue");
        girlColorPhotos.add("girl_dark_green");
        girlColorPhotos.add("girl_yellow");
        girlColorPhotos.add("girl_orange");
        girlColorPhotos.add("girl_maroon");
        girlColorPhotos.add("girl_red");
        girlColorPhotos.add("girl_purple");
        girlColorPhotos.add("girl_black");
        girlColorPhotos.add("girl_white");

        initializeImageSwitcher();
        initBySelectSkin(true);
        setImageRotateListener();

        info.hoang8f.android.segmented.SegmentedGroup segmentedSkin = (info.hoang8f.android.segmented.SegmentedGroup)findViewById(R.id.segmented_skin);

        RadioButton boyButton = (RadioButton)findViewById(R.id.btn_boy);
        boyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBySelectSkin(true);
            }
        });
        boyButton.setSelected(true);

        RadioButton girlButton = (RadioButton)findViewById(R.id.btn_girl);
        girlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBySelectSkin(false);
            }
        });

        skinColors = new ArrayList<>();
        for(int colorIdx = 0; colorIdx < 9; colorIdx++)
            skinColors.add(false);

        blueView = (View)findViewById(R.id.shape_blue);
        blueView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageByColor(v, 0);
            }
        });

        darkGreenView = (View)findViewById(R.id.shape_dark_green);
        darkGreenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageByColor(v, 1);
            }
        });

        yellowView = (View)findViewById(R.id.shape_yellow);
        yellowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageByColor(v, 2);
            }
        });

        orangeView = (View)findViewById(R.id.shape_orange);
        orangeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageByColor(v, 3);
            }
        });

        maroonView = (View)findViewById(R.id.shape_maroon);
        maroonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageByColor(v, 4);
            }
        });

        redView = (View)findViewById(R.id.shape_red);
        redView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageByColor(v, 5);
            }
        });

        purpleView = (View)findViewById(R.id.shape_purple);
        purpleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageByColor(v, 6);
            }
        });

        blackView = (View)findViewById(R.id.shape_black);
        blackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageByColor(v, 7);
            }
        });

        whiteView = (View)findViewById(R.id.shape_white);
        whiteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageByColor(v, 8);
            }
        });
    }

    private void initColorView(){
        blueView.setBackgroundResource(getResources().getIdentifier(circles.get(0), "drawable", AvatarActivity.this.getPackageName()));
        darkGreenView.setBackgroundResource(getResources().getIdentifier(circles.get(1), "drawable", AvatarActivity.this.getPackageName()));
        yellowView.setBackgroundResource(getResources().getIdentifier(circles.get(2), "drawable", AvatarActivity.this.getPackageName()));
        orangeView.setBackgroundResource(getResources().getIdentifier(circles.get(3), "drawable", AvatarActivity.this.getPackageName()));
        maroonView.setBackgroundResource(getResources().getIdentifier(circles.get(4), "drawable", AvatarActivity.this.getPackageName()));
        redView.setBackgroundResource(getResources().getIdentifier(circles.get(5), "drawable", AvatarActivity.this.getPackageName()));
        purpleView.setBackgroundResource(getResources().getIdentifier(circles.get(6), "drawable", AvatarActivity.this.getPackageName()));
        blackView.setBackgroundResource(getResources().getIdentifier(circles.get(7), "drawable", AvatarActivity.this.getPackageName()));
        whiteView.setBackgroundResource(getResources().getIdentifier(circles.get(8), "drawable", AvatarActivity.this.getPackageName()));
    }

    private void setImageByColor(View v, int index){

        initColorView();

        for(int colorIdx = 0; colorIdx < 9; colorIdx++){
            skinColors.set(colorIdx, false);
        }
        v.setBackgroundResource(getResources().getIdentifier(selectedCircles.get(index), "drawable", AvatarActivity.this.getPackageName()));
        skinColors.set(index, true);

        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_static));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_static));

        if(isBoySelected) {
            imageSwitcher.setImageResource(getResources().getIdentifier(boyColorPhotos.get(index), "mipmap", AvatarActivity.this.getPackageName()));
        }else {
            imageSwitcher.setImageResource(getResources().getIdentifier(girlColorPhotos.get(index), "mipmap", AvatarActivity.this.getPackageName()));
        }
    }

    private void initializeImageSwitcher() {
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(AvatarActivity.this);
                return imageView;
            }
        });
    }

    private void initBySelectSkin(boolean isBoy){
        currBoyImage = 0;
        currGirlImage = 0;
        isBoySelected = isBoy;
        setInitialImage();
    }

    private void setImageRotateListener() {
        final Button previousButton = (Button) findViewById(R.id.btn_previous);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(isBoySelected) {
                    if (currBoyImage > 0) {
                        currBoyImage--;
                        setCurrentImage(false);
                    }
                }else{
                    if (currGirlImage > 0) {
                        currGirlImage--;
                        setCurrentImage(false);
                    }
                }
            }
        });

        final Button nextButton = (Button) findViewById(R.id.btn_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (isBoySelected) {
                    if (currBoyImage < boyPhotos.size() - 1) {
                        currBoyImage++;
                        setCurrentImage(true);
                    }
                } else {
                    if (currGirlImage < girlPhotos.size() - 1) {
                        currGirlImage++;
                        setCurrentImage(true);
                    }
                }
            }
        });

    }

    private void setInitialImage() {
        setCurrentImage(false);
    }

    private void setCurrentImage(boolean isNext) {

        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        if(isNext) {
            imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
            imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_left));
        }else {
            imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left));
            imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_right));
        }

        if(isBoySelected)
            imageSwitcher.setImageResource(getResources().getIdentifier(boyPhotos.get(currBoyImage), "mipmap", AvatarActivity.this.getPackageName()));
        else
            imageSwitcher.setImageResource(getResources().getIdentifier(girlPhotos.get(currGirlImage), "mipmap", AvatarActivity.this.getPackageName()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                UpdateProfilePhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void UpdateProfilePhoto()
    {
        String useid = MainTabActivity.userid;

        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.buildDrawingCache();
        Bitmap bm = imageSwitcher.getDrawingCache();
        String encodedImageString = BitMapToString(bm);

        SvcApiService.getUserIdEndPoint().updateProfilePhoto(
                useid,
                encodedImageString,
                new SvcApiRestCallback<CommonRespose>() {
                    @Override
                    public void failure(SvcError svcError) {
                        Log.d(TAG, "Failed to Login " + svcError.toString());
                        finish();
                    }

                    @Override
                    public void success(CommonRespose result, Response response) {
                        Log.d(TAG, "Succeed to Server " + result.toString());
                        try {
                            String responseStatus = result.status;

                            Log.d(TAG, "status=" + responseStatus);

                            if (responseStatus.equals("true")) {
                            }

                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                            finish();
                        }
                    }
                });
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,50, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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
