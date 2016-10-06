package com.example.caleb.myjourney;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by beverly on 10/6/2016.
 */

public class AlarmReceiver extends BroadcastReceiver
{

    public AlarmReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "Alarm! Flight is departing soon!", Toast.LENGTH_LONG).show();
    }
}
