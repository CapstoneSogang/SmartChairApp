package mountainq.kinggod.capstone.sogang.smartchairapp;

import android.app.Application;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.StaticDatas;
import retrofit2.Retrofit;

/**
 * Created by dnay2 on 2017-03-19.
 */

public class ApplicationController extends Application {

    private static ApplicationController instance;

    public static ApplicationController getInstance() {
        return instance;
    }




    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance = this;
        this.buildService();
    }

    private void buildService() {
        synchronized (ApplicationController.class){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(StaticDatas.BASE_URL)
                    .build();
        }
    }
}
