package pf.paranoidfan.com.paranoidfan;

/**
 * Created by KasperStar on 9/5/2016.
 */

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.Helper.MapPinControl;
import pf.paranoidfan.com.paranoidfan.Model.MapPinModel;

/**
 * Created by KasperStar on 8/23/2016.
 */
public class EventListAdapter extends BaseAdapter {

    public static String TAG = LeaderboardAdapter.class.getSimpleName();
    ArrayList<MapPinModel> mPinList;
    ReviewListActivity main;
    public static ImageLoader imageLoader = null;

    EventListAdapter(Activity mainActivity, ArrayList<MapPinModel> userList){

        mPinList = new ArrayList<MapPinModel>();

        mPinList = userList;

        main = (ReviewListActivity)mainActivity;

        if(imageLoader == null) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(new FadeInBitmapDisplayer(300)).build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    main.getApplicationContext())
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache())
                    .discCacheSize(100 * 1024 * 1024).build();

            ImageLoader.getInstance().init(config);

            imageLoader = ImageLoader.getInstance();
        }
    }

    @Override
    public int getCount() {
        return mPinList.size();
    }

    @Override
    public long getItemId(int position) {
        return  0;
    }

    @Override
    public Object getItem(int position) {
        return mPinList.get(position);
    }

    static class ViewHolder{
        ImageView pinImage;
        TextView pinTitle;
        TextView pinCreatedDate;
        TextView distance;
        ImageView distanceImage;
        ImageView rating1;
        ImageView rating2;
        ImageView rating3;
        ImageView rating4;
        ImageView rating5;
        ImageView shareImage;
        TextView pinStadium;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        MapPinModel pinItem = mPinList.get(position);

        if (view == null) {
            view = main.getLayoutInflater().inflate(R.layout.cell_review, null);
            holder = new ViewHolder();
            holder.pinImage = (ImageView) view.findViewById(R.id.img_pin);
            holder.pinTitle = (TextView) view.findViewById(R.id.txt_pintitle);
            holder.pinCreatedDate = (TextView) view.findViewById(R.id.txt_date);
            holder.distance = (TextView) view.findViewById(R.id.txt_distance);
            holder.distanceImage = (ImageView)view.findViewById(R.id.img_direction);
            holder.rating1 = (ImageView) view.findViewById(R.id.rate1);
            holder.rating2 = (ImageView) view.findViewById(R.id.rate2);
            holder.rating3 = (ImageView) view.findViewById(R.id.rate3);
            holder.rating4 = (ImageView) view.findViewById(R.id.rate4);
            holder.rating5 = (ImageView) view.findViewById(R.id.rate5);
            holder.shareImage = (ImageView) view.findViewById(R.id.img_share);
            holder.pinStadium = (TextView) view.findViewById(R.id.txt_stadium);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.pinTitle.setText(pinItem.getMapPinTitle());
        holder.pinCreatedDate.setText("Added: " + pinItem.getMapPinDateCreated());

        if(pinItem.getDistance() != null && !pinItem.getDistance().isEmpty()) {
            holder.distance.setText(pinItem.getDistance());

            holder.distanceImage.setTag(position << 1);
            holder.distanceImage.setOnClickListener(main);

            holder.shareImage.setTag(position << 1 | 0b1);
            holder.shareImage.setOnClickListener(main);
        }

        MapPinControl.SetMapPinImageByType(holder.pinImage, pinItem.getMapPinType(), pinItem.getMapPinTags());

        int ratingCount = Integer.parseInt(pinItem.getMapPinRating());
        int score = 0;
        for(int bitIdx = 0; bitIdx < ratingCount; bitIdx++)
            score |= 1 << bitIdx;

        holder.rating1.setImageResource(((score & 1 << 0) > 0) ? R.mipmap.ic_star_fill : R.mipmap.ic_star_empty);
        holder.rating2.setImageResource(((score & 1 << 1) > 0) ? R.mipmap.ic_star_fill : R.mipmap.ic_star_empty);
        holder.rating3.setImageResource(((score & 1 << 2) > 0) ? R.mipmap.ic_star_fill : R.mipmap.ic_star_empty);
        holder.rating4.setImageResource(((score & 1 << 3) > 0) ? R.mipmap.ic_star_fill : R.mipmap.ic_star_empty);
        holder.rating5.setImageResource(((score & 1 << 4) > 0) ? R.mipmap.ic_star_fill : R.mipmap.ic_star_empty);

        if(pinItem.getMapAddress() != null && !pinItem.getMapAddress().isEmpty())
            holder.pinStadium.setText(pinItem.getMapAddress());

        return view;
    }

}

