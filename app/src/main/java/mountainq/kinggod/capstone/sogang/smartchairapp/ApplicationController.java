package mountainq.kinggod.capstone.sogang.smartchairapp;

import android.app.Application;

/**
 * Created by dnay2 on 2017-03-19.
 */

public class ApplicationController extends Application {

    private static ApplicationController instance;

    public static ApplicationController getInstance() {
        return instance;
    }

}
