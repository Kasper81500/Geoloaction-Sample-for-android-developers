package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
 * Created by KasperStar on 8/20/2016.
 */
public class FriendListAdapter extends BaseAdapter {

    public static String TAG = MeetMeListAdapter.class.getSimpleName();
    ArrayList<MeetMeModel> mMeetMeList;
    Activity main;
    public static ImageLoader imageLoader = null;
    boolean delayLock = false;

    String meetMeType = "";

    FriendListAdapter(Activity mainActivity, List<FriendModel> friendList, String meetMeType){

        this.meetMeType = meetMeType;

        mMeetMeList = new ArrayList<MeetMeModel>();

        for(int memberIdx = 0; memberIdx < friendList.size(); memberIdx++){
            MeetMeModel meetmeItem = new MeetMeModel(
                    meetMeType,
                    friendList.get(memberIdx).getUserId(),
                    friendList.get(memberIdx).getProfileAvatar(),
                    friendList.get(memberIdx).getFullname(),
                    friendList.get(memberIdx).getPhone(),
                    false,
                    false
            );

            mMeetMeList.add(meetmeItem);
        }

        main = mainActivity;

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
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        MeetMeModel meetMeItem = mMeetMeList.get(position);

        if (view == null) {
            view = main.getLayoutInflater().inflate(R.layout.cell_meetme, null);
            holder = new ViewHolder();
            holder.avatarImage = (ImageView) view.findViewById(R.id.img_avatar);
            holder.fullNameText = (TextView) view.findViewById(R.id.txt_fullname);
            holder.phoneText = (TextView) view.findViewById(R.id.txt_phone);
            holder.inviteButton = (Button) view.findViewById(R.id.btn_invite);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.fullNameText.setText(meetMeItem.name);
        holder.phoneText.setText(meetMeItem.phone);

        if (meetMeItem.avatar != null && !meetMeItem.avatar.isEmpty()){
            holder.avatarImage.setVisibility(View.VISIBLE);
            imageLoader.displayImage(meetMeItem.avatar, holder.avatarImage);
        }else {
            holder.avatarImage.setVisibility(View.GONE);
        }

        if(meetMeType.equals(MeetMeType.Contact)){
            if (meetMeItem.selected) {
                holder.inviteButton.setBackgroundResource(R.mipmap.ic_checkmark);
            } else {
                holder.inviteButton.setBackgroundResource(R.mipmap.ic_plus_grey);
            }

            holder.inviteButton.setTag(position);
            holder.inviteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int selectedPosition = (int) v.getTag();
                    Button selectedButton = (Button) v;

                    MeetMeModel item = mMeetMeList.get(selectedPosition);

                    item.selected = !item.selected;

                    if (item.selected) {
                        selectedButton.setBackgroundResource(R.mipmap.ic_checkmark);
                    } else {
                        selectedButton.setBackgroundResource(R.mipmap.ic_plus_grey);
                    }
                }
            });
        }else if(meetMeType.equals(MeetMeType.Friend)){
            holder.inviteButton.setBackgroundResource(R.mipmap.ic_envelop);
            holder.inviteButton.setTag(position);
            holder.inviteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedPosition = (int) v.getTag();

                    MeetMeModel item = mMeetMeList.get(selectedPosition);
                    Intent intent = new Intent(main, MessagesActivity.class);
                    intent.putExtra("receiverId", item.pfUserId);
                    intent.putExtra("receiverName",item.name);
                    main.startActivity(intent);
                }
            });
        }

        return view;
    }
}
