package com.example.caleb.myjourney;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by beverly on 10/6/2016.
 */

public class AlarmReceiver extends BroadcastReceiver
{
    private String departureTime, gate;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "Alarm! Flight is departing soon!", Toast.LENGTH_LONG).show();
        Bundle bundle = intent.getExtras();
        departureTime = bundle.getString("departureTime");
        gate = bundle.getString("gate");

        NotificationCompat.Builder notifBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Your plane is departing soon at " + departureTime + ".")
                        .setContentText("Kindly make your way to Gate: " + gate + ".");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notifBuilder.build());

        //Toast.makeText(context, "poop", Toast.LENGTH_LONG).show();
    }

}
