package mountainq.kinggod.capstone.sogang.smartchairapp.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import mountainq.kinggod.capstone.sogang.smartchairapp.managers.HueManager;

/**
 * Created by dnay2 on 2017-03-19.
 */

public class ChairService extends FirebaseMessagingService {

    HueManager hueManager = HueManager.getInstance();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData());
    }

    private void showNotification(Map<String, String> data){

    }

//    의자 움직이는거 만들어야함


    //    private NotificationCompat.Builder buildSimpleNotification(String code, String idx, String title, String message) {
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_notice_alarm)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setShowWhen(true)
//                .setWhen(System.currentTimeMillis())
//                .setSound(defaultSoundUri)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setColor(StaticDatas.MAIN_COLOR)
//                .setContentIntent(getPendingIntent(code, idx));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder.setCategory(Notification.CATEGORY_MESSAGE)
//                    .setPriority(Notification.PRIORITY_HIGH)
//                    .setVisibility(Notification.VISIBILITY_PUBLIC)
//                    .setColor(StaticDatas.MAIN_COLOR)
//                    .setSmallIcon(R.drawable.ic_notice_alarm);
//
//        }
//        return builder;
//    }
//
//    private NotificationCompat.Builder buildCustomNotification(String code, String id, String title, String message) {
//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notificationbar);
//        remoteViews.setImageViewResource(R.id.logoimg, R.mipmap.ic_launcher);
//        remoteViews.setTextViewText(R.id.title, title);
//        remoteViews.setTextViewText(R.id.message, message);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setContent(remoteViews)
//                .setSmallIcon(R.drawable.ic_notice_alarm)
//                .setAutoCancel(true)
//                .setWhen(System.currentTimeMillis())
//                .setShowWhen(true)
//                .setContentIntent(getPendingIntent(code, id))
//                .setDefaults(Notification.DEFAULT_ALL);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            notificationBuilder.setCategory(Notification.CATEGORY_MESSAGE)
//                    .setPriority(Notification.PRIORITY_HIGH)
//                    .setVisibility(Notification.VISIBILITY_PUBLIC);
//        }
//
//
//        return notificationBuilder;
//    }
//
//    private PendingIntent getPendingIntent(String code, String idx) {
//        Intent intent = null;
//
//        intent = new Intent(this, MainActivity.class);
//        intent.putExtra("code", code);
//        intent.putExtra("idx", idx);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(LoginActivity.class);
//        stackBuilder.addNextIntent(intent);
//
//        return stackBuilder.getPendingIntent(
//                0,
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );
//    }
}
