package pf.paranoidfan.com.paranoidfan;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pf.paranoidfan.com.paranoidfan.Model.PinTypeModel;

public class ConnectFragment extends Fragment {
    View fragmentView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect, container, false);

        ImageView note = (ImageView)view.findViewById(R.id.img_note);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Note);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });

        ImageView friendList = (ImageView)view.findViewById(R.id.img_friendlist);
        friendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendListActivity.class);
                getActivity().startActivity(intent);
            }
        });

        ImageView meetme = (ImageView)view.findViewById(R.id.img_meetme);
        meetme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MeetMeActivity.class);
                getActivity().startActivity(intent);
            }
        });

        ImageView leaderboard = (ImageView)view.findViewById(R.id.img_leaderboard);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LeaderboardActivity.class);
                getActivity().startActivity(intent);
            }
        });

        ImageView profile = (ImageView)view.findViewById(R.id.img_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });

        ImageView groups = (ImageView)view.findViewById(R.id.img_groups);
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GroupsActivity.class);
                getActivity().startActivity(intent);
            }
        });

        fragmentView = view;
        return view;

    }
}
