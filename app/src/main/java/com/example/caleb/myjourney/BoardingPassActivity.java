package com.example.caleb.myjourney;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BoardingPassActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Flight flightInfo;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private View.OnClickListener mCorkyListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.v("Boarding Pass Activity", "clicked");
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding_pass);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = "Boarding Pass";

        mDrawerLayout.setOnClickListener(mCorkyListener);
        //mDrawerList.setOnItemClickListener(mCorkyListener);

        Intent intent = getIntent();
        flightInfo = (Flight) intent.getSerializableExtra("flightInfo");

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mActivityTitle);

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);


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

        TextView flightNo = (TextView) findViewById(R.id.flightNumber);
        TextView departure = (TextView) findViewById(R.id.departure);
        TextView departureCity = (TextView) findViewById(R.id.departureCity);
        TextView departureTerminal = (TextView) findViewById(R.id.departureTerminal);
        TextView duration = (TextView) findViewById(R.id.duration);
        TextView arrivalCity = (TextView) findViewById(R.id.arrivalCity);
        TextView arrival = (TextView) findViewById(R.id.arrival);
        TextView arrivalTerminal = (TextView) findViewById(R.id.arrivalTerminal);

        flightNo.setText("Flight " + flightInfo.getAirlineCode() + " " + flightInfo.getFlightNumber());
        departure.setText(flightInfo.getAirportCode() + " " + scheduled);
        departureTerminal.setText(tempSplit[0] + ", Changi Airport Terminal " + flightInfo.getTerminal());
        duration.setText("Total travelling time: " + flightInfo.getDuration() / 60 + " hours " + flightInfo.getDuration() % 60 + " minutes");
        arrival.setText(flightInfo.getAirportCode2() + " " + String.format("%02d", h) + ":" + String.format("%02d", m));
        arrivalCity.setText(flightInfo.getCity());
        arrivalTerminal.setText(dateSplit[0] + "-" + dateSplit[1] + "-" + String.format("%02d", day));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        super.onBackPressed();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            finish();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.boarding_pass, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        super.onBackPressed();

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addDrawerItems() {
        String[] osArray = {"Search Flights", "My Journey", "Check In", "Krisflyer", "Login", "Settings"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MyJourneyActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();

                switch (position) {
                    case 1:
                        Intent journey = new Intent(BoardingPassActivity.this, MyJourneyActivity.class);

                        // passing on the variables  needed in My Journey
                        journey.putExtra("waitTime", -1);
                        journey.putExtra("statusText", flightInfo.getStatusText());
                        journey.putExtra("scheduled", flightInfo.getScheduled());
                        journey.putExtra("terminal", flightInfo.getTerminal());
                        journey.putExtra("city", flightInfo.getCity());
                        journey.putExtra("flightInfo", flightInfo);
                        startActivity(journey);
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
}
