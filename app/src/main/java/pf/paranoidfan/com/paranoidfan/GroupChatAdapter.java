package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.core.PlayerState;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.playlists.MediaSource;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import pf.paranoidfan.com.paranoidfan.Helper.MapPinControl;
import pf.paranoidfan.com.paranoidfan.Model.GroupChatModel;

/**
 * Created by KasperStar on 8/28/2016.
 */
public class GroupChatAdapter extends BaseAdapter {

    public static String TAG = ChatListAdapter.class.getSimpleName();

    List<GroupChatModel> mChatItems;
    GroupChatActivity main;
    private ImageLoader imageLoader;
    boolean delayLock = false;
    String userId;

    public enum ChatType {
        MESSAGE,
        IMAGE,
        VIDEO
    }

    GroupChatAdapter(Activity mainActivity, List<GroupChatModel> chatItems){
        mChatItems = chatItems;
        main = (GroupChatActivity)mainActivity;

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
        RelativeLayout mediaLayout2;
        RelativeLayout senderPart;
    }

    public void add(GroupChatModel chatItems){
        mChatItems.add(0, chatItems);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        GroupChatModel chatItem = mChatItems.get(position);

        ChatType chatType = ChatType.MESSAGE;

        if(chatItem.getChatPhoto() != null && !chatItem.getChatPhoto().isEmpty()){
            chatType = ChatType.IMAGE;
        }

        if(chatItem.getChatVideoLink() != null && !chatItem.getChatVideoLink().isEmpty()){
            chatType = ChatType.VIDEO;
        }

        Boolean isSender = userId.equals(chatItem.getUserId());

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
            holder.mediaLayout2 = (RelativeLayout) view.findViewById(R.id.relative_multimedia2);
            holder.senderPart = (RelativeLayout) view.findViewById(R.id.relative_sender);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(isSender){
            holder.receiverPart.setVisibility(View.GONE);
            holder.senderPart.setVisibility(View.VISIBLE);
        }else{
            holder.receiverPart.setVisibility(View.VISIBLE);
            holder.senderPart.setVisibility(View.GONE);
        }

        /*Initialize View Object*/
        if(isSender) {
            holder.videoPlayIcon2.setVisibility(View.GONE);
            if (chatType == ChatType.IMAGE || chatType == ChatType.VIDEO) {
                holder.mediaLayout2.setVisibility(View.VISIBLE);
                holder.dateMessageText2.setVisibility(View.GONE);
                holder.dateImageText2.setVisibility(View.VISIBLE);
            } else {
                holder.mediaLayout2.setVisibility(View.GONE);
                holder.dateMessageText2.setVisibility(View.VISIBLE);
                holder.dateImageText2.setVisibility(View.GONE);
            }

            /*Set up View Object*/
            imageLoader.displayImage(chatItem.getProfileAvatar(), holder.profileImage2);

            String message = chatItem.getChatMessage();
            if (message != null && !message.isEmpty()) {
                holder.message2.setVisibility(View.VISIBLE);
                holder.message2.setText(chatItem.getChatMessage());
            } else {
                holder.message2.setVisibility(View.INVISIBLE);
                holder.message2.setText("");
            }

            if (chatType == ChatType.VIDEO)
                holder.videoPlayIcon2.setVisibility(View.VISIBLE);

            if (chatType != ChatType.MESSAGE) {
                String imageUrl = chatItem.getChatPhoto();
                if (chatType == ChatType.VIDEO) {
                    String videoPhotoLink = chatItem.getChatVideoLink();
                    videoPhotoLink = videoPhotoLink.replace(".mp4", "_thumb.png");
                    videoPhotoLink = videoPhotoLink.replace("videos/", "videos/thumbnails/");
                    imageUrl = videoPhotoLink;

                    Log.d(TAG, "---------------------- VIDEO image link = " + imageUrl);
                }
                imageLoader.displayImage(imageUrl, holder.photo2);
                holder.photo2.setVisibility(View.VISIBLE);
            }

            holder.dateMessageText2.setText(MapPinControl.ConvertTimeToAgo(chatItem.getChatDateCreated()));
            holder.dateImageText2.setText(MapPinControl.ConvertTimeToAgo(chatItem.getChatDateCreated()));

            if (chatItem.getChatVideoLink() != null && !chatItem.getChatVideoLink().isEmpty())
                setupJwPlayer(holder.jwPlayerView2, holder.photo2, holder.videoPlayIcon2, chatItem.getChatVideoLink());
        }else{
            holder.videoPlayIcon1.setVisibility(View.GONE);
            if (chatType == ChatType.IMAGE || chatType == ChatType.VIDEO) {
                holder.mediaLayout1.setVisibility(View.VISIBLE);
                holder.dateMessageText1.setVisibility(View.GONE);
                holder.dateImageText1.setVisibility(View.VISIBLE);
            } else {
                holder.mediaLayout1.setVisibility(View.GONE);
                holder.dateMessageText1.setVisibility(View.VISIBLE);
                holder.dateImageText1.setVisibility(View.GONE);
            }

            /*Set up View Object*/
            imageLoader.displayImage(chatItem.getProfileAvatar(), holder.profileImage1);

            String message = chatItem.getChatMessage();
            if (message != null && !message.isEmpty()) {
                holder.message1.setVisibility(View.VISIBLE);
                holder.message1.setText(chatItem.getChatMessage());
            } else {
                holder.message1.setVisibility(View.INVISIBLE);
                holder.message1.setText("");
            }

            if (chatType == ChatType.VIDEO)
                holder.videoPlayIcon1.setVisibility(View.VISIBLE);

            if (chatType != ChatType.MESSAGE) {
                String imageUrl = chatItem.getChatPhoto();
                if (chatType == ChatType.VIDEO) {
                    String videoPhotoLink = chatItem.getChatVideoLink();
                    videoPhotoLink = videoPhotoLink.replace(".mp4", "_thumb.png");
                    videoPhotoLink = videoPhotoLink.replace("videos/", "videos/thumbnails/");
                    imageUrl = videoPhotoLink;

                    Log.d(TAG, "---------------------- VIDEO image link = " + imageUrl);
                }
                imageLoader.displayImage(imageUrl, holder.photo1);
                holder.photo1.setVisibility(View.VISIBLE);
            }

            holder.dateMessageText1.setText(MapPinControl.ConvertTimeToAgo(chatItem.getChatDateCreated()));
            holder.dateImageText1.setText(MapPinControl.ConvertTimeToAgo(chatItem.getChatDateCreated()));

            if (chatItem.getChatVideoLink() != null && !chatItem.getChatVideoLink().isEmpty())
                setupJwPlayer(holder.jwPlayerView1, holder.photo1, holder.videoPlayIcon1, chatItem.getChatVideoLink());

        }
        return view;
    }

