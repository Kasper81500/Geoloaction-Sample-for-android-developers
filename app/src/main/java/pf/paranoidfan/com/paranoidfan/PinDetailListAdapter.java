package pf.paranoidfan.com.paranoidfan;

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
import pf.paranoidfan.com.paranoidfan.Model.CommonRespose;
import pf.paranoidfan.com.paranoidfan.Model.PinDetailModel;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

/**
 * Created by KasperStar on 8/14/2016.
 */
public class PinDetailListAdapter extends BaseAdapter {

    public static String TAG = PinDetailListAdapter.class.getSimpleName();

    List<PinDetailModel> mChatItems;
    MainTabActivity main;
    PinDetailFragment mFragment;
    private ImageLoader imageLoader;
    boolean delayLock = false;
    public String replyId = "0";

    public enum ChatType {
        MESSAGE,
        IMAGE,
        VIDEO
    }

    PinDetailListAdapter(MainTabActivity mainActivity, PinDetailFragment fragment, List<PinDetailModel> chatItems){
        mFragment = fragment;
        mChatItems = chatItems;
        main = mainActivity;

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

    public void add(PinDetailModel chatItems){
        mChatItems.add(0, chatItems);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        PinDetailModel chatItem = mChatItems.get(position);

        ChatType chatType = ChatType.MESSAGE;

        if(chatItem.getPinChatPhoto() != null && !chatItem.getPinChatPhoto().isEmpty()){
            chatType = ChatType.IMAGE;
        }

        if(chatItem.getPinChatVideoLink() != null && !chatItem.getPinChatVideoLink().isEmpty()){
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
        if(chatItem.getLiked() != null && chatItem.getLiked()){
            holder.likeCountText.setText(chatItem.getPinChatLikeCount());
            holder.likeImage.setImageResource(R.mipmap.ic_heart_red);
        }else{
            holder.likeCountText.setText("");
            holder.likeImage.setImageResource(R.mipmap.ic_grey_heart);
        }
        holder.likeImage.setTag(position);
        holder.likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                int likeCount = 0;
                try {
                    Integer.parseInt(mChatItems.get(position).getPinChatLikeCount());
                    likeCount += 1;
                }catch (Exception e){
                    e.printStackTrace();
                    likeCount = 1;
                }

                ImageView heartImage = (ImageView)v;
                if(likeCount > 0) {
                    mChatItems.get(position).setPinChatLikeCount(likeCount + "");
                    mChatItems.get(position).setLiked(true);
                    heartImage.setImageResource(R.mipmap.ic_heart_red);
                    LikePinChatWithID(mChatItems.get(position).getPinChatId());
                }
            }
        });

        String message = chatItem.getPinChatMessage();
        if(message != null && !message.isEmpty()){
            holder.message.setText(chatItem.getPinChatMessage());
        }else{
            holder.message.setText("");
        }

        if(chatType == ChatType.VIDEO)
            holder.videoPlayIcon.setVisibility(View.VISIBLE);

        if(chatType != ChatType.MESSAGE){
            String imageUrl = chatItem.getPinChatPhoto();
            if(chatType == ChatType.VIDEO) {
                String videoPhotoLink = chatItem.getPinChatVideoLink();
                videoPhotoLink = videoPhotoLink.replace(".mp4", "_thumb.png");
                videoPhotoLink = videoPhotoLink.replace("videos/", "videos/thumbnails/");
                imageUrl = videoPhotoLink;

                Log.d(TAG, "---------------------- VIDEO image link = " + imageUrl);
            }
            imageLoader.displayImage(imageUrl, holder.photo);
            holder.photo.setVisibility(View.VISIBLE);
        }

        holder.dateText.setText(chatItem.getDistance() + " - " + MapPinControl.ConvertTimeToAgo(chatItem.getPinChatDateCreated()));

        holder.reportText.setTag(chatItem);
        holder.reportText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PinDetailModel model = (PinDetailModel) v.getTag();
                ReportChatWithID(MainTabActivity.userid, model.getPinChatId(), "Pin Chat");
            }
        });

        holder.replyText.setTag(chatItem);
        holder.replyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PinDetailModel model = (PinDetailModel) v.getTag();
                mFragment.editText.setText("@" + model.getFullname() + " ");
                mFragment.editText.requestFocus();
                mFragment.editText.setSelection(mFragment.editText.getText().length());

                replyId = model.getUserId();
                InputMethodManager imm = (InputMethodManager) main.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mFragment.editText, InputMethodManager.SHOW_IMPLICIT);

            }
        });

        if(chatItem.getPinChatVideoLink() != null && !chatItem.getPinChatVideoLink().isEmpty())
            setupJwPlayer(holder.jwPlayerView, holder.photo, holder.videoPlayIcon, chatItem.getPinChatVideoLink());

        return view;
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
                    }else{
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


    private void LikePinChatWithID( final String chatId)
    {
        final String userid = MainTabActivity.userid;

        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().likePinChatWithID(userid, chatId, new SvcApiRestCallback<CommonRespose>() {
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

                    if (!responseStatus.equals("true")) {
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
