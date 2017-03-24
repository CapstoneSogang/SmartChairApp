package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.GetCordFromDB;
import mountainq.kinggod.capstone.sogang.smartchairapp.interfaces.HTTPInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jwahn37 on 2017. 3. 24..
 */

public class GetDBFromServer {

    private DbOpenHelper mDbOpenHelper;

    public GetDBFromServer(DbOpenHelper mDbOpenHelper, GetCordFromDB getCordFromDB){
        this.mDbOpenHelper = mDbOpenHelper;
       // getCordFromDB.check="1";

        getData(getCordFromDB);
    }


    public void getData(GetCordFromDB getCordFromDB){
        Log.d("reciece","data");
        //http interface
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HTTPInterface.URL).build();
        HTTPInterface service = retrofit.create(HTTPInterface.class);
        final Call<ResponseBody> comment = service.getComment(1);
        comment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String body=response.body().string();
                    Log.d("http test",body);
                    ////
                    JSONArray jarray = new JSONArray(body);   // JSONArray 생성
                    for(int i=0; i < jarray.length(); i++){
                        JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                        int postId = jObject.getInt("postId");
                        String name = jObject.getString("name");
                        String email = jObject.getString("email");

                        Log.d("http test2 ",email);
                    }


                    //Log.d("responsebody type",response.body().)
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        //Call<List<Repo>> repos = service.listRepos("octocat");
       // getCoordinate();

    }
}
