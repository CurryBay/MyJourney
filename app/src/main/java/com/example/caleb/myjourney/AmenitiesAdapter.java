package com.example.caleb.myjourney;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tinghaong on 6/10/16.
 */

public class AmenitiesAdapter extends ArrayAdapter {


    public AmenitiesAdapter(Activity context, ArrayList<AirportAmenities> Amenities) {
        super(context,0,Amenities);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View list_item_view = convertView;
        if (list_item_view == null)
            list_item_view = LayoutInflater.from(getContext()).inflate(R.layout.airport_list, parent, false);

        AirportAmenities currentAmenity = (AirportAmenities)getItem(position);

        TextView text = (TextView)list_item_view.findViewById(R.id.text);
        text.setText(currentAmenity.getAmenitiesName());

        ImageView image = (ImageView)list_item_view.findViewById(R.id.image);
        image.setImageResource(currentAmenity.getImageId());

        return super.getView(position, convertView, parent);
    }
}
