package sleidom.com.dailychallengeapp.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import sleidom.com.dailychallengeapp.MainActivity;
import sleidom.com.dailychallengeapp.R;

/**
 * AlarmReceiver handles the broadcast message and generates Notification
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Get notification manager to manage/send notifications
        Intent intentToRepeat = new Intent(context, MainActivity.class);
        //set flag to restart/relaunch the app
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Pending intent to handle launch of Activity in intent above
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build notification
        buildLocalNotification(context, pendingIntent);

    }

    public void buildLocalNotification(Context context, PendingIntent pendingIntent) {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Channel_Sleidom",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel_Sleidom_Desc");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(android.R.drawable.arrow_down_float) // notification icon
                .setContentTitle(context.getString(R.string.notification_title)) // title for notification
                .setContentText(context.getString(R.string.notification_desc))// message for notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // clear notification after click

        mNotificationManager.notify(0, mBuilder.build());



    }
}
