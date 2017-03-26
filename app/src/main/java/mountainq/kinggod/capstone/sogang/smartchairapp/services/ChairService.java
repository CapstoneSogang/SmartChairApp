package mountainq.kinggod.capstone.sogang.smartchairapp.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import mountainq.kinggod.capstone.sogang.smartchairapp.HueManager.ThreadHue;
import mountainq.kinggod.capstone.sogang.smartchairapp.LaunchActivity;
import mountainq.kinggod.capstone.sogang.smartchairapp.MainActivity;
import mountainq.kinggod.capstone.sogang.smartchairapp.R;
import mountainq.kinggod.capstone.sogang.smartchairapp.datas.StaticDatas;
import mountainq.kinggod.capstone.sogang.smartchairapp.managers.HueTask;
import mountainq.kinggod.capstone.sogang.smartchairapp.managers.PropertyManager;

/**
 * Created by dnay2 on 2017-03-19.
 */

public class ChairService extends FirebaseMessagingService {

    private static final int RED = 1;
    private static final int GREEN = 2;
    private static final int TURN_ON = 3;
    private static final int TURN_OFF = 4;

    PropertyManager propertyManager = PropertyManager.getInstance();
    ThreadHue colorThread;

    @Override
    public void onCreate() {
        super.onCreate();
        colorThread = new ThreadHue();
        //colorThread.setThreadColor(true,62535,200,200);
        colorThread.start();

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData());
        Log.d("test", "received message : " + remoteMessage.getData().toString());
        /*
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        t.start();
        */
    }



    private void showNotification(Map<String, String> data) {
        int code = Integer.parseInt(data.get("code"));
        changeHueLight(code);
        String message = "default";
        String title = "title";
        switch (code){
            case RED:
                break;
            case GREEN:
                break;
            case TURN_ON:
                break;
            case TURN_OFF:
                break;
        }
        NotificationCompat.Builder builder = buildSimpleNotification("", "", "Hello Hue", message);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, builder.build());
    }

    private void showNotification(String message) {
        NotificationCompat.Builder builder = buildSimpleNotification("", "", "Hello Hue", message);
        builder.build();
    }


    private NotificationCompat.Builder buildSimpleNotification(String code, String idx, String title, String message) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setColor(StaticDatas.COLOR_MAIN)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setShowWhen(true)
                .setWhen(System.currentTimeMillis())
                .setSound(defaultSoundUri)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(getPendingIntent(code, idx));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setColor(StaticDatas.COLOR_MAIN)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }
        return builder;
    }

    private PendingIntent getPendingIntent(String code, String idx) {
        Intent intent = null;

        intent = new Intent(this, MainActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("idx", idx);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LaunchActivity.class);
        stackBuilder.addNextIntent(intent);

        return stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    public void changeHueLight(final int code) {


        switch (code) {
            case RED:
                new HueTask(HueTask.ORDER_RED).execute();
                //query = new HueColor(true, 0, 0, 0);
                //ThreadHue threadHue = new ThreadHue();
                //colorThread.setThreadColor(true,62535,200,200);
                //threadHue.start();
                //threadHue.stop();
                break;
            case GREEN:
                new HueTask(HueTask.ORDER_GREEN).execute();
                // ThreadHue threadHue1 = new ThreadHue();
                //colorThread.setThreadColor(true,23500,200,200);
                //threadHue1.start();
                //query = new HueColor(true, 0, 0, 0);
                break;
            case TURN_ON:
                new HueTask(HueTask.ORDER_TURN_ON).execute();
                //   ThreadHue threadHue2 = new ThreadHue();
              //  colorThread.setThreadColor(true,0,200,0);
              //  threadHue2.start();
                //query = new HueColor(true, 0, 0, 0);
                break;
            case TURN_OFF:
          //      ThreadHue threadHue3 = new ThreadHue();
                new HueTask(HueTask.ORDER_TURN_OFF).execute();
                //  colorThread.setThreadColor(false,0,0,0);
            //    threadHue3.start();
                //query = new HueColor(true, 0, 0, 0);
                break;
        }

    }

}
