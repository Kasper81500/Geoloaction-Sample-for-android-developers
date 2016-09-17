package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.Model.GroupModel;

/**
 * Created by KasperStar on 8/26/2016.
 */
public class GroupsAdapter extends BaseAdapter {

    public static String TAG = LeaderboardAdapter.class.getSimpleName();
    ArrayList<GroupModel> mGroupList;
    GroupsActivity main;

    GroupsAdapter(Activity mainActivity, ArrayList<GroupModel> userList){

        mGroupList = new ArrayList<GroupModel>();

        mGroupList = userList;

        main = (GroupsActivity)mainActivity;
    }

    @Override
    public int getCount() {
        return mGroupList.size();
    }

    @Override
    public long getItemId(int position) {
        return  0;
    }

    @Override
    public Object getItem(int position) {
        return mGroupList.get(position);
    }

    static class ViewHolder{
        TextView fullNameText;
        Button messageButton;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        GroupModel groupItem = mGroupList.get(position);

        if (view == null) {
            view = main.getLayoutInflater().inflate(R.layout.cell_group, null);
            holder = new ViewHolder();
            holder.fullNameText = (TextView) view.findViewById(R.id.txt_fullname);
            holder.messageButton = (Button) view.findViewById(R.id.btn_message);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.fullNameText.setText(groupItem.getGroupName());
        holder.fullNameText.setTag(groupItem);
        holder.fullNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupModel groupItem = (GroupModel) v.getTag();
                Intent intent = new Intent(main, GroupProfileActivity.class);
                intent.putExtra("GroupModel", (Serializable) groupItem);
                main.startActivity(intent);
            }
        });

        holder.messageButton.setTag(groupItem);
        holder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupModel groupItem = (GroupModel) v.getTag();
                Intent intent = new Intent(main, GroupChatActivity.class);
                intent.putExtra("groupId", groupItem.getGroupId());
                main.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }
}
