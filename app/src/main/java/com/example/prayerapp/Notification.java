package com.example.prayerapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Random;

public class Notification extends BroadcastReceiver {

    String time,name;
    private static final String FAJR_CHANNEL_ID = "ch1";
    private static final String DUHUR_CHANNEL_ID = "ch2";

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

        switch(name){
            case "Fajr":
                sendNotificationFajr(context);
                break;
            case "Dhuhr":
                sendNotificationDuhur(context);
                break;
            default:
                Log.e("error" , "error in send !!!");

        }

    }

    public void createNotificationChannel(Context context) {

        mNotifyManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create a fajer Channel
            NotificationChannel fajrChannel = new NotificationChannel(FAJR_CHANNEL_ID,
                    "Reminder Notification", NotificationManager
                    .IMPORTANCE_HIGH);//-------------------------------------------------------------->CHANGE MASCOT
            fajrChannel.enableLights(true);
            fajrChannel.setLightColor(Color.RED);
            fajrChannel.enableVibration(true);
            fajrChannel.setDescription("fajr prayer");//---------------->Change Mascot
            mNotifyManager.createNotificationChannel(fajrChannel);

            //create duhur Channel

            NotificationChannel duhurChannel = new NotificationChannel(DUHUR_CHANNEL_ID,
                    "Reminder Notification", NotificationManager
                    .IMPORTANCE_HIGH);//-------------------------------------------------------------->CHANGE MASCOT
            duhurChannel.enableLights(true);
            duhurChannel.setLightColor(Color.RED);
            duhurChannel.enableVibration(true);
            duhurChannel.setDescription("Duhur prayer");//---------------->Change Mascot
            mNotifyManager.createNotificationChannel(duhurChannel);
        }

    }//End createNotificationCH

    public void sendNotificationFajr(Context context) {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(context , FAJR_CHANNEL_ID);
        mNotifyManager.notify(random.nextInt(9999 - 1000) + 1000, notifyBuilder.build());
    }//End Send notification

    public void sendNotificationDuhur(Context context){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(context , DUHUR_CHANNEL_ID);
        mNotifyManager.notify(random.nextInt(9999 - 1000) + 1000, notifyBuilder.build());
    }

    public  NotificationCompat.Builder getNotificationBuilder(Context context , String channel){

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

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context, channel)
                .setContentTitle(name)
                .setContentText("Prayer reminder")
                .setSmallIcon(R.drawable.ic_andriod)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true);
        return notifyBuilder;
    }//end getNotificationBuilder
}
