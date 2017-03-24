package mountainq.kinggod.capstone.sogang.smartchairapp.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import mountainq.kinggod.capstone.sogang.smartchairapp.MainActivity;
import mountainq.kinggod.capstone.sogang.smartchairapp.R;
import mountainq.kinggod.capstone.sogang.smartchairapp.datas.HueColor;
import mountainq.kinggod.capstone.sogang.smartchairapp.datas.StaticDatas;
import mountainq.kinggod.capstone.sogang.smartchairapp.interfaces.HueDeviceControl;
import mountainq.kinggod.capstone.sogang.smartchairapp.managers.PropertyManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by dnay2 on 2017-03-19.
 */

public class ChairService extends FirebaseMessagingService {

    private static final int BLUE = 101;
    private static final int GREEN = 102;
    private static final int RED = 103;
    private static final int WHITE = 104;

    PropertyManager propertyManager = PropertyManager.getInstance();

    HueDeviceControl deviceControl;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData());
        changHueLight(Integer.parseInt(remoteMessage.getData().get("integerKey")));
    }

    private void showNotification(Map<String, String> data) {
        int code = Integer.parseInt(data.get("code"));
        changHueLight(code);
        String message = "default";
        String title = "title";
        switch (code){
            case BLUE:
                break;
            case GREEN:
                break;
            case RED:
                break;
            case WHITE:
                break;
        }
        NotificationCompat.Builder builder = buildSimpleNotification("", "", "Hello Hue", data.get("message"));
        builder.build();
    }

    private void showNotification(String message) {
        NotificationCompat.Builder builder = buildSimpleNotification("", "", "Hello Hue", message);
        builder.build();
    }


    private NotificationCompat.Builder buildSimpleNotification(String code, String idx, String title, String message) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setColor(StaticDatas.MAIN_COLOR)
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
                    .setColor(StaticDatas.MAIN_COLOR)
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
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        return stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    public void changHueLight(final int code) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .build();
        deviceControl = retrofit.create(HueDeviceControl.class);

        HueColor query = null;
        switch (code) {
            case BLUE:
                query = new HueColor(true, 0, 0, 0);
                break;
            case GREEN:
                query = new HueColor(true, 0, 0, 0);
                break;
            case RED:
                query = new HueColor(true, 0, 0, 0);
                break;
            case WHITE:
                query = new HueColor(true, 0, 0, 0);
                break;
        }

        if(query == null) return;

        Call<ResponseBody> request = deviceControl.changeColor(
                propertyManager.getHueIp(),
                propertyManager.getHueName(),
                query
        );
        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.d("test", "successful");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
