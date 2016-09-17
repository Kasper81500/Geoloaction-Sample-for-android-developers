package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import pf.paranoidfan.com.paranoidfan.Helper.MeetMeType;
import pf.paranoidfan.com.paranoidfan.Model.FriendModel;
import pf.paranoidfan.com.paranoidfan.Model.MeetMeModel;

/**
 * Created by KasperStar on 8/31/2016.
 */
public class ContactListAdapter extends BaseAdapter {

    public static String TAG = MeetMeListAdapter.class.getSimpleName();
    int defaultAvatarSize = 30;
    public ArrayList<MeetMeModel> mMeetMeList;
    ContactListActivity main;
    public static ImageLoader imageLoader = null;
    boolean delayLock = false;
    FriendListActivity.PlaceholderFragment mFragment;


    ContactListAdapter(Activity mainActivity, List<FriendModel> friendList){

        mMeetMeList = new ArrayList<MeetMeModel>();

        for(int memberIdx = 0; memberIdx < friendList.size(); memberIdx++){
            MeetMeModel meetmeItem = new MeetMeModel(
                    MeetMeType.Friend,
                    friendList.get(memberIdx).getUserId(),
                    friendList.get(memberIdx).getProfileAvatar(),
                    friendList.get(memberIdx).getFullname(),
                    friendList.get(memberIdx).getPhone(),
                    false,
                    false
            );

            mMeetMeList.add(meetmeItem);
        }

        main = (ContactListActivity)mainActivity;

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
        return mMeetMeList.size();
    }

    @Override
    public long getItemId(int position) {
        return  0;
    }

    @Override
    public Object getItem(int position) {
        return mMeetMeList.get(position);
    }

    static class ViewHolder{
        ImageView avatarImage;
        TextView fullNameText;
        TextView phoneText;
        Button inviteButton;
        LinearLayout mainLayout;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        MeetMeModel meetMeItem = mMeetMeList.get(position);

        if (view == null) {
            view = main.getLayoutInflater().inflate(R.layout.cell_meetme, null);
            holder = new ViewHolder();
            holder.avatarImage = (ImageView) view.findViewById(R.id.img_avatar);
            holder.fullNameText = (TextView) view.findViewById(R.id.txt_fullname);
            holder.phoneText = (TextView) view.findViewById(R.id.txt_phone);
            holder.inviteButton = (Button) view.findViewById(R.id.btn_invite);
            holder.mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.fullNameText.setText(meetMeItem.name);
        holder.phoneText.setText(meetMeItem.phone);

        if(meetMeItem.selected) {
            holder.inviteButton.setVisibility(View.VISIBLE);
            holder.inviteButton.setBackgroundResource(R.mipmap.ic_checkmark);
        }else {
            holder.inviteButton.setVisibility(View.GONE);
        }

        imageLoader.displayImage(meetMeItem.avatar, holder.avatarImage);

        holder.mainLayout.setTag(position);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = (int) v.getTag();
                mMeetMeList.get(selectedPosition).selected = !mMeetMeList.get(selectedPosition).selected;
                main.selectedFriends = mMeetMeList;
                Log.d(TAG, "friend list selected index = " + selectedPosition);
                ContactListAdapter.this.notifyDataSetChanged();
            }
        });

        return view;
    }
}
