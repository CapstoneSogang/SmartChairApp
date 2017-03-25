package mountainq.kinggod.capstone.sogang.smartchairapp.HueManager;

/**
 * Created by jwahn37 on 2017. 3. 25..
 */

public class ThreadHue extends Thread {
    public void run() {
        //System.out.println("thread run.");
        hue_contoller hue_controller = new hue_contoller();
    }


}
