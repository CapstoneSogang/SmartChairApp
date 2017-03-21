package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

import android.util.Log;

import java.io.IOException;

import mountainq.kinggod.capstone.sogang.smartchairapp.interfaces.HTTPInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jwahn37 on 2017. 3. 21..
 */

public class ReceiveData {

    public void getData(){
        Log.d("reciece","data");
        //http interface
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HTTPInterface.URL).build();
        HTTPInterface service = retrofit.create(HTTPInterface.class);
        final Call<ResponseBody> comment = service.getComment(1);
        comment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("http test",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        //Call<List<Repo>> repos = service.listRepos("octocat");

    }
}
