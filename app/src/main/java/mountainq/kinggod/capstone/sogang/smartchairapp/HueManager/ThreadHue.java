package mountainq.kinggod.capstone.sogang.smartchairapp.HueManager;

import android.util.Log;

/**
 * Created by jwahn37 on 2017. 3. 25..
 */

public class ThreadHue extends Thread {
    // RegisterActivity registerActivity;
    private boolean stopFlag = false;
    static hue_contoller hueContoller;
    static boolean on;
    static int hue,bri, sat;
    static boolean init = true;
    static boolean second = true;
    static boolean controlFlag = false;
    static boolean contiFlag=true;
   // public static int tp=0;

    public ThreadHue( hue_contoller hueContoller){
        //this.registerActivity = registerActivity;
        this.hueContoller = hueContoller;

    }
    public ThreadHue()
    {

    }

    public void setThreadColor(boolean on, int hue, int bri, int sat)
    {
        this.on=on;
        this.hue=hue;
        this.bri=bri;
        this.sat=sat;
        controlFlag=true;
    }

    //  hueContoller1.controlHue(true,62535,200,200);
    public void run() {

//System.out.println("thread run.");
        //hue_contoller hue_controller = new hue_contoller(registerActivity);
/*
        if (init ==true)
        {
            hueContoller.registerHue();
            init=false;
        }
        else        //등록이 필요 없다
        {
            hueContoller.controlHue(on,hue,bri,sat);
            Log.d("controlhue","thread");
        }
*/
        while (contiFlag) {
            Log.d("thread ing", "zzzz");

            if (init ==true) //처음 등록시
            {
                hueContoller.registerHue();
                init=false;
                contiFlag=false;
            }

            else if(second==true)   //두번째 (칼라 세팅) 쓰레드가 호출시
            {
                second = false;
            }

            else
            {
                if (controlFlag == true) { //컨트롤 해야할때
                    hueContoller.controlHue(on, hue, bri, sat);
                    Log.d("controlhue", "thread");
                    controlFlag=false;
                }
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
