package pf.paranoidfan.com.paranoidfan;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

import pf.paranoidfan.com.paranoidfan.Model.TeamInfo;

/**
 * Created by KasperStar on 9/10/2016.
 */

public class GroupProfileTeamAdapter extends RecyclerView.Adapter<GroupProfileTeamAdapter.MyViewHolder> {
    public static ImageLoader imageLoader = null;
    private List<TeamInfo> teamList;
    Activity main;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            avatar = (ImageView) view.findViewById(R.id.img_avatar);
            name = (TextView) view.findViewById(R.id.txt_name);
        }
    }

    public GroupProfileTeamAdapter(Activity activity, List<TeamInfo> teamList) {
        this.teamList = teamList;
        main = activity;

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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_recycler_team, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TeamInfo team = teamList.get(position);
        imageLoader.displayImage(team.avater, holder.avatar);
        holder.name.setText(team.teamname);
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }
}