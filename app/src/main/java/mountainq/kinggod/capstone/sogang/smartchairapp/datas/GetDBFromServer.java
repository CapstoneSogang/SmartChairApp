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
        setDataBase(mDbOpenHelper);

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

    protected void setDataBase(DbOpenHelper mDbOpenHelper) //디비 설정하기
    {
        mDbOpenHelper.open();

        //정자세 -> 걸쳐앉음 -> 정자세 -> 거북목 -> 정자세
        mDbOpenHelper.deleteAll(); //나중에 삭제할 부분. 디버깅용

        mDbOpenHelper.insertColumn("0317","1417","1" , "0");
        mDbOpenHelper.insertColumn("0317","1418","1" , "0");
        mDbOpenHelper.insertColumn("0317","1419","1" , "0");
        mDbOpenHelper.insertColumn("0317","1420","1" , "0");
        mDbOpenHelper.insertColumn("0317","1417","0" , "0");
        mDbOpenHelper.insertColumn("0317","1417","0" , "1");
        mDbOpenHelper.insertColumn("0317","1417","0" , "1");
        mDbOpenHelper.insertColumn("0317","1417","0" , "1");
        mDbOpenHelper.insertColumn("0317","1417","0" , "1");
        mDbOpenHelper.insertColumn("0317","1417","0" , "0");
        mDbOpenHelper.insertColumn("0317","1417","0" , "0");
        mDbOpenHelper.insertColumn("0317","1417","1" , "0");
        mDbOpenHelper.insertColumn("0317","1417","1" , "0");
        mDbOpenHelper.insertColumn("0317","1417","1" , "0");
        mDbOpenHelper.insertColumn("0317","1400","3" , "5");
        mDbOpenHelper.insertColumn("0317","1401","0" , "5");
        mDbOpenHelper.insertColumn("0317","1402","0" , "6");
        mDbOpenHelper.insertColumn("0317","1403","0" , "6");
        mDbOpenHelper.insertColumn("0317","1404","0" , "7");
        mDbOpenHelper.insertColumn("0317","1405","0" , "6");
        mDbOpenHelper.insertColumn("0317","1406","0" , "5");

        mDbOpenHelper.insertColumn("0318","1417","1" , "0");
        mDbOpenHelper.insertColumn("0318","1418","1" , "0");
        mDbOpenHelper.insertColumn("0318","1419","1" , "0");
        mDbOpenHelper.insertColumn("0318","1420","1" , "0");
        mDbOpenHelper.insertColumn("0318","1417","0" , "0");
        mDbOpenHelper.insertColumn("0318","1417","0" , "1");
        mDbOpenHelper.insertColumn("0318","1417","0" , "1");
        mDbOpenHelper.insertColumn("0318","1417","0" , "1");
        mDbOpenHelper.insertColumn("0318","1417","0" , "1");
        mDbOpenHelper.insertColumn("0318","1417","0" , "0");
        mDbOpenHelper.insertColumn("0318","1417","0" , "0");
        mDbOpenHelper.insertColumn("0318","1417","1" , "0");
        mDbOpenHelper.insertColumn("0318","1417","1" , "0");
        mDbOpenHelper.insertColumn("0318","1417","1" , "0");

        mDbOpenHelper.insertColumn("0319","1400","3" , "5");
        mDbOpenHelper.insertColumn("0319","1401","0" , "5");
        mDbOpenHelper.insertColumn("0319","1402","0" , "6");
        mDbOpenHelper.insertColumn("0319","1403","0" , "6");
        mDbOpenHelper.insertColumn("0319","1404","0" , "7");
        mDbOpenHelper.insertColumn("0319","1405","0" , "6");
        mDbOpenHelper.insertColumn("0319","1406","0" , "5");
        mDbOpenHelper.insertColumn("0319","1407","6" , "6");
        mDbOpenHelper.insertColumn("0319","1408","6" , "7");
        mDbOpenHelper.insertColumn("0319","1409","6" , "8");
        mDbOpenHelper.insertColumn("0319","1410","5" , "6");
        mDbOpenHelper.insertColumn("0319","1411","6" , "0");
        mDbOpenHelper.insertColumn("0319","1412","7" , "0");
        mDbOpenHelper.insertColumn("0319","1413","6" , "0");
        mDbOpenHelper.insertColumn("0319","1414","6" , "0");
        mDbOpenHelper.insertColumn("0319","1415","6" , "6");
        mDbOpenHelper.insertColumn("0319","1416","6" , "7");
        mDbOpenHelper.insertColumn("0319","1417","6" , "6");
        mDbOpenHelper.insertColumn("0319","1417","0" , "0");
        mDbOpenHelper.insertColumn("0319","1417","0" , "0");
        mDbOpenHelper.insertColumn("0319","1417","0" , "0");


        Log.d("insert", "zzz");

    }
}
