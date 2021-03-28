package com.example.prayerapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Random;

public class Notification extends BroadcastReceiver {

    int id=0;
    private static final String ATHKAR_CHANNEL_ID = "ch1";
    NotificationManager mNotifyManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        mNotifyManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE );

        NotificationChannel AthkarChannel = new NotificationChannel(ATHKAR_CHANNEL_ID,
                "Reminder Notification", NotificationManager.IMPORTANCE_HIGH);
        AthkarChannel.enableLights(true);
        AthkarChannel.setLightColor(Color.RED);
        AthkarChannel.enableVibration(true);
        AthkarChannel.setDescription("prayer");
        mNotifyManager.createNotificationChannel(AthkarChannel);

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, Athkar.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, ATHKAR_CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("تطبيق الصلاة")
                .setContentText("ابدأ يومك بأذكار الصباح واختم بأذكار المساء")
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent);
        notificationManager.notify(id, mNotifyBuilder.build());
        id++;
    }
}
