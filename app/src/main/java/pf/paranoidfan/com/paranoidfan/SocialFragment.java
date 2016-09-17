package pf.paranoidfan.com.paranoidfan;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pf.paranoidfan.com.paranoidfan.Model.PinTypeModel;

public class SocialFragment extends Fragment {

    public static String TAG = SocialFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        ImageView tailgate = (ImageView)view.findViewById(R.id.img_tailgate);
        tailgate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Tailgate);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });

        ImageView partying = (ImageView)view.findViewById(R.id.img_partying);
        partying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Partying);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });

        ImageView gameShowing = (ImageView)view.findViewById(R.id.img_showing);
        gameShowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.GameShowing);
                ((MainTabActivity)getActivity()).addFragment(fragment, "CreatePin");
            }
        });

        ImageView watchParty = (ImageView)view.findViewById(R.id.img_watchparty);
        watchParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.WatchParty);
                ((MainTabActivity)getActivity()).addFragment(fragment, "CreatePin");
            }
        });

        ImageView playing = (ImageView)view.findViewById(R.id.img_playing);
        playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Playing);
                ((MainTabActivity)getActivity()).addFragment(fragment, "CreatePin");
            }
        });

        ImageView celebrity = (ImageView)view.findViewById(R.id.img_celebrity);
        celebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Celebrity);
                ((MainTabActivity)getActivity()).addFragment(fragment, "CreatePin");
            }
        });

        ImageView music = (ImageView)view.findViewById(R.id.img_music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Music);
                ((MainTabActivity)getActivity()).addFragment(fragment, "CreatePin");
            }
        });

        ImageView treasure = (ImageView)view.findViewById(R.id.img_treasure);
        treasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Treasure);
                ((MainTabActivity)getActivity()).addFragment(fragment, "CreatePin");
            }
        });

        ImageView meetup = (ImageView)view.findViewById(R.id.img_meetup);
        meetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Meetup);
                ((MainTabActivity)getActivity()).addFragment(fragment, "CreatePin");
            }
        });
        return view;
    }
}
