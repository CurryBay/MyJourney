package com.example.caleb.myjourney;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {


    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.airport_list, container, false);

        /** TODO: Insert all the code from the NumberActivity’s onCreate() method after the setContentView method call */

        return rootView;
    }

}
