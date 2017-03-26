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


    @Override
    public void onCreate() {
        super.onCreate();


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
                title = "RED";
                message = "자세를 바로 하세요";
                break;
            case GREEN:
                title = "GREEN";
                message = "좋은 자세 입니다";
                break;
            case TURN_ON:
                title = "LIGHT ON";
                message = "일을 시작합니다";
                break;
            case TURN_OFF:
                title = "LIGHT OFF";
                message = "일을 끝냅니다";
                break;
        }
        NotificationCompat.Builder builder = buildSimpleNotification("", "", title, message);
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

                break;
            case GREEN:
                new HueTask(HueTask.ORDER_GREEN).execute();

                break;
            case TURN_ON:
                new HueTask(HueTask.ORDER_TURN_ON).execute();

                break;
            case TURN_OFF:
                new HueTask(HueTask.ORDER_TURN_OFF).execute();

                break;
        }

    }

}
