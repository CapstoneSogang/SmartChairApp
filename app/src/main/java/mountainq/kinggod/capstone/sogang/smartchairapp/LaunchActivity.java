package mountainq.kinggod.capstone.sogang.smartchairapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import mountainq.kinggod.capstone.sogang.smartchairapp.managers.PropertyManager;

/**
 * Created by dnay2 on 2017-03-19.
 */

public class LaunchActivity extends AppCompatActivity {

    private static final int FIRST_TIME = 100; //처음 쓰는 경우
    private static final int SECOND_TIME = 200; //두번째 쓰는 경우

    private static final int DEFAULT_RUNTIME = 5000;
    private static final int DEFAULT_INTERVAL = 1000;
    PropertyManager manager = PropertyManager.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Log.d("test", "launch task");

//        new LaunchTask().execute(FIRST_TIME, DEFAULT_RUNTIME, DEFAULT_INTERVAL);
        if(!checkToken())
            new LaunchTask().execute(FIRST_TIME, DEFAULT_RUNTIME, DEFAULT_INTERVAL);
        else
            new LaunchTask().execute(SECOND_TIME, DEFAULT_RUNTIME, DEFAULT_INTERVAL);

//        Intent intent = new Intent(LaunchActivity.this, RegisterActivity.class);
//        startActivity(intent);
    }

    private boolean checkToken() {
        return manager.getPushToken().equals("default") && manager.getUserToken().equals("default");
    }

    private class LaunchTask extends AsyncTask<Integer, Integer, Void> {

        private int runTime = 0;
        private int interval = 0;
        private int staticInterval = 0;

        @Override
        protected Void doInBackground(Integer... params) {
            Intent intent = getNext(params[0]);
            long preTime = System.currentTimeMillis();
            runTime = params[1];
            staticInterval = interval = params[2];
            int value = 0;
            Log.d("test", "execute");

//            while (interval < runTime) {
//                if(preTime - System.currentTimeMillis() > staticInterval){
//                    interval += interval;
//                    //애니메이션 같은거 여기다 두면댐
//                    publishProgress(++value);
//                }
//
//            }
            SystemClock.sleep(3000);
            startActivity(intent);
            finish();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        private Intent getNext(int code){
            switch (code){
                case FIRST_TIME:
                    return new Intent(LaunchActivity.this, RegisterActivity.class);
                case SECOND_TIME:
                    return new Intent(LaunchActivity.this, MainActivity.class);
            }
            return null;
        }
    }



}