    private void setupJwPlayer(final JWPlayerView jwPlayer, final ImageView videoImage, final ImageView videoPlayImage, String url){

        Log.d(TAG, "video link = " + url);

        List<PlaylistItem> playArray = new ArrayList<>();
        List<MediaSource> mediaSources = new ArrayList<>();

        final Uri originalUriMp4 = Uri.parse(url);
        mediaSources.add(new MediaSource(originalUriMp4.toString()));

        PlaylistItem item = new PlaylistItem();
        item.setSources(mediaSources);

        playArray.add(item);
        jwPlayer.load(playArray);

        jwPlayer.addOnCompleteListener(new VideoPlayerEvents.OnCompleteListener() {
            @Override
            public void onComplete() {
                jwPlayer.play();
            }
        });

        jwPlayer.addOnPlayListener(new VideoPlayerEvents.OnPlayListener() {
            @Override
            public void onPlay(PlayerState playerState) {
                videoImage.setVisibility(View.GONE);
                videoPlayImage.setVisibility(View.GONE);
                jwPlayer.setVisibility(View.VISIBLE);
            }
        });

        jwPlayer.addOnErrorListener(new VideoPlayerEvents.OnErrorListener() {
            @Override
            public void onError(String s) {
                videoImage.setVisibility(View.VISIBLE);
                videoPlayImage.setVisibility(View.VISIBLE);
                jwPlayer.setVisibility(View.GONE);
            }
        });

        jwPlayer.addOnSetupErrorListener(new VideoPlayerEvents.OnSetupErrorListener() {
            @Override
            public void onSetupError(String s) {
                videoImage.setVisibility(View.VISIBLE);
                videoPlayImage.setVisibility(View.VISIBLE);
                jwPlayer.setVisibility(View.GONE);
            }
        });

        jwPlayer.addOnCompleteListener(new VideoPlayerEvents.OnCompleteListener() {
            @Override
            public void onComplete() {
                videoImage.setVisibility(View.VISIBLE);
                jwPlayer.setVisibility(View.GONE);
            }
        });

        jwPlayer.setControls(false);
        jwPlayer.play();
    }


}
