package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longtailvideo.jwplayer.JWPlayerView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

import pf.paranoidfan.com.paranoidfan.Helper.MapPinControl;
import pf.paranoidfan.com.paranoidfan.Model.GroupMessageModel;

/**
 * Created by KasperStar on 8/30/2016.
 */
public class MessagesAdapter extends BaseAdapter {

    public static String TAG = ChatListAdapter.class.getSimpleName();

    List<GroupMessageModel> mChatItems;
    MessagesActivity main;
    private ImageLoader imageLoader;
    boolean delayLock = false;
    String userId;

    public enum ChatType {
        SenderMESSAGE,
        ReceiverMESSAGE,
        SenderIMAGE,
        ReceiverIMAGE,
        SenderSticker,
        ReceiverSticker
    }

    MessagesAdapter(Activity mainActivity, List<GroupMessageModel> chatItems){
        mChatItems = chatItems;
        main = (MessagesActivity)mainActivity;

        userId = MainTabActivity.userid;

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

    @Override
    public int getCount() {
        return mChatItems.size();
    }

    @Override
    public long getItemId(int position) {
        return  0;
    }

    @Override
    public Object getItem(int position) {
        return mChatItems.get(position);
    }

    static class ViewHolder{
        /*receiver*/
        ImageView profileImage1;
        TextView message1;
        ImageView photo1;
        JWPlayerView jwPlayerView1;
        ImageView videoPlayIcon1;
        TextView dateMessageText1;
        TextView dateImageText1;
        ImageView stickImage1;
        RelativeLayout mediaLayout1;
        RelativeLayout receiverPart;

        /*sender*/
        ImageView profileImage2;
        TextView message2;
        ImageView photo2;
        JWPlayerView jwPlayerView2;
        ImageView videoPlayIcon2;
        TextView dateMessageText2;
        TextView dateImageText2;
        ImageView stickImage2;
        RelativeLayout mediaLayout2;
        RelativeLayout senderPart;
    }

    public void add(GroupMessageModel chatItems){
        mChatItems.add(0, chatItems);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        GroupMessageModel chatItem = mChatItems.get(position);
        ChatType chatType = ChatType.SenderMESSAGE;

        int receiverId = 0;
        if(main.receiverId != null && !main.receiverId.isEmpty())
            receiverId = Integer.parseInt(main.receiverId);

        int groupId = 0;
        if(main.groupId != null && !main.groupId.isEmpty())
            groupId = Integer.parseInt(main.groupId);

        if(receiverId > 0)
            chatType = (mChatItems.get(position).getReceiverId().equals(main.receiverId))? ChatType.SenderMESSAGE : ChatType.ReceiverMESSAGE;
        else if(groupId > 0)
            chatType = (mChatItems.get(position).getSenderId().equals(userId))? ChatType.SenderMESSAGE : ChatType.ReceiverMESSAGE;

        if(chatType == ChatType.SenderMESSAGE &&
                    ((mChatItems.get(position).getPhoto() != null && mChatItems.get(position).getPhoto().length() > 0)
                 || (mChatItems.get(position).getVideoLink() != null && mChatItems.get(position).getVideoLink().length() > 0))){
            chatType = ChatType.SenderIMAGE;
        }else if(chatType == ChatType.ReceiverMESSAGE &&
                    ((mChatItems.get(position).getPhoto() != null && mChatItems.get(position).getPhoto().length() > 0)
                 || (mChatItems.get(position).getVideoLink() != null && mChatItems.get(position).getVideoLink().length() > 0))){
            chatType = ChatType.ReceiverIMAGE;
        }else if(chatType == ChatType.SenderMESSAGE && mChatItems.get(position).getSticker() != null && !mChatItems.get(position).getSticker().isEmpty()){
            chatType = ChatType.SenderSticker;
        }else if(chatType == ChatType.ReceiverMESSAGE && mChatItems.get(position).getSticker() != null && !mChatItems.get(position).getSticker().isEmpty()){
            chatType = ChatType.ReceiverSticker;
        }

        if (view == null) {
            view = main.getLayoutInflater().inflate(R.layout.cell_chat_receiver, null);
            holder = new ViewHolder();
            /*Receiver*/
            holder.profileImage1 = (ImageView) view.findViewById(R.id.img_profile1);
            holder.message1 = (TextView) view.findViewById(R.id.txt_message1);
            holder.photo1 = (ImageView) view.findViewById(R.id.img_photo1);
            holder.jwPlayerView1 = (JWPlayerView) view.findViewById(R.id.view_video1);
            holder.videoPlayIcon1 = (ImageView)view.findViewById(R.id.img_play1);
            holder.dateMessageText1 = (TextView) view.findViewById(R.id.txt_message_date1);
            holder.dateImageText1 = (TextView) view.findViewById(R.id.txt_image_date1);
            holder.stickImage1 = (ImageView)view.findViewById(R.id.img_stick1);
            holder.mediaLayout1 = (RelativeLayout) view.findViewById(R.id.relative_multimedia1);
            holder.receiverPart = (RelativeLayout) view.findViewById(R.id.relative_receiver);
            /*Transiver*/
            holder.profileImage2 = (ImageView) view.findViewById(R.id.img_profile2);
            holder.message2 = (TextView) view.findViewById(R.id.txt_message2);
            holder.photo2 = (ImageView) view.findViewById(R.id.img_photo2);
            holder.jwPlayerView2 = (JWPlayerView) view.findViewById(R.id.view_video2);
            holder.videoPlayIcon2 = (ImageView)view.findViewById(R.id.img_play2);
            holder.dateMessageText2 = (TextView) view.findViewById(R.id.txt_message_date2);
            holder.dateImageText2 = (TextView) view.findViewById(R.id.txt_image_date2);
            holder.stickImage2 = (ImageView)view.findViewById(R.id.img_stick2);
            holder.mediaLayout2 = (RelativeLayout) view.findViewById(R.id.relative_multimedia2);
            holder.senderPart = (RelativeLayout) view.findViewById(R.id.relative_sender);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        boolean isSender = false;

        if(chatType == ChatType.SenderMESSAGE || chatType == ChatType.SenderIMAGE || chatType == ChatType.SenderSticker){
            holder.receiverPart.setVisibility(View.GONE);
            holder.senderPart.setVisibility(View.VISIBLE);
            isSender = true;
        }else{
            holder.receiverPart.setVisibility(View.VISIBLE);
            holder.senderPart.setVisibility(View.GONE);
            isSender = false;
        }

        /*Initialize View Object*/
        if(isSender) {
            holder.videoPlayIcon2.setVisibility(View.GONE);
            if (chatType == ChatType.SenderIMAGE) {
                holder.mediaLayout2.setVisibility(View.VISIBLE);
                holder.dateMessageText2.setVisibility(View.GONE);
                holder.dateImageText2.setVisibility(View.VISIBLE);
                holder.stickImage2.setVisibility(View.GONE);
                holder.profileImage2.setVisibility(View.VISIBLE);
                holder.message2.setVisibility(View.VISIBLE);
            } else if(chatType == ChatType.SenderSticker) {
                holder.mediaLayout2.setVisibility(View.GONE);
                holder.dateMessageText2.setVisibility(View.GONE);
                holder.dateImageText2.setVisibility(View.GONE);
                holder.stickImage2.setVisibility(View.VISIBLE);
                holder.profileImage2.setVisibility(View.GONE);
                holder.message2.setVisibility(View.GONE);
            }else{
                holder.mediaLayout2.setVisibility(View.GONE);
                holder.dateMessageText2.setVisibility(View.VISIBLE);
                holder.dateImageText2.setVisibility(View.GONE);
                holder.stickImage2.setVisibility(View.GONE);
                holder.profileImage2.setVisibility(View.VISIBLE);
                holder.message2.setVisibility(View.VISIBLE);
            }

            /*Set up View Object*/
            imageLoader.displayImage(chatItem.getProfileAvatar(), holder.profileImage2);

            String message = chatItem.getMessage();
            if(chatType != ChatType.SenderSticker) {
                if (message != null && !message.isEmpty()) {
                    holder.message2.setVisibility(View.VISIBLE);
                    holder.message2.setText(chatItem.getMessage());
                } else {
                    holder.message2.setVisibility(View.INVISIBLE);
                }
            }

            if (chatType != ChatType.SenderMESSAGE) {
                if(chatType == ChatType.SenderSticker){
                    MapPinControl.setStickImage(chatItem.getSticker(), holder.stickImage2);
                }else{
                    String imageUrl = chatItem.getPhoto();
                    imageLoader.displayImage(imageUrl, holder.photo2);
                }

                holder.photo2.setVisibility(View.VISIBLE);
            }

            holder.dateMessageText2.setText(MapPinControl.ConvertTimeToAgo(chatItem.getDateCreated()));
            holder.dateImageText2.setText(MapPinControl.ConvertTimeToAgo(chatItem.getDateCreated()));

        }else{
            holder.videoPlayIcon1.setVisibility(View.GONE);
            if (chatType == ChatType.ReceiverIMAGE) {
                holder.mediaLayout1.setVisibility(View.VISIBLE);
                holder.dateMessageText1.setVisibility(View.GONE);
                holder.dateImageText1.setVisibility(View.VISIBLE);
                holder.stickImage1.setVisibility(View.GONE);
                holder.profileImage1.setVisibility(View.VISIBLE);
                holder.message1.setVisibility(View.VISIBLE);
            }else if(chatType == ChatType.ReceiverSticker) {
                holder.mediaLayout1.setVisibility(View.GONE);
                holder.dateMessageText1.setVisibility(View.GONE);
                holder.dateImageText1.setVisibility(View.GONE);
                holder.stickImage1.setVisibility(View.VISIBLE);
                holder.profileImage1.setVisibility(View.GONE);
                holder.message1.setVisibility(View.GONE);
            } else {
                holder.mediaLayout1.setVisibility(View.GONE);
                holder.dateMessageText1.setVisibility(View.VISIBLE);
                holder.dateImageText1.setVisibility(View.GONE);
                holder.stickImage1.setVisibility(View.GONE);
                holder.profileImage1.setVisibility(View.VISIBLE);
                holder.message1.setVisibility(View.VISIBLE);
            }

            /*Set up View Object*/
            imageLoader.displayImage(chatItem.getProfileAvatar(), holder.profileImage1);

            String message = chatItem.getMessage();
            if(chatType != ChatType.ReceiverSticker) {
                if (message != null && !message.isEmpty()) {
                    holder.message1.setVisibility(View.VISIBLE);
                    holder.message1.setText(chatItem.getMessage());
                } else {
                    holder.message1.setVisibility(View.INVISIBLE);
                    holder.message1.setText("");
                }
            }

            if (chatType != ChatType.ReceiverMESSAGE) {

                if(chatType == ChatType.ReceiverSticker){
                    MapPinControl.setStickImage(chatItem.getSticker(), holder.stickImage1);
                }else{
                    String imageUrl = chatItem.getPhoto();
                    imageLoader.displayImage(imageUrl, holder.photo1);
                }

                holder.photo1.setVisibility(View.VISIBLE);
            }

            holder.dateMessageText1.setText(MapPinControl.ConvertTimeToAgo(chatItem.getDateCreated()));
            holder.dateImageText1.setText(MapPinControl.ConvertTimeToAgo(chatItem.getDateCreated()));
        }
        return view;
    }
}
