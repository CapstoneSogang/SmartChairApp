package mountainq.kinggod.capstone.sogang.smartchairapp.graphs;

import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.DataBases;
import mountainq.kinggod.capstone.sogang.smartchairapp.datas.DbOpenHelper;
import mountainq.kinggod.capstone.sogang.smartchairapp.interfaces.HTTPInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jwahn37 on 2017. 3. 21..
 */

public class GetCordFromDB {

    protected final float TIME_SPAN = 1; //1분단위 센서값 가정시
    protected final float FULL_SPAN = 5; //30분 앉아있으면 가장 멕시멈 / 미니멈된다고 가정
    protected final int MAX_DATA=1000;

    public int recentDateIdx = 0; //가장 최근 날짜의 인덱스
    public String _date[] = new String[ MAX_DATA];     //디비 내용 저장
    public String _time[] = new String[ MAX_DATA];
    public String waist[] = new String[ MAX_DATA];
    public String neck[] = new String[ MAX_DATA];
    public Cursor mCursor;
    DbOpenHelper mDbOpenHelper;
    public int numOfData;

    public float waistHealth[] = new float[MAX_DATA];
    public float neckHealth[] = new float[MAX_DATA];


    public GetCordFromDB(DbOpenHelper mDbOpenHelper)
    {
        this.mDbOpenHelper = mDbOpenHelper;
        getData();
    }

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
        getCoordinate();

    }

    public void getCoordinate()
    {
        mCursor = mDbOpenHelper.readDbHelper();
        // mCursor = mDbOpenHelper.readDbHelper()
        mCursor.moveToFirst();

        Log.d(Integer.toString((mCursor.getCount())),"zzzz");
        for(int i=0;i<(numOfData=mCursor.getCount());i++) {
            _date[i]= mCursor.getString(mCursor.getColumnIndexOrThrow(DataBases.CreateDB._DATE));
            _time[i]= mCursor.getString(mCursor.getColumnIndexOrThrow(DataBases.CreateDB._TIME));
            waist[i]= mCursor.getString(mCursor.getColumnIndexOrThrow(DataBases.CreateDB.POS_WAIST));
            neck[i]= mCursor.getString(mCursor.getColumnIndexOrThrow(DataBases.CreateDB.POS_NECK));

            mCursor.moveToNext();
            Log.d(_date[i]+" "+_time[i]+" "+waist[i]+ " " + neck[i], "zzz");


        }
       // if (chartFlag.equals("line chart"))
        //    graphAlgorithm();
        //  else if (chartFlag.equals("pie chart"))
        graphAlgorithm();
    }

    private void graphAlgorithm() //미분시켜서 그래프 좌표 뽑아내기 -> 30분 자세면 100%변화라 가정하자
    {

        //nt recentDateIdx=0; //가장 최근 날짜로부터의 데이터들

        for(int i=0;i<numOfData;i++)
        {
            if(_date[i].equals(_date[numOfData-1]))
            {
                recentDateIdx=i; //가장 최근 날짜로부터의 데이터들
                Log.d("recentDateIdx", Integer.toString(i));
                Log.d("numOfData", Integer.toString(numOfData));
                break;
            }
        }

        if (waist[recentDateIdx].equals("0")) //잘못된 자세로 앉기 시작
        {
            waistHealth[recentDateIdx]=0;   //초기값
        }
        else    //정자세
            waistHealth[recentDateIdx]=100; //단위는 %

        if (neck[recentDateIdx].equals("0")) //잘못된 자세로 앉기 시작
        {
            neckHealth[recentDateIdx]=0;   //초기값
        }
        else    //정자세
            neckHealth[recentDateIdx]=100; //단위는 %


        for(int i=recentDateIdx+1;i<numOfData;i++)
        {
            int waistFlag = (waist[i].equals("0")) ? -1 : 1 ;    //+면 그래프상승 -면 그래프 하강
            int neckFlag = (neck[i].equals("0")) ? -1 : 1 ;    //+면 그래프상승 -면 그래프 하강

            Log.d("flag",Integer.toString(waistFlag));

            waistHealth[i]=waistHealth[i-1] + 100*(TIME_SPAN/FULL_SPAN) *waistFlag;
            neckHealth[i]=neckHealth[i-1] + 100*(TIME_SPAN/FULL_SPAN) *neckFlag;

            if(waistHealth[i]>=100) //최대 100, 최소 0이다.
                waistHealth[i]=100;
            if(waistHealth[i]<=0)
                waistHealth[i]=0;

            if(neckHealth[i]>=100) //최대 100, 최소 0이다.
                neckHealth[i]=100;
            if(neckHealth[i]<=0)
                neckHealth[i]=0;
            Log.d("recentDateIdx",Integer.toString(recentDateIdx));
            Log.d("date",_date[i]);
            Log.d("waistHealth",waist[i]);
            Log.d("neckHealth",neck[i]);
        }





    }




}
