package com.example.caleb.myjourney;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class ShopFragment extends Fragment {


    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.airport_list, container, false);

        final ArrayList<AirportAmenities> Shop = new ArrayList<>();

        Shop.add(new AirportAmenities("Burberry", "02-10", R.drawable.shop1));
        Shop.add(new AirportAmenities("Adidas", "01-15", R.drawable.shop2));
        Shop.add(new AirportAmenities("Money Changer", "02-22", R.drawable.shop3));
        Shop.add(new AirportAmenities("DFS", "02-40", R.drawable.shop4));
        Shop.add(new AirportAmenities("Gucci", "01-11", R.drawable.shop5));
        Shop.add(new AirportAmenities("Sony", "02-32", R.drawable.shop6));
        Shop.add(new AirportAmenities("7-eleven", "02-01", R.drawable.shop7));
        Shop.add(new AirportAmenities("Rolex", "02-13", R.drawable.shop8));
        Shop.add(new AirportAmenities("Times", "01-18", R.drawable.shop9));
        Shop.add(new AirportAmenities("ALDO", "02-04", R.drawable.shop10));


        AmenitiesAdapter adapter = new AmenitiesAdapter(getActivity(), Shop);
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        return rootView;
    }
}
