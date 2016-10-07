package com.example.caleb.myjourney;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import static com.example.caleb.myjourney.R.id.departure;

public class MyJourneyActivity extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader = new ArrayList<String>();
    HashMap<String, List<ListItem>> listDataChild = new HashMap<String, List<ListItem>>();
    int currentpos = -1, waittime = -1;
    Context c;
    Intent boardingPass;
    private Flight flightInfo = null;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String scheduled;
    private int hours;
    private int min;
    private String remainingTime;
    private String flight_number2;
    private String gate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_journey);

        mDrawerList = (ListView)findViewById(R.id.navList2);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout2);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        flightInfo = (Flight) intent.getSerializableExtra("flightInfo");
        waittime =intent.getExtras().getInt("waitTime");
        flight_number2 = intent.getExtras().getString("flight_number2");
        String tempSchedule = flightInfo.getScheduled();
        String[] tempSplit = tempSchedule.split("T");
        String[] dateSplit = tempSplit[0].split("-");
        final String[] timeInfo = tempSplit[1].split(":");
        scheduled= timeInfo[0] + ":" + timeInfo[1];


        //Log.v("MyJourneyActivity", "flightInfo.getStatusText() is: " + flightInfo.getStatusText());

        TextView cityname = (TextView) findViewById(R.id.cityname);
        cityname.setText(flightInfo.getCity());

        boardingPass = new Intent(MyJourneyActivity.this, BoardingPassActivity.class);
        boardingPass.putExtra("flightInfo", flightInfo);

        sendGetRequestFlightDetails();
        gate = intent.getExtras().getString("gate");
        if (gate == null) {
            gate = "Not Available";
        }


        Log.i("======= Hours"," :: "+ hours);

            // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        //prepareListData();

        c = this;
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, flightInfo);

        // setting list adapter
        expListView.setAdapter(listAdapter);

                Calendar currentTime = Calendar.getInstance(TimeZone.getDefault());
                Date currentLocalTime = currentTime.getTime();
                DateFormat date = new SimpleDateFormat("HH:mm a");
                date.setTimeZone(TimeZone.getDefault());

                String localTime = date.format(currentLocalTime);
                Log.v("MyJourney", localTime);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                Date date1 = simpleDateFormat.parse(localTime, new ParsePosition(0));
                Date date2 = simpleDateFormat.parse(scheduled, new ParsePosition(0));

                long difference = date2.getTime() - date1.getTime();
                int days = (int) (difference / (1000*60*60*24));
                hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
                min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
                hours = (hours < 0 ? -hours : hours);
                min = (min < 0 ? -min : min);
        remainingTime = hours + "h " + min + "m";





        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                if (groupPosition != currentpos && currentpos != -1){
                    expListView.collapseGroup(currentpos);
                }
                currentpos = groupPosition;
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                switch(groupPosition){
                    case 0: //Pre-Flight
                        switch(childPosition){
                            case 0: //Manage Booking Details
                                goToManageBooking();
                                break;
                            case 1: goToVisa();
                                break;
                            case 2:
                                Intent explore = new Intent(MyJourneyActivity.this, ExploreActivity.class);
                                explore.putExtra("waitTime", waittime);
                                explore.putExtra("flight_number2", flight_number2);
                                explore.putExtra("flightInfo", flightInfo);
                                startActivity(explore);
                        }
                        break;
                    case 1: //Checkin
                        switch(childPosition){
                            case 0: //Check in online
                                goToOnlineCheckIn();
                                break;
                            case 3:
                                startActivity(boardingPass);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 2: //departure
                        switch(childPosition){
                            case 0:
                                Intent alarm = new Intent(MyJourneyActivity.this, Alarm.class);
                                alarm.putExtra("scheduled", scheduled);
                                alarm.putExtra("hour", timeInfo[0]);
                                alarm.putExtra("minute", timeInfo[1]);
                                startActivity(alarm);
                                break;
                            case 3:
                                startActivity(boardingPass);
                                break;
                            case 4:
                                Intent map = new Intent(MyJourneyActivity.this, AirportMap.class);
                                map.putExtra("mapID", 0);
                                map.putExtra("flightInfo", flightInfo);
                                startActivity(map);
                                break;
                        }
                        break;
                    case 3: //transit
                        switch(childPosition){
                            case 3:
                                startActivity(boardingPass);
                                break;
                            case 4:
                                Intent map = new Intent(MyJourneyActivity.this, AirportMap.class);
                                map.putExtra("mapID", 1);
                                map.putExtra("flightInfo", flightInfo);
                                startActivity(map);
                                break;
                        }
                        break;
                    case 4: //Arrival
                        switch(childPosition) {
                            case 1:
                                Intent map = new Intent(MyJourneyActivity.this, AirportMap.class);
                                map.putExtra("mapID", 2);
                                map.putExtra("flightInfo", flightInfo);
                                startActivity(map);

                            case 2: //explore
                        }
                        break;
                }


                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<ListItem>>();

        // Adding child data
        listDataHeader.add("Pre-Flight");
        listDataHeader.add("Check In");
        listDataHeader.add("Departure");
        listDataHeader.add("Transit");
        listDataHeader.add("Arrival");

        // Adding child data
        List<ListItem> preFlight = new ArrayList<>();
        preFlight.add(new ListItem("Manage Booking Details", true));
        preFlight.add(new ListItem("Apply Visa", true));
        preFlight.add(new ListItem("Explore", true));

        List<ListItem> checkIn = new ArrayList<>();
        checkIn.add(new ListItem("Check In Online", true));
        checkIn.add(new ListItem("Estimated Waiting Time: " + waittime + " minutes"));
        checkIn.add(new ListItem("Flight Status: " + flightInfo.getStatusText()));
        checkIn.add(new ListItem("Flight Details", true));

        List<ListItem> departure = new ArrayList<>();
        departure.add(new ListItem("Time left to departure: " + remainingTime, true));
        departure.add(new ListItem("Terminal: " + flightInfo.getTerminal()));
        departure.add(new ListItem("Gate: " + gate));
        departure.add(new ListItem("Flight Details", true));
        departure.add(new ListItem("Map", true));

        List<ListItem> transit = new ArrayList<>();
        transit.add(new ListItem("Time left to departure: " + remainingTime, true));
        transit.add(new ListItem("Terminal: " + flightInfo.getTerminal()));
        transit.add(new ListItem("Gate: " + gate));
        transit.add(new ListItem("Flight Details", true));
        transit.add(new ListItem("Map", true));


        List<ListItem> arrival = new ArrayList<>();
        arrival.add(new ListItem("Flight Status: " + flightInfo.getStatusText()));
        arrival.add(new ListItem("Map", true));
        arrival.add(new ListItem("Explore", true));

        listDataChild.put(listDataHeader.get(0), preFlight); // Header, Child data
        listDataChild.put(listDataHeader.get(1), checkIn);
        listDataChild.put(listDataHeader.get(2), departure);
        listDataChild.put(listDataHeader.get(3), transit);
        listDataChild.put(listDataHeader.get(4), arrival);
    }

    public void sendGetRequestFlightDetails() {
        new MyJourneyActivity.GetFlightDetails(this).execute();
    }

    private void addDrawerItems() {
        String[] osArray = {"Home", "My Journey", "Explore", "Boarding Pass", "Login", "Settings"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent main = new Intent(MyJourneyActivity.this, MainActivity.class);
                        startActivity(main);
                        break;
                    case 1:
                        closeOptionsMenu();
                        closeContextMenu();
                        break;
                    case 2:
                        try {
                            Intent explore = new Intent(MyJourneyActivity.this, ExploreActivity.class);
                            explore.putExtra("waitTime", waittime);
                            explore.putExtra("flight_number2", flight_number2);
                            explore.putExtra("statusText", flightInfo.getStatusText());
                            explore.putExtra("scheduled", flightInfo.getScheduled());
                            explore.putExtra("terminal", flightInfo.getTerminal());
                            explore.putExtra("city", flightInfo.getCity());
                            startActivity(explore);
                        } catch (Exception e) {
                            Toast.makeText(MyJourneyActivity.this, "No Flight Number Entered!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        startActivity(boardingPass);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    // NAVIGATION DRAWER DETAILS

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_journey, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToVisa() {
        goToUrl ( "http://visacentral.sg/requirements");
    }

    public void goToOnlineCheckIn() {
        goToUrl("https://www.singaporeair.com/en_UK/plan-and-book/check-in-online/");
    }

    public void goToManageBooking() {
        goToUrl("https://www.singaporeair.com/en_UK/plan-and-book/managebooking/");
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchWeb);
    }

    private class GetFlightDetails extends AsyncTask<String, Void, Integer> {

        private final Context context;

        public GetFlightDetails(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {

                // final TextView outputView = (TextView) findViewById(R.id.showOutput);
                final String baseURL = "https://flifo-qa.api.aero/flifo/v3/flight";
                Uri builtUri = Uri.parse(baseURL).buildUpon()
                        .appendPath("sin")
                        .appendPath("sq")
                        .appendPath(flight_number2)
                        .appendPath("d")
                        .build();
                Log.v("MainActivity", builtUri.toString());
                URL url = new URL(builtUri.toString());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("X-apiKey", "2cfd0827f82ceaccae7882938b4b1627");

                BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                final StringBuilder responseStrBuilder = new StringBuilder();
                String inputStr = "";
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
                Log.v("MainActivity", "String is : " + responseStrBuilder.toString());

                Flight flight_info = null;
                try {
                    JSONObject test = new JSONObject(responseStrBuilder.toString());
                    JSONArray flightRecord = test.getJSONArray("flightRecord");
                    JSONObject c = flightRecord.getJSONObject(0);
                    flight_info = new Flight(c);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //flightInfo = flight_info;
                Log.v("flightInfo", "TEST TEST TEST" + flightInfo.getStatusText());
                MyJourneyActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                       /* outputView.setText(responseStrBuilder);
                        progress.dismiss(); */
                    }
                });


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {
            Log.v("TEST", "Post executed");

            prepareListData();

            listAdapter = new ExpandableListAdapter(c, listDataHeader, listDataChild, flightInfo);

            // setting list adapter
            expListView.setAdapter(listAdapter);

            super.onPostExecute(null);
        }

    }

}
