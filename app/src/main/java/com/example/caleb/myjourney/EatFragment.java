package com.example.caleb.myjourney;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class EatFragment extends Fragment {


    public EatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.airport_list, container, false);


        final ArrayList<AirportAmenities> Eat = new ArrayList<>();

        Eat.add(new AirportAmenities("4 Fingers Crispy Chicken", "B1-24", R.drawable.food1));
        Eat.add(new AirportAmenities("Macdonald's", "01-13", R.drawable.food2));
        Eat.add(new AirportAmenities("Dunkin' Donuts", "B1-02", R.drawable.food3));
        Eat.add(new AirportAmenities("PAUL", "02-15", R.drawable.food4));
        Eat.add(new AirportAmenities("Arteastig", "02-27", R.drawable.food5));
        Eat.add(new AirportAmenities("Auntie Anne's", "02-28", R.drawable.food6));
        Eat.add(new AirportAmenities("Starbucks", "02-41", R.drawable.food7));
        Eat.add(new AirportAmenities("KFC", "B1-10", R.drawable.food8));
        Eat.add(new AirportAmenities("Bengawan Solo", "01-07", R.drawable.food9));
        Eat.add(new AirportAmenities("Burger King", "02-32", R.drawable.food10));




        AmenitiesAdapter adapter = new AmenitiesAdapter(getActivity(), Eat);
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        return rootView;
    }

}
