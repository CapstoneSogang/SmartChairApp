package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.GetCordFromDB;
import mountainq.kinggod.capstone.sogang.smartchairapp.interfaces.HTTPInterface;
import mountainq.kinggod.capstone.sogang.smartchairapp.managers.RegisterTask;
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

        mDbOpenHelper.deleteAll(); //나중에 삭제할 부분. 디버깅용


        //총 1주일치의 데이터 확보 : 초반 6일은 건강자세vs안건강자세 , 공부량만 보면 됨
        //가장 최신데이터인 4/3 데이터가 중요! 출력되는 1~3번 그래프는 가장 최신 정보(4/3)를 이용하여 그리게 된다

        //3/29 데이터 5분단위로 받아온다 /데이터 24개 /  2시간 공부(19~21시), 건강자세 :5번(5/24)
        //3/30 데이터 5분단위로 받아온다 /데이터 19개 / 90분 공부(19~20:30), 건강자세 :8번(8/19)
        //3/31 데이터 5분단위로 받아온다 /데이터 22개 /   110분 공부(19~20:50), 건강자세 :12번(12/22)
        //4/1 데이터 5분단위로 받아온다 /데이터 12개 /   60분 공부(19~20), 건강자세 :6번(6/12)
        //4/2 데이터 5분단위로 받아온다 /데이터 15개 /   70분 공부(19~20:10), 건강자세 :11번(11/15)
        //4/3 데이터 5분단위로 받아온다 /데이터 24개 /   120분 공부(19~21),
        //허리 : 정자세 -> 디스크(19:20~19:40) ->정자세 -> 디스크(20:00~20:30)
        //목 : 정자세 -> 디스크 (19:50~22:20) -> 정자세
        //건강자세 : 11번 : (11/24)




        //3/29 데이터 5분단위로 받아온다 /데이터 24개 /  2시간 공부(19~21시), 건강자세 :5번(5/24)
        mDbOpenHelper.insertColumn("0329","1900","1" , "1");
        mDbOpenHelper.insertColumn("0329","1905","1" , "1");
        mDbOpenHelper.insertColumn("0329","1910","1" , "1");
        mDbOpenHelper.insertColumn("0329","1915","1" , "1");
        mDbOpenHelper.insertColumn("0329","1920","1" , "1");
        mDbOpenHelper.insertColumn("0329","1925","1" , "0");
        mDbOpenHelper.insertColumn("0329","1930","1" , "0");
        mDbOpenHelper.insertColumn("0329","1935","1" , "0");
        mDbOpenHelper.insertColumn("0329","1940","1" , "0");
        mDbOpenHelper.insertColumn("0329","1945","1" , "0");
        mDbOpenHelper.insertColumn("0329","1950","0" , "0");
        mDbOpenHelper.insertColumn("0329","1955","1" , "0");
        mDbOpenHelper.insertColumn("0329","2000","1" , "0");
        mDbOpenHelper.insertColumn("0329","2005","1" , "0");
        mDbOpenHelper.insertColumn("0329","2010","0" , "5");
        mDbOpenHelper.insertColumn("0329","2015","0" , "5");
        mDbOpenHelper.insertColumn("0329","2020","0" , "6");
        mDbOpenHelper.insertColumn("0329","2025","0" , "6");
        mDbOpenHelper.insertColumn("0329","2030","0" , "7");
        mDbOpenHelper.insertColumn("0329","2035","0" , "6");
        mDbOpenHelper.insertColumn("0329","2040","0" , "5");
        mDbOpenHelper.insertColumn("0329","2045","1" , "0");
        mDbOpenHelper.insertColumn("0329","2050","1" , "0");
        mDbOpenHelper.insertColumn("0329","2055","1" , "0");
        mDbOpenHelper.insertColumn("0329","2100","1" , "0");

        //3/30 데이터 5분단위로 받아온다 /데이터 19개 / 90분 공부(19~20:30), 건강자세 :8번(8/19)
        mDbOpenHelper.insertColumn("0330","1900","1" , "1");
        mDbOpenHelper.insertColumn("0330","1905","1" , "1");
        mDbOpenHelper.insertColumn("0330","1910","1" , "1");
        mDbOpenHelper.insertColumn("0330","1915","1" , "1");
        mDbOpenHelper.insertColumn("0330","1920","1" , "1");
        mDbOpenHelper.insertColumn("0330","1925","1" , "1");
        mDbOpenHelper.insertColumn("0330","1930","1" , "1");
        mDbOpenHelper.insertColumn("0330","1935","1" , "1");
        mDbOpenHelper.insertColumn("0330","1940","0" , "1");
        mDbOpenHelper.insertColumn("0330","1945","0" , "0");
        mDbOpenHelper.insertColumn("0330","1950","0" , "0");
        mDbOpenHelper.insertColumn("0330","1955","1" , "0");
        mDbOpenHelper.insertColumn("0330","2000","1" , "0");
        mDbOpenHelper.insertColumn("0330","2005","1" , "0");
        mDbOpenHelper.insertColumn("0330","2010","0" , "5");
        mDbOpenHelper.insertColumn("0330","2015","0" , "5");
        mDbOpenHelper.insertColumn("0330","2020","0" , "6");
        mDbOpenHelper.insertColumn("0330","2025","0" , "6");
        mDbOpenHelper.insertColumn("0330","2030","0" , "7");

        //3/31 데이터 5분단위로 받아온다 /데이터 22개 /   110분 공부(19~20:50), 건강자세 :12번(12/22)
        mDbOpenHelper.insertColumn("0331","1900","1" , "1");
        mDbOpenHelper.insertColumn("0331","1905","1" , "1");
        mDbOpenHelper.insertColumn("0331","1910","1" , "1");
        mDbOpenHelper.insertColumn("0331","1915","1" , "1");
        mDbOpenHelper.insertColumn("0331","1920","1" , "1");
        mDbOpenHelper.insertColumn("0331","1925","1" , "1");
        mDbOpenHelper.insertColumn("0331","1930","1" , "1");
        mDbOpenHelper.insertColumn("0331","1935","1" , "1");
        mDbOpenHelper.insertColumn("0331","1940","1" , "1");
        mDbOpenHelper.insertColumn("0331","1945","1" , "1");
        mDbOpenHelper.insertColumn("0331","1950","1" , "1");
        mDbOpenHelper.insertColumn("0331","1955","1" , "1");
        mDbOpenHelper.insertColumn("0331","2000","1" , "0");
        mDbOpenHelper.insertColumn("0331","2005","1" , "0");
        mDbOpenHelper.insertColumn("0331","2010","0" , "5");
        mDbOpenHelper.insertColumn("0331","2015","0" , "5");
        mDbOpenHelper.insertColumn("0331","2020","0" , "6");
        mDbOpenHelper.insertColumn("0331","2025","0" , "6");
        mDbOpenHelper.insertColumn("0331","2030","0" , "7");
        mDbOpenHelper.insertColumn("0331","2035","0" , "6");
        mDbOpenHelper.insertColumn("0331","2040","0" , "5");
        mDbOpenHelper.insertColumn("0331","2045","1" , "0");
        mDbOpenHelper.insertColumn("0331","2050","1" , "0");

        //4/1 데이터 5분단위로 받아온다 /데이터 12개 /   60분 공부(19~20), 건강자세 :6번(6/12)
        mDbOpenHelper.insertColumn("0401","1900","1" , "1");
        mDbOpenHelper.insertColumn("0401","1905","1" , "1");
        mDbOpenHelper.insertColumn("0401","1910","1" , "1");
        mDbOpenHelper.insertColumn("0401","1915","1" , "1");
        mDbOpenHelper.insertColumn("0401","1920","1" , "1");
        mDbOpenHelper.insertColumn("0401","1925","1" , "1");
        mDbOpenHelper.insertColumn("0401","1930","0" , "1");
        mDbOpenHelper.insertColumn("0401","1935","0" , "1");
        mDbOpenHelper.insertColumn("0401","1940","0" , "1");
        mDbOpenHelper.insertColumn("0401","1945","0" , "0");
        mDbOpenHelper.insertColumn("0401","1950","0" , "0");
        mDbOpenHelper.insertColumn("0401","1955","1" , "0");
        mDbOpenHelper.insertColumn("0401","2000","1" , "0");

        //4/2 데이터 5분단위로 받아온다 /데이터 19개 /   90분 공부(19~20:30), 건강자세 :8번(8/19)
        mDbOpenHelper.insertColumn("0402","1900","1" , "1");
        mDbOpenHelper.insertColumn("0402","1905","1" , "1");
        mDbOpenHelper.insertColumn("0402","1910","1" , "1");
        mDbOpenHelper.insertColumn("0402","1915","1" , "1");
        mDbOpenHelper.insertColumn("0402","1920","1" , "1");
        mDbOpenHelper.insertColumn("0402","1925","1" , "1");
        mDbOpenHelper.insertColumn("0402","1930","1" , "1");
        mDbOpenHelper.insertColumn("0402","1935","1" , "1");
        mDbOpenHelper.insertColumn("0402","1940","0" , "1");
        mDbOpenHelper.insertColumn("0402","1945","0" , "0");
        mDbOpenHelper.insertColumn("0402","1950","0" , "0");
        mDbOpenHelper.insertColumn("0402","1955","1" , "0");
        mDbOpenHelper.insertColumn("0402","2000","1" , "0");
        mDbOpenHelper.insertColumn("0402","2005","1" , "0");
        mDbOpenHelper.insertColumn("0402","2010","0" , "5");
        mDbOpenHelper.insertColumn("0402","2015","0" , "5");
        mDbOpenHelper.insertColumn("0402","2020","0" , "6");
        mDbOpenHelper.insertColumn("0402","2025","0" , "6");
        mDbOpenHelper.insertColumn("0402","2030","0" , "7");

        //4/3 데이터 5분단위로 받아온다 /데이터 15개 /   70분 공부(19~20:10), 건강자세 :11번(11/15)

        mDbOpenHelper.insertColumn("0403","1900","1" , "1");
        mDbOpenHelper.insertColumn("0403","1905","1" , "1");
        mDbOpenHelper.insertColumn("0403","1910","1" , "1");
        mDbOpenHelper.insertColumn("0403","1915","1" , "1");
        mDbOpenHelper.insertColumn("0403","1920","1" , "1");
        mDbOpenHelper.insertColumn("0403","1925","1" , "1");
        mDbOpenHelper.insertColumn("0403","1930","1" , "1");
        mDbOpenHelper.insertColumn("0403","1935","1" , "1");
        mDbOpenHelper.insertColumn("0403","1940","1" , "1");
        mDbOpenHelper.insertColumn("0403","1945","1" , "1");
        mDbOpenHelper.insertColumn("0403","1950","1" , "1");
        mDbOpenHelper.insertColumn("0403","1955","1" , "0");
        mDbOpenHelper.insertColumn("0403","2000","1" , "0");
        mDbOpenHelper.insertColumn("0403","2005","1" , "0");
        mDbOpenHelper.insertColumn("0403","2010","0" , "5");

    //4/4 데이터 5분단위로 받아온다 /데이터 24개 /   120분 공부(19~21),
        //허리 : 정자세 -> 디스크(19:20~19:40) ->정자세 -> 디스크(20:00~20:30)
        //목 : 정자세 -> 디스크 (19:50~22:20) -> 정자세
        //건강자세 : 11번 : (11/24)
        mDbOpenHelper.insertColumn("0404","1900","1" , "1");
        mDbOpenHelper.insertColumn("0404","1905","1" , "1");
        mDbOpenHelper.insertColumn("0404","1910","1" , "1");
        mDbOpenHelper.insertColumn("0404","1915","1" , "1");
        mDbOpenHelper.insertColumn("0404","1920","0" , "1");
        mDbOpenHelper.insertColumn("0404","1925","0" , "1");
        mDbOpenHelper.insertColumn("0404","1930","0" , "1");
        mDbOpenHelper.insertColumn("0404","1935","0" , "1");
        mDbOpenHelper.insertColumn("0404","1940","0" , "1");
        mDbOpenHelper.insertColumn("0404","1945","1" , "1");
        mDbOpenHelper.insertColumn("0404","1950","1" , "0");
        mDbOpenHelper.insertColumn("0404","1955","1" , "0");
        mDbOpenHelper.insertColumn("0404","2000","0" , "0");
        mDbOpenHelper.insertColumn("0404","2005","0" , "0");
        mDbOpenHelper.insertColumn("0404","2010","0" , "0");
        mDbOpenHelper.insertColumn("0404","2015","0" , "0");
        mDbOpenHelper.insertColumn("0404","2020","0" , "0");
        mDbOpenHelper.insertColumn("0404","2025","0" , "4");
        mDbOpenHelper.insertColumn("0404","2030","0" , "7");
        mDbOpenHelper.insertColumn("0404","2035","1" , "6");
        mDbOpenHelper.insertColumn("0404","2040","1" , "5");
        mDbOpenHelper.insertColumn("0404","2045","1" , "1");
        mDbOpenHelper.insertColumn("0404","2050","4" , "1");
        mDbOpenHelper.insertColumn("0404","2055","4" , "1");
        mDbOpenHelper.insertColumn("0404","2100","1" , "1");

        Log.d("insert", "zzz");

    }


    private class DataTask extends RegisterTask{

        private String token;

        public DataTask(String token) {
            this.token = token;
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("token", token);
            }catch (JSONException e){
                e.printStackTrace();
            }

            String jsonBody = jsonObject.toString();
            try{
                String result = postMethod(StaticDatas.LOGIN_URL, jsonBody);
                if(result != null){
                    try{
                        JSONArray array = new JSONArray(result);
                        JSONObject temp;
                        for(int i=0;i<array.length();i++){
                            temp = array.getJSONObject(i);
                            String date = temp.getString("");
                            String time = temp.getString("");
                            String sensor1 = temp.getString("");
                            String sensor2 = temp.getString("");

                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return super.doInBackground(params);
        }
    }
}
