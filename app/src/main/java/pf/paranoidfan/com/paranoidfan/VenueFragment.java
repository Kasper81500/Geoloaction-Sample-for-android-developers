package pf.paranoidfan.com.paranoidfan;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pf.paranoidfan.com.paranoidfan.Model.PinTypeModel;

public class VenueFragment extends Fragment {

    public VenueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_venue, container, false);
        ImageView foodDrink = (ImageView)view.findViewById(R.id.img_fooddrink);
        foodDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.FoodDrink);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });
        ImageView medicalCare = (ImageView)view.findViewById(R.id.img_medical);
        medicalCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.MedicalCare);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });
        ImageView apparel = (ImageView)view.findViewById(R.id.img_apparel);
        apparel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Apparel);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });
        ImageView police = (ImageView)view.findViewById(R.id.img_police);
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Police);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });
        ImageView ticket = (ImageView)view.findViewById(R.id.img_ticket);
        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Ticket);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });
        ImageView parking = (ImageView)view.findViewById(R.id.img_parking);
        parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Parking);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });
        ImageView beer = (ImageView)view.findViewById(R.id.img_beer);
        beer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Beer);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });
        ImageView taxi = (ImageView)view.findViewById(R.id.img_taxi);
        taxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Taxi);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });
        ImageView broadcast = (ImageView)view.findViewById(R.id.img_broadcast);
        broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Broadcast);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });
        ImageView rickshaw = (ImageView)view.findViewById(R.id.img_rickshaw);
        rickshaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Rickshaw);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });
        ImageView restroom = (ImageView)view.findViewById(R.id.img_restroom);
        restroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.Restroom);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });
        ImageView entryexit = (ImageView)view.findViewById(R.id.img_entry);
        entryexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePinFragment fragment = CreatePinFragment.newInstance(PinTypeModel.EntryExit);
                ((MainTabActivity) getActivity()).addFragment(fragment, "");
            }
        });


        return view;


    }
}
