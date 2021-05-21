package com.example.planner;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "CHANNEL_SAMPLE";



    @Override
    public void onReceive(Context context, Intent intent) {

            // Get id & message
        String notificationId = intent.getStringExtra("notificationId");
            long days = intent.getLongExtra("message",0);


            Intent mainIntent = new Intent(context, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

            // NotificationManager
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For API 26 and above
                CharSequence channelName = "My Notification";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
                notificationManager.createNotificationChannel(channel);
            }

            // Prepare Notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                    .setContentTitle(notificationId)
                    .setContentText(days+" left of "+notificationId)
                    .setContentIntent(contentIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);


            notificationManager.notify(Integer.parseInt(String.valueOf(days)), builder.build());
        }

    }


