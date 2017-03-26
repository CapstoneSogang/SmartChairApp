package mountainq.kinggod.capstone.sogang.smartchairapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.StaticDatas;
import mountainq.kinggod.capstone.sogang.smartchairapp.managers.PropertyManager;

/**
 * Created by dnay2 on 2017-03-19.
 */

public class LaunchActivity extends AppCompatActivity {

    private static final int FIRST_TIME = 100; //처음 쓰는 경우 : 아이디 인증 전
    private static final int SECOND_TIME = 200; //두번째 쓰는 경우 : 휴 디바이스 없는 경우
    private static final int MAIN_LAUNCH = 300; // 메인 호출

    private static final int DEFAULT_RUNTIME = 5000;
    private static final int DEFAULT_INTERVAL = 1000;
    PropertyManager manager = PropertyManager.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Log.d("test", "launch task");
        Log.d("test", "userToken : " + manager.getUserToken() + "\n" +
        "hue IP : " + manager.getHueIp() + "\n" +
        "hue Name : " + manager.getHueName() + "\n" +
        "pushToken : " + manager.getPushToken());
        if(manager.getUserToken().equals("default"))
            new LaunchTask().execute(FIRST_TIME, DEFAULT_RUNTIME, DEFAULT_INTERVAL);
        else if (manager.getHueIp().equals("default") || manager.getHueName().equals("default"))
            new LaunchTask().execute(SECOND_TIME, DEFAULT_RUNTIME, DEFAULT_INTERVAL);
        else
            new LaunchTask().execute(MAIN_LAUNCH, DEFAULT_RUNTIME, DEFAULT_INTERVAL);

    }


    private class LaunchTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            Intent intent = getNext(params[0]);

            Log.d("test", "execute");

            SystemClock.sleep(3000);
            startActivity(intent);
            finish();
            return null;
        }
        private Intent getNext(int code){
            Intent intent = null;
            switch (code){
                case FIRST_TIME:
                    intent = new Intent(LaunchActivity.this, RegisterActivity.class);
                    intent.putExtra(StaticDatas.STATUS_CODE, StaticDatas.STATUS_USER_REGISTER);
                    break;
                case SECOND_TIME:
                    intent = new Intent(LaunchActivity.this, MainActivity.class);
                    intent.putExtra(StaticDatas.STATUS_CODE, StaticDatas.STATUS_HUE_REGISTER);
                    break;
                case MAIN_LAUNCH:
                    intent = new Intent(LaunchActivity.this, MainActivity.class);
                    break;
            }

            return intent;
        }
    }



}
