package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pf.paranoidfan.com.paranoidfan.Model.TeamModel;

/**
 * Created by KasperStar on 8/27/2016.
 */
public class SelectTeamListAdapter extends BaseAdapter {

    public static String TAG = MeetMeListAdapter.class.getSimpleName();
    List<TeamModel> mTeamList;
    Activity main;

    SelectTeamListAdapter(Activity mainActivity, List<TeamModel> teamList){
        mTeamList = teamList;
        main = mainActivity;
    }

    @Override
    public int getCount() {
        return mTeamList.size();
    }

    @Override
    public long getItemId(int position) {
        return  0;
    }

    @Override
    public Object getItem(int position) {
        return mTeamList.get(position);
    }

    static class ViewHolder{
        TextView teamNameText;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        TeamModel meetMeItem = mTeamList.get(position);

        if (view == null) {
            view = main.getLayoutInflater().inflate(R.layout.cell_team, null);
            holder = new ViewHolder();
            holder.teamNameText = (TextView) view.findViewById(R.id.txt_teamname);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.teamNameText.setText(meetMeItem.getTeam());

        return view;
    }

}
