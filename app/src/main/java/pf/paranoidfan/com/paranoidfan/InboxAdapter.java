package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
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
import pf.paranoidfan.com.paranoidfan.Model.InboxModel;

/**
 * Created by KasperStar on 8/23/2016.
 */
public class InboxAdapter extends BaseAdapter {

    public static String TAG = InboxAdapter.class.getSimpleName();
    ArrayList<InboxModel> mInboxList;
    InboxActivity main;
    public static ImageLoader imageLoader = null;
    String userId;

    InboxAdapter(Activity mainActivity, ArrayList<InboxModel> inboxList){

        userId = MainTabActivity.userid;

        mInboxList = new ArrayList<InboxModel>();

        mInboxList = inboxList;

        main = (InboxActivity)mainActivity;

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
        return mInboxList.size();
    }

    @Override
    public long getItemId(int position) {
        return  0;
    }

    @Override
    public Object getItem(int position) {
        return mInboxList.get(position);
    }

    static class ViewHolder{
        ImageView avatarImage;
        TextView fullNameText;
        TextView messageText;
        TextView dateText;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        InboxModel inboxModel = mInboxList.get(position);

        if (view == null) {
            view = main.getLayoutInflater().inflate(R.layout.cell_inbox, null);
            holder = new ViewHolder();
            holder.avatarImage = (ImageView) view.findViewById(R.id.img_avatar);
            holder.fullNameText = (TextView) view.findViewById(R.id.txt_fullname);
            holder.messageText = (TextView)view.findViewById(R.id.txt_message);
            holder.dateText = (TextView)view.findViewById(R.id.txt_date);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.fullNameText.setText(inboxModel.getFullname());
        holder.messageText.setText(inboxModel.getMessage());
        holder.dateText.setText(MapPinControl.ConvertTimeToAgo(inboxModel.getDateCreated()));

        int groupId = 0;
        if(inboxModel.getGroupId() != null && inboxModel.getGroupId() > 0){
            groupId = inboxModel.getGroupId();
        }

        int userGroupId = 0;
        if(inboxModel.getUserGroupId() != null && Integer.parseInt(inboxModel.getUserGroupId()) > 0){
            userGroupId = Integer.parseInt(inboxModel.getUserGroupId());
        }

        if(inboxModel.getMessage().equals("Meetme request")){
            holder.avatarImage.setImageResource(R.mipmap.menu_meetme);
        }else if(groupId > 0 || userGroupId > 0) {
            holder.avatarImage.setImageResource(R.mipmap.menu_group);
        }else{
            imageLoader.displayImage(inboxModel.getProfileAvatar(), holder.avatarImage);
        }

        holder.messageText.setText(inboxModel.getMessage());
        if(!userId.equals(inboxModel.getSenderId())){
            if(inboxModel.getIsRead().equals("0")){
                SpannableString spanString = new SpannableString(inboxModel.getMessage());
                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                holder.messageText.setText(spanString);
            }
        }

        return view;
    }
}
