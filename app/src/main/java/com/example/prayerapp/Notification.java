package com.example.prayerapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Random;

public class Notification extends BroadcastReceiver {

    String time,name;
    private static final String PRIMARY_CHANNEL_ID = "notification_channel";
    private NotificationManager mNotifyManager;
    public Calendar c;
    Random random = new Random();
    public int m = random.nextInt(9999 - 1000) + 1000;

    @Override
    public void onReceive(Context context, Intent intent) {

        //create channel
        createNotificationChannel(context);

        name = intent.getStringExtra("name");
        time = intent.getStringExtra("time");

        //Send the notification
        sendNotification(context);

    }

    public void createNotificationChannel(Context context) {

        mNotifyManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Reminder Notification", NotificationManager
                    .IMPORTANCE_HIGH);//-------------------------------------------------------------->CHANGE MASCOT
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification for prayer");//---------------->Change Mascot
            mNotifyManager.createNotificationChannel(notificationChannel);
        }

    }//End createNotificationCH

    public void sendNotification(Context context) {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(context);
        mNotifyManager.notify(random.nextInt(9999 - 1000) + 1000, notifyBuilder.build());
    }//End Send notification

    public  NotificationCompat.Builder getNotificationBuilder(Context context){

        Intent notifyIntent = new Intent(context, Home.class);
        //add details of reminder to the notification
        notifyIntent.putExtra("name",name);
        notifyIntent.putExtra("time",time);

// Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

// Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, random.nextInt(9999 - 1000) + 1000, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle(name)
                .setContentText("Prayer reminder")
                .setSmallIcon(R.drawable.ic_andriod)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true);
        return notifyBuilder;
    }//end getNotificationBuilder
}
