package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import pf.paranoidfan.com.paranoidfan.Model.ChatModel;
import pf.paranoidfan.com.paranoidfan.Model.ChatResponse;
import pf.paranoidfan.com.paranoidfan.Model.CommonRespose;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

/**
 * Created by KasperStar on 8/25/2016.
 */
public class ChatListAdapter extends BaseAdapter {

    public static String TAG = ChatListAdapter.class.getSimpleName();

    List<ChatModel> mChatItems;
    ChatActivity main;
    private ImageLoader imageLoader;
    boolean delayLock = false;
    String userId;
    public String replyId = "0";

    public enum ChatType {
        MESSAGE,
        IMAGE,
        VIDEO
    }

    ChatListAdapter(Activity mainActivity, List<ChatModel> chatItems){
        mChatItems = chatItems;
        main = (ChatActivity)mainActivity;

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
        ImageView profileImage;
        TextView userName;
        TextView likeCountText;
        ImageView likeImage;
        TextView message;
        ImageView photo;
        JWPlayerView jwPlayerView;
        ImageView videoPlayIcon;
        TextView dateText;
        TextView replyText;
        TextView reportText;
        TextView tipText;
    }

    public void add(ChatModel chatItems){
        mChatItems.add(0, chatItems);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        ChatModel chatItem = mChatItems.get(position);

        ChatType chatType = ChatType.MESSAGE;

        if(chatItem.getChatPhoto() != null && !chatItem.getChatPhoto().isEmpty()){
            chatType = ChatType.IMAGE;
        }

        if(chatItem.getChatVideoLink() != null && !chatItem.getChatVideoLink().isEmpty()){
            chatType = ChatType.VIDEO;
        }

        if (view == null) {
            view = main.getLayoutInflater().inflate(R.layout.customview_message, null);
            holder = new ViewHolder();
            holder.profileImage = (ImageView) view.findViewById(R.id.img_profile);
            holder.userName = (TextView) view.findViewById(R.id.txt_username);
            holder.likeCountText = (TextView) view.findViewById(R.id.txt_likecount);
            holder.likeImage = (ImageView) view.findViewById(R.id.img_like);
            holder.likeCountText = (TextView) view.findViewById(R.id.txt_likecount);
            holder.message = (TextView) view.findViewById(R.id.txt_message);
            holder.photo = (ImageView) view.findViewById(R.id.img_photo);
            holder.jwPlayerView = (JWPlayerView) view.findViewById(R.id.view_video);
            holder.videoPlayIcon = (ImageView)view.findViewById(R.id.img_play);
            holder.dateText = (TextView) view.findViewById(R.id.txt_time);
            holder.replyText = (TextView) view.findViewById(R.id.btn_reply);
            holder.reportText = (TextView) view.findViewById(R.id.btn_report);
            holder.tipText = (TextView) view.findViewById(R.id.btn_tip);
            holder.tipText.setVisibility(View.GONE);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        /*Initialize View Object*/
        holder.videoPlayIcon.setVisibility(View.GONE);
        holder.photo.setVisibility(View.GONE);
        holder.jwPlayerView.setVisibility(View.GONE);
        int gridHeight = main.phoneWidth;
        if (chatType == ChatType.IMAGE || chatType == ChatType.VIDEO){
            holder.photo.getLayoutParams().height = gridHeight;
            holder.jwPlayerView.getLayoutParams().height = gridHeight;
        }else {
            holder.photo.getLayoutParams().height = 0;
            holder.jwPlayerView.getLayoutParams().height = 0;
        }

        /*Set up View Object*/
        imageLoader.displayImage(chatItem.getProfileAvatar(), holder.profileImage);
        holder.userName.setText(chatItem.getFullname());
        if(chatItem.getLiked()!=null && chatItem.getLiked()){
            holder.likeCountText.setText(chatItem.getChatLikeCount());
            holder.likeImage.setImageResource(R.mipmap.ic_heart_red);
        }else{
            holder.likeCountText.setText("0");
            holder.likeImage.setImageResource(R.mipmap.ic_grey_heart);
        }

        holder.likeImage.setTag(position);
        holder.likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                String chatId = mChatItems.get(position).getLocalChatId();
                AddFavorite(userId, chatId);
                ImageView likeImage = (ImageView)v;
                likeImage.setImageResource(R.mipmap.ic_heart_red);
            }
        });

        String message = chatItem.getChatMessage();
        if(message != null && !message.isEmpty()){
            holder.message.setText(chatItem.getChatMessage());
        }else{
            holder.message.setText("");
        }

        if(chatType == ChatType.VIDEO)
            holder.videoPlayIcon.setVisibility(View.VISIBLE);

        if(chatType != ChatType.MESSAGE){
            String imageUrl = chatItem.getChatPhoto();
            if(chatType == ChatType.VIDEO) {
                String videoPhotoLink = chatItem.getChatVideoLink();
                videoPhotoLink = videoPhotoLink.replace(".mp4", "_thumb.png");
                videoPhotoLink = videoPhotoLink.replace("videos/", "videos/thumbnails/");
                imageUrl = videoPhotoLink;

                Log.d(TAG, "---------------------- VIDEO image link = " + imageUrl);
            }
            imageLoader.displayImage(imageUrl, holder.photo);
            holder.photo.setVisibility(View.VISIBLE);
        }

        holder.dateText.setText(chatItem.getDistance() + " - " + MapPinControl.ConvertTimeToAgo(chatItem.getChatDateCreated()));

        holder.reportText.setTag(chatItem);
        holder.reportText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatModel model = (ChatModel) v.getTag();
                ReportChatWithID(MainTabActivity.userid, model.getLocalChatId(), "Geo Chat");
            }
        });

        holder.replyText.setTag(chatItem);
        holder.replyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatModel model = (ChatModel) v.getTag();
                main.editText.setText("@" + model.getFullname() + " ");
                main.editText.requestFocus();
                main.editText.setSelection(main.editText.getText().length());
                replyId = model.getUserId();

                InputMethodManager imm = (InputMethodManager) main.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(main.editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        if(chatItem.getChatVideoLink() != null && !chatItem.getChatVideoLink().isEmpty())
            setupJwPlayer(holder.jwPlayerView, holder.photo, holder.videoPlayIcon, chatItem.getChatVideoLink());

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


    private void ReportChatWithID(final String userid, final String chatId, final String chatType)
    {
        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().reportChatWithID(userid, chatId, chatType, new SvcApiRestCallback<CommonRespose>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(CommonRespose result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);

                    if (responseStatus.equals("true")) {
                        showAlertMessage("Success", result.message);
                    } else {
                        showAlertMessage("Error", result.message);
                    }

                    delayLock = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }

    private void GetAllChatByLocation(final String userid){

        if(delayLock == true)
            return;

        double latitude = MainTabActivity.myPosition.latitude;
        double longitude = MainTabActivity.myPosition.longitude;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().getAllLocalChatByLocation(latitude, longitude, userid, new SvcApiRestCallback<ChatResponse>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(ChatResponse result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;


                    Log.d(TAG, "status=" + responseStatus);
                    Log.d(TAG, "data=" + result.data.toString());

                    if (responseStatus.equals("true")) {
                        showAlertMessage("Success", "");
                    }

                    delayLock = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }


    private void AddFavorite(final String userid, final String chatId){

        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().likeChatWithID(userid, chatId, new SvcApiRestCallback<CommonRespose>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(CommonRespose result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    String responseStatus = result.status;

                    Log.d(TAG, "status=" + responseStatus);
                    if (responseStatus.equals("true")) {

                    }

                    delayLock = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }

    private void showAlertMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(main);
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
