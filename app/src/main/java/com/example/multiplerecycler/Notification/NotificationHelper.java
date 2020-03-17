package com.example.multiplerecycler.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;

public class NotificationHelper extends ContextWrapper {
        public static final String CHANNEL1_ID="CHANNEL1";
        public static final String CHANNEL1_NAME="FIRST_CHANNEL";



        public NotificationHelper(Context base) {
        super(base);
        
        createNotificationChannel();
    }

    private void createNotificationChannel() {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel channel_1 = new NotificationChannel(CHANNEL1_ID,CHANNEL1_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel_1.setDescription("This is for reminders");
            NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel_1);


        }

    }


}
