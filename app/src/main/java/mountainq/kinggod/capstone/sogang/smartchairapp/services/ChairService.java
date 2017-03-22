package mountainq.kinggod.capstone.sogang.smartchairapp.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.philips.lighting.hue.listener.PHHTTPListener;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;

import java.util.ArrayList;
import java.util.Map;

import mountainq.kinggod.capstone.sogang.smartchairapp.MainActivity;
import mountainq.kinggod.capstone.sogang.smartchairapp.managers.HueManager;
import mountainq.kinggod.capstone.sogang.smartchairapp.managers.PropertyManager;

/**
 * Created by dnay2 on 2017-03-19.
 */

public class ChairService extends FirebaseMessagingService {

    private static final int BLUE = 101;
    private static final int GREEN = 102;
    private static final int RED = 103;
    private static final int WHITE = 104;

    PropertyManager propertyManager = PropertyManager.getInstance();
    HueManager hueManager = HueManager.getInstance();
    PHBridgeSearchManager sm;
    PHAccessPoint accessPoint;

    @Override
    public void onCreate() {
        super.onCreate();
        ArrayList<PHAccessPoint> list = hueManager.searchPhHueDevice();
        if (list.size() > 0) {
            showNotification("there are some Hue devices near by here.");
            for (PHAccessPoint ap : list) {
                propertyManager.setHueIp(ap.getIpAddress());
            }

        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData());
    }

    private void showNotification(Map<String, String> data) {

    }

    private void showNotification(String message) {
        NotificationCompat.Builder builder = buildSimpleNotification("", "", "Hello Hue", message);
        builder.build();
    }


    private NotificationCompat.Builder buildSimpleNotification(String code, String idx, String title, String message) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_notice_alarm)
//                .setColor(StaticDatas.MAIN_COLOR)
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
//                    .setColor(StaticDatas.MAIN_COLOR)
//                    .setSmallIcon(R.drawable.ic_notice_alarm)
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
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        return stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    public void changHueLight(int code) {
         String query = "";
        switch (code) {
            case BLUE:
                break;
            case GREEN:
                break;
            case RED:
                break;
            case WHITE:
                break;
        }
        hueManager.getPhHueSDK().getSelectedBridge().doHTTPPut("", "", new PHHTTPListener() {
            @Override
            public void onHTTPResponse(String s) {

            }
        });
    }

}
