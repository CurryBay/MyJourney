package com.example.caleb.myjourney;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by tinghaong on 6/10/16.
 */

public class AirportMap extends AppCompatActivity {
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String scheduled;
    private String flight_number2;
    private String gate;
    int waittime = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        int mapChoice = i.getExtras().getInt("mapID");
        setContentView(R.layout.airport_map);
        ImageView airportMap = (ImageView)findViewById(R.id.airportMap);
        TextView airportText = (TextView)findViewById(R.id.airportName);
        switch (mapChoice) {
            case 0:
                airportText.setText("Changi Airport Terminal 3");
                airportMap.setImageResource(R.drawable.sin_t3);
                break;
            case 1:
                airportText.setText("Frankfurt Airport Terminal 1");
                airportMap.setImageResource(R.drawable.fra);
                break;
            case 2:
                airportText.setText("John F. Kennedy Airport Terminal 4");
                airportMap.setImageResource(R.drawable.jfk_t4);
                break;
        }

        Intent intent = getIntent();
        waittime = intent.getExtras().getInt("waitTime");
        flight_number2 = intent.getExtras().getString("flight_number2");
        //String tempSchedule = intent.getExtras().getString("scheduled");
        //String[] tempSplit = tempSchedule.split("T");
       // String[] timeInfo = tempSplit[1].split(":");
        //scheduled = timeInfo[0] + ":" + timeInfo[1];
       // gate = intent.getExtras().getString("gate");

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



    }

    // NAVIGATION DRAWER DETAILS

    private void addDrawerItems() {
        String[] osArray = {"Search Flights", "My Journey", "Check In", "Krisflyer", "Login", "Settings"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ExploreActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();

                switch (position) {
                    case 1:
                        Intent journey = new Intent(AirportMap.this, MyJourneyActivity.class);

                        // passing on the variables  needed in My Journey
                        journey.putExtra("waitTime", waittime);
                        journey.putExtra("flight_number2", flight_number2);
                        journey.putExtra("scheduled", scheduled);
                        journey.putExtra("gate", gate);
                        startActivity(journey);
                    default:
                        break;
                }
            }
        });
    }

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
}
