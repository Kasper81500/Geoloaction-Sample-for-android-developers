package pf.paranoidfan.com.paranoidfan;

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

import pf.paranoidfan.com.paranoidfan.Model.UserModel;

/**
 * Created by KasperStar on 8/23/2016.
 */
public class LeaderboardAdapter extends BaseAdapter {

    public static String TAG = LeaderboardAdapter.class.getSimpleName();
    ArrayList<UserModel> mUserList;
    LeaderboardActivity main;
    public static ImageLoader imageLoader = null;

    LeaderboardAdapter(Activity mainActivity, ArrayList<UserModel> userList){

        mUserList = new ArrayList<UserModel>();

        mUserList = userList;

        main = (LeaderboardActivity)mainActivity;

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
        return mUserList.size();
    }

    @Override
    public long getItemId(int position) {
        return  0;
    }

    @Override
    public Object getItem(int position) {
        return mUserList.get(position);
    }

    static class ViewHolder{
        ImageView avatarImage;
        TextView fullNameText;
        TextView reviewText;
        TextView favoriteText;
        TextView pointText;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        UserModel meetMeItem = mUserList.get(position);

        if (view == null) {
            view = main.getLayoutInflater().inflate(R.layout.cell_leaderboard, null);
            holder = new ViewHolder();
            holder.avatarImage = (ImageView) view.findViewById(R.id.img_avatar);
            holder.fullNameText = (TextView) view.findViewById(R.id.txt_name);
            holder.reviewText = (TextView)view.findViewById(R.id.txt_review);
            holder.favoriteText = (TextView)view.findViewById(R.id.txt_favourite);
            holder.pointText = (TextView) view.findViewById(R.id.txt_point);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.fullNameText.setText(meetMeItem.getFullname());

        if(meetMeItem.getProfileAvatar() != null)
            imageLoader.displayImage(meetMeItem.getProfileAvatar(), holder.avatarImage);

        holder.pointText.setText(meetMeItem.getProfilePoints());

        holder.favoriteText.setTag(position << 1);
        holder.favoriteText.setOnClickListener(main);

        holder.reviewText.setTag(position << 1 | 0b1);
        holder.reviewText.setOnClickListener(main);

        return view;
    }
}
