package com.example.caleb.myjourney;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class PlayFragment extends Fragment {


    public PlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.airport_list, container, false);

        final ArrayList<AirportAmenities> Play = new ArrayList<>();

        Play.add(new AirportAmenities("Butterfly Garden", "T3", R.drawable.play1));
        Play.add(new AirportAmenities("Children's Playground", "T3", R.drawable.play2));
        Play.add(new AirportAmenities("Movie Theatre", "T3", R.drawable.play3));
        Play.add(new AirportAmenities("Amore Fitness", "T2", R.drawable.play4));
        Play.add(new AirportAmenities("Sun Paradise", "T1", R.drawable.play5));
        Play.add(new AirportAmenities("Orchid Garden", "T2", R.drawable.play6));
        Play.add(new AirportAmenities("Kinetic Rain", "T1 ", R.drawable.play7));
        Play.add(new AirportAmenities("The Slide@T3", "T3", R.drawable.play8));
        Play.add(new AirportAmenities("Entertainment Deck", "T2", R.drawable.play9));
        Play.add(new AirportAmenities("Spa", "T2", R.drawable.play10));

        AmenitiesAdapter adapter = new AmenitiesAdapter(getActivity(), Play);
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        return rootView;

    }


}
