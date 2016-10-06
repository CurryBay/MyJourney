package com.example.caleb.myjourney;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.caleb.myjourney.R;

import static android.R.string.no;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<ListItem>> _listDataChild;
    private Flight flightInfo;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<ListItem>> listChildData, Flight flightInfo) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.flightInfo = flightInfo;

    }

    @Override
    public ListItem getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).getName();
        final boolean isButton = getChild(groupPosition, childPosition).isButton();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        ImageView button = (ImageView) convertView.findViewById(R.id.button);
        LinearLayout flightinfo = (LinearLayout) convertView.findViewById(R.id.flightinfo);

        String tempSchedule = flightInfo.getScheduled();
        String[] tempSplit = tempSchedule.split("T");
        String[] dateSplit = tempSplit[0].split("-");
        final String[] timeInfo = tempSplit[1].split(":");
        String scheduled = timeInfo[0] + ":" + timeInfo[1];
        int time = Integer.parseInt(timeInfo[0]) * 60 + Integer.parseInt(timeInfo[1]);
        int day = Integer.parseInt(dateSplit[2]);
        day += (time + flightInfo.getDuration()) / 1440;
        time = (time + flightInfo.getDuration()) % 1440;
        int h = time / 60;
        int m = time % 60;

        TextView flightNo = (TextView) convertView.findViewById(R.id.flightNumber);
        TextView departure = (TextView) convertView.findViewById(R.id.departure);
        TextView departureCity = (TextView) convertView.findViewById(R.id.departureCity);
        TextView departureTerminal = (TextView) convertView.findViewById(R.id.departureTerminal);
        TextView duration = (TextView) convertView.findViewById(R.id.duration);
        TextView arrivalCity = (TextView) convertView.findViewById(R.id.arrivalCity);
        TextView arrival = (TextView) convertView.findViewById(R.id.arrival);
        TextView arrivalTerminal = (TextView) convertView.findViewById(R.id.arrivalTerminal);

        flightNo.setText("Flight " + flightInfo.getAirlineCode() + " " + flightInfo.getFlightNumber());
        departure.setText(flightInfo.getAirportCode() + " " + scheduled);
        departureTerminal.setText(tempSplit[0] + ", Changi Airport Terminal " + flightInfo.getTerminal());
        duration.setText("Total travelling time: " + flightInfo.getDuration() / 60 + " hours " + flightInfo.getDuration() % 60 + " minutes");
        arrival.setText(flightInfo.getAirportCode2() + " " + String.format("%02d", h) + ":" + String.format("%02d", m));
        arrivalCity.setText(flightInfo.getCity());
        arrivalTerminal.setText(dateSplit[0] + "-" + dateSplit[1] + "-" + String.format("%02d", day));

        Log.v("MyJourneyActivity", "flight details");

        flightinfo.setVisibility(GONE);
        if ((groupPosition == 1 && childPosition == 2) || (groupPosition == 2 && childPosition == 3)) {
            flightinfo.setVisibility(VISIBLE);
        }

        txtListChild.setText(childText);

        if (isButton) {
            button.setVisibility(VISIBLE);
            Log.v("ExpandableListAdapter", "IS BUTTON");
        }
        else {
            button.setVisibility(GONE);
            Log.v("ExpandableListAdapter", "BUTTON RESET");
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        ImageView node = (ImageView) convertView.findViewById(R.id.node);

        if (isExpanded) {
            node.setImageResource(R.drawable.ic_radio_button_checked_white_24dp);
        }
        else {
            node.setImageResource(R.drawable.ic_radio_button_unchecked_white_24dp);
        }

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}