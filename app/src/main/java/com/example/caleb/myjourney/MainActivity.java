package com.example.caleb.myjourney;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private int waitTime = -1;
    private Flight flightInfo = null;
    private String statusText, scheduled, terminal, city, gate;

    private String flight_number2;
    private String airlines;

    // not sure if these information are needed:
    // private String status, estimated, cityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button submitButton = (Button)findViewById(R.id.submit_button);
        final EditText flight_number = (EditText)findViewById(R.id.flight_number);

        flight_number.setText("SQ");
        Selection.setSelection(flight_number.getText(), flight_number.getText().length());

        flight_number.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().contains("SQ")){
                    flight_number.setText("SQ");
                    Selection.setSelection(flight_number.getText(), flight_number.getText().length());

                }

            }
        });

        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("EditText", flight_number.getText().toString());
                String tempFlightNo = flight_number.getText().toString();
                flight_number2 = tempFlightNo.replaceAll("[^0-9]", ""); // returns 123
                Log.v("EditText", flight_number2);
                sendGetRequestFlightDetails();
            }
        });


        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // running the different API calls to fetch data once the Main Activity starts
        sendGetRequestWaitTime();
    }

    // API CALL FOR WAIT TIME

    public void sendGetRequestWaitTime() {
        new GetWaitTime(this).execute();
    }

    private class GetWaitTime extends AsyncTask<String, Void, Void> {


        private final Context context;

        public GetWaitTime(Context c){
            this.context = c;
        }

        // private ProgressDialog progress;

        protected void onPreExecute(){
           // ProgressDialog progress= new ProgressDialog(this.context);
            //progress.setMessage("Loading");
            //progress.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            int wait_time = -1;
            try {

                //final TextView outputView = (TextView) findViewById(R.id.snippet1);
                URL url = new URL("https://waittime-qa.api.aero/waittime/v1/current/SIN");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                // connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("X-apiKey","8e2cff00ff9c6b3f448294736de5908a");

                BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                final StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr = "";
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
                Log.v("MainActivity","String is : " + responseStrBuilder.toString());

                try {
                    JSONObject test = new JSONObject(responseStrBuilder.toString());
                    JSONArray current = test.getJSONArray("current");
                    JSONObject c = current.getJSONObject(0);
                    wait_time = c.getInt("projectedMaxWaitMinutes");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.v("MainActivity", "this " + wait_time);

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //outputView.setText(responseStrBuilder);
                        //progress.dismiss();

                    }
                });


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            waitTime = wait_time;
            return null;
        }

        protected void onPostExecute(){
            super.onPostExecute(null);
        }

    }

    // API CALL FOR FLIGHT DETAILS

    public void sendGetRequestFlightDetails() {
        new GetFlightDetails(this).execute();
    }


    private class GetFlightDetails extends AsyncTask<String, Void, Integer> {

        private ProgressDialog progress;
        private final Context context;

        public GetFlightDetails(Context c){
            this.context = c;
        }

        protected void onPreExecute(){
            progress= new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {

                // final TextView outputView = (TextView) findViewById(R.id.showOutput);
                final String baseURL = "https://flifo-qa.api.aero/flifo/v3/flight";
                Uri builtUri = Uri.parse(baseURL).buildUpon()
                        .appendPath("fra")
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
                    Log.v("TEST", "flight_info created");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                flightInfo = flight_info;
                Log.v("flightInfo", "TEST TEST TEST");
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                       /* outputView.setText(responseStrBuilder);
                        progress.dismiss(); */
                    }
                });


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.v("MainActivity", "Please enter a valid flight");
               // Looper.prepare();
               // Toast.makeText(MainActivity.this, "Please enter a valid flight", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            return null;
    }

        protected void onPostExecute(Integer a){
            Intent journey = new Intent(context, MyJourneyActivity.class);
            journey.putExtra("waitTime", waitTime);
            journey.putExtra("flight_number2", flight_number2);
            journey.putExtra("statusText", flightInfo.getStatusText());
            journey.putExtra("scheduled", flightInfo.getScheduled());
            journey.putExtra("terminal", flightInfo.getTerminal());
            journey.putExtra("city", flightInfo.getCity());
            journey.putExtra("gate", flightInfo.getGate());
            progress.dismiss();
            startActivity(journey);
            super.onPostExecute(null);
        }

    }

    // NAVIGATION DRAWER DETAILS

    private void addDrawerItems() {
        String[] osArray = { "Search Flights", "My Journey", "Check In", "Krisflyer", "Login", "Settings" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();

                switch(position){
                    case 1:
                        if (flight_number2 != null) {
                            Intent journey = new Intent(MainActivity.this, MyJourneyActivity.class);
                            Log.v("MainActivity", flightInfo.getScheduled());
                            // passing on the variables  needed in My Journey
                              journey.putExtra("waitTime", waitTime);
                              journey.putExtra("flight_number2", flight_number2);
                              journey.putExtra("statusText", flightInfo.getStatusText());
                              journey.putExtra("scheduled", flightInfo.getScheduled());
                              journey.putExtra("terminal", flightInfo.getTerminal());
                              journey.putExtra("city", flightInfo.getCity());
                              journey.putExtra("gate", flightInfo.getGate());

                            Intent alarmReceiver = new Intent(MainActivity.this, AlarmReceiver.class);
                            alarmReceiver.putExtra("gate", flightInfo.getGate());

                            /* journey.putExtra("statusText", flightInfo.getStatusText());
                            journey.putExtra("statusText", flightInfo.getStatusText());
                            journey.putExtra("statusText", flightInfo.getStatusText()); */

                            startActivity(journey);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "No Flight Number Entered!", Toast.LENGTH_SHORT).show();
                        }
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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