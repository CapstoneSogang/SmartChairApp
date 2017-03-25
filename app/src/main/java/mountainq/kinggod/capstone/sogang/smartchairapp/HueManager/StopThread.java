package mountainq.kinggod.capstone.sogang.smartchairapp.HueManager;

import android.util.Log;

/**
 * Created by jwahn37 on 2017. 3. 26..
 */

public class StopThread implements Runnable {
    private boolean stopFlag = false;
    public void run() {
        while(!stopFlag)
        {
            Log.d("thread","alive");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("thread","dead");
    }

    public void stop1(){
        this.stopFlag=true;
    }
}
