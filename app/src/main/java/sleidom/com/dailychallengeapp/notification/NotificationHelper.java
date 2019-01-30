package sleidom.com.dailychallengeapp.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;

import static android.content.Context.ALARM_SERVICE;

public class NotificationHelper {

    public static int ALARM_TYPE_RTC = 100;

    public static int ALARM_TYPE_ELAPSED = 101;
    private static AlarmManager alarmManagerElapsed;
    private static PendingIntent alarmIntentElapsed;

    /***
     * This is another way to schedule notifications using the elapsed time.
     * Its based on the relative time since device was booted up.
     * @param context
     */
    public static void scheduleRepeatingElapsedNotification(Context context) {
        //Setting intent to class where notification will be handled
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Setting pending intent to respond to broadcast sent by AlarmManager everyday at 8am
        alarmIntentElapsed = PendingIntent.getBroadcast(context, ALARM_TYPE_ELAPSED, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //getting instance of AlarmManager service
        alarmManagerElapsed = (AlarmManager)context.getSystemService(ALARM_SERVICE);

        // Every day
        /*alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY,
                AlarmManager.INTERVAL_DAY, alarmIntentElapsed);*/

        alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY,
                AlarmManager.INTERVAL_DAY, alarmIntentElapsed);
        System.out.println("Creando notificacion2");
    }

    public static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * Enable boot receiver to persist alarms set for notifications across device reboots
     */
    public static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * Disable boot receiver when user cancels/opt-out from notifications
     */
    public static void disableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static void cancelAlarmElapsed() {
        if (alarmManagerElapsed!= null) {
            alarmManagerElapsed.cancel(alarmIntentElapsed);
        }
    }
}
