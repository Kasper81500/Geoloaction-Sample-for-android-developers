package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
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

import pf.paranoidfan.com.paranoidfan.Helper.MeetMeType;
import pf.paranoidfan.com.paranoidfan.Model.CommonRespose;
import pf.paranoidfan.com.paranoidfan.Model.MeetMeModel;
import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

/**
 * Created by KasperStar on 8/20/2016.
 */
public class MeetMeListAdapter extends BaseAdapter {

    public static String TAG = MeetMeListAdapter.class.getSimpleName();
    ArrayList<MeetMeModel> mMeetMeList;
    MeetMeListActivity main;
    private ImageLoader imageLoader;
    boolean delayLock = false;

    public boolean isShare = false;
    public boolean showGroups = false;
    public String pinId = "";
    public String mMeetMeText = "";
    public boolean isInvite = false;
    public boolean isGroupInvite = false;
    public String groupId = "";

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    MeetMeListAdapter(Activity mainActivity){

        main = (MeetMeListActivity)mainActivity;
        mMeetMeList = new ArrayList<MeetMeModel>();

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

    public void addItems(ArrayList<MeetMeModel> list) {
        for(MeetMeModel item: list){
            mMeetMeList.add(item);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMeetMeList.size();
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public int getItemViewType(int position) {
        return mMeetMeList.get(position).isHeader ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return mMeetMeList.get(position);
    }

    static class ViewHolder{
        ImageView avatarImage;
        TextView fullNameText;
        TextView phoneText;
        Button selectedButton;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        MeetMeModel meetMeItem = mMeetMeList.get(position);
        int rowType = getItemViewType(position);

        if (view == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_SEPARATOR:
                    view = main.getLayoutInflater().inflate(R.layout.cell_meetme_header, null);
                    holder.fullNameText = (TextView) view.findViewById(R.id.txt_header);
                    break;
                case TYPE_ITEM:
                    view = main.getLayoutInflater().inflate(R.layout.cell_meetme, null);
                    holder.avatarImage = (ImageView) view.findViewById(R.id.img_avatar);
                    holder.fullNameText = (TextView) view.findViewById(R.id.txt_fullname);
                    holder.phoneText = (TextView) view.findViewById(R.id.txt_phone);
                    holder.selectedButton = (Button) view.findViewById(R.id.btn_invite);
                    break;
            }

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(rowType == TYPE_ITEM) {
            holder.fullNameText.setText(meetMeItem.name);

            if(meetMeItem.phone != null && !meetMeItem.phone.isEmpty() && !meetMeItem.phone.equals("Group")) {
                holder.phoneText.setVisibility(View.VISIBLE);
                holder.phoneText.setText(meetMeItem.phone);
            }else {
                holder.phoneText.setVisibility(View.GONE);
            }

            imageLoader.displayImage(meetMeItem.avatar, holder.avatarImage);

            holder.selectedButton.setTag(position);
            holder.selectedButton.setOnClickListener(new View.OnClickListener() {
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

                    if(showGroups || isInvite) {
                        if (item.selected) {
                            if (item.meetMeType.equals(MeetMeType.Group))
                                main.selectedGroupList.add(item);
                            else if (item.meetMeType.equals(MeetMeType.Friend))
                                main.selectedFriendList.add(item);
                            else if (item.meetMeType.equals(MeetMeType.Contact))
                                main.selectedContractList.add(item);
                        } else {
                            if (item.meetMeType.equals(MeetMeType.Group))
                                main.selectedGroupList.remove(item);
                            else if (item.meetMeType.equals(MeetMeType.Friend))
                                main.selectedFriendList.remove(item);
                            else if (item.meetMeType.equals(MeetMeType.Contact))
                                main.selectedContractList.remove(item);
                        }
                    }else if(isShare){
                        SharePin(position);
                    }else if(isGroupInvite){
                        SendGroupInvite(position);
                    }else{
                        SendMeetMeReqeust(position);
                    }
                }
            });
        }else{
            holder.fullNameText.setText(meetMeItem.name);
        }
        return view;
    }

    private void SharePin(int position){
        String userId = MainTabActivity.userid;
        String pfUserId = mMeetMeList.get(position).pfUserId;
        String groupId = mMeetMeList.get(position).pfUserId;
        String phone = mMeetMeList.get(position).phone;

        Log.d(TAG, "user id = " + userId);
        Log.d(TAG, "group id = " + groupId);
        Log.d(TAG, "pfuser id = " + pfUserId);
        Log.d(TAG, "phone = " + phone);
        Log.d(TAG, "pin id = " + pinId);

        SvcApiService.getUserIdEndPoint().sharePin(userId, groupId, pfUserId, phone, pinId, new SvcApiRestCallback<CommonRespose>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
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
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertMessage("Error", "Invalid request");
                }
            }
        });
    }


    private void SendMeetMeReqeust(int position){
        MeetMeModel item = mMeetMeList.get(position);
        String userId = MainTabActivity.userid;
        String phone = item.phone;
        String pfUserId = item.pfUserId;
        String message = mMeetMeText;

        SvcApiService.getUserIdEndPoint().sendMeetMeRequest(userId, phone, pfUserId, message, new SvcApiRestCallback<CommonRespose>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
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

                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertMessage("Error", "Invalid request");
                }
            }
        });
    }


    private void SendGroupInvite(int position){
        MeetMeModel item = mMeetMeList.get(position);
        String userId = MainTabActivity.userid;
        String groupId = this.groupId;
        String pfUserId = item.pfUserId;
        String phone = item.phone;
        String message = mMeetMeText;

        SvcApiService.getUserIdEndPoint().sendGroupInvite(groupId, userId, pfUserId, phone, message, new SvcApiRestCallback<CommonRespose>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
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

                } catch (Exception e) {
                    e.printStackTrace();
                    showAlertMessage("Error", "Invalid request");
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
