package com.example.caleb.myjourney;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ServiceFragment extends Fragment {


    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.airport_list, container, false);

        final ArrayList<AirportAmenities> Service = new ArrayList<>();

        Service.add(new AirportAmenities("SilverKris Lounge", "T3 , T2", R.drawable.service1));
        Service.add(new AirportAmenities("KrisFlyer Gold Lounge", "T3, T2", R.drawable.service2));
        Service.add(new AirportAmenities("Baggage Storage", "02-03", R.drawable.service3));
        Service.add(new AirportAmenities("Tax Refund", "01-01", R.drawable.service4));
        Service.add(new AirportAmenities("Customer Service", "01-05", R.drawable.service5));
        Service.add(new AirportAmenities("Lost & Found", "01-17", R.drawable.service6));
        Service.add(new AirportAmenities("DBS Bank", "01-22", R.drawable.service7));
        Service.add(new AirportAmenities("Baby Care Room", "03-02", R.drawable.service8));
        Service.add(new AirportAmenities("Business Center", "03-41", R.drawable.service9));
        Service.add(new AirportAmenities("Ground Transport Desk", "01-10", R.drawable.service10));

        AmenitiesAdapter adapter = new AmenitiesAdapter(getActivity(), Service);
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        return rootView;
    }

}
