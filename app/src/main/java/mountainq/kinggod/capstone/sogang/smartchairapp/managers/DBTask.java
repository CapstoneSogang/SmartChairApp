package mountainq.kinggod.capstone.sogang.smartchairapp.managers;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.PostureData;
import mountainq.kinggod.capstone.sogang.smartchairapp.datas.StaticDatas;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.GetCordFromDB;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.Graph;

/**
 * Created by dnay2 on 2017-03-29.
 */

public class DBTask extends RegisterTask {


    public static final int DELETE_ALL_DATA = 90;
    public static final int DELETE_ITEM = 80;
    public static final int GET_DATA_FROM_SERVER = 10;
    public static final int DRAW_GRAPH = 20;
    public static final int GRAPH_PIE = 200;
    public static final int GRAPH_LINE = 201;
    public static final int GRAPH_BAR = 202;

    private DataBaseManager dbm = null;
    private ArrayList<PostureData> itemList = null;
    private Context mContext = null;
    private int order = -1;
    private int graphStyle = -1;
    private Graph graphLayer = null;
    private String fromDate = "";
    private String toDate = "";

    public DBTask(Context mContext, int order) {
        this.mContext = mContext;
        this.order = order;
        dbm = new DataBaseManager(mContext);
    }

    public DBTask(Context mContext, int order, int graphStyle, Graph graphLayer) {
        this.mContext = mContext;
        this.order = order;
        this.graphStyle = graphStyle;
        this.graphLayer = graphLayer;
        dbm = new DataBaseManager(mContext);
    }

    public DBTask(Context mContext, int order, int graphStyle, Graph graphLayer, String fromDate, String toDate) {
        this.mContext = mContext;
        this.order = order;
        this.graphStyle = graphStyle;
        this.graphLayer = graphLayer;
        this.fromDate = fromDate;
        this.toDate = toDate;
        dbm = new DataBaseManager(mContext);
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        switch (order){
            case GET_DATA_FROM_SERVER:
                Log.d("getdatafromserver","zzz");
                setGetDataFromServer();
                return GET_DATA_FROM_SERVER;
            case DRAW_GRAPH:
                itemList = new ArrayList<>(dbm.selectAll());
                GetCordFromDB getCord = new GetCordFromDB(itemList);

                return DRAW_GRAPH;
            default:
                break;
        }
        return super.doInBackground(params);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case GET_DATA_FROM_SERVER:
                break;
            case DRAW_GRAPH:
                switch (graphStyle){
                    case GRAPH_BAR:

                        break;
                    case GRAPH_LINE:
                        break;
                    case GRAPH_PIE:
                        break;
                }
                if(graphLayer != null){

                    graphLayer.drawGraph("graph");
                }

                break;
        }

    }

    private void setGetDataFromServer() {
         //dbm = new DataBaseManager(mContext);
        JSONObject jsonObject = new JSONObject();
        try {
            //jinu
            jsonObject.put("id", StaticDatas.loginId);
            jsonObject.put("last_time", StaticDatas.lastTime);
            Log.d("id",StaticDatas.loginId);
            Log.d("last_time",StaticDatas.lastTime);
            //jsonObject.put("token", PropertyManager.getInstance().getPushToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonBody = jsonObject.toString();
        String result;
        try {
            result = postMethod(StaticDatas.LOGIN_URL, jsonBody);
            if (result != null) {
                try {
                    JSONArray array = new JSONArray(result);
                    JSONObject temp;
                    for (int i = 0; i < array.length(); i++) {
                        temp = array.getJSONObject(i);
                        //jinu
                        String serverDate = temp.getString("DATE_TIME");
                        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(changeTimeFormat(serverDate));
                        // Timestamp timestamp = new Timestamp(date.getTime());
                        Log.d("mydate1:",changeTimeFormat(serverDate));

                        SimpleDateFormat sdfDate = new SimpleDateFormat("MMdd");
                        SimpleDateFormat sdfHour = new SimpleDateFormat("HHmm");
                        Log.d("curtime", sdfDate.format(date));

                        PostureData item = new PostureData(
                                //  temp.getString("_date"),
                                //  temp.getString("_time"),
                                sdfDate.format(date),
                                sdfHour.format(date),
                                temp.getString("WAIST"),
                                temp.getString("NECK"),
                                temp.getString("POSTURE_L"),
                                temp.getString("POSTURE_R")

                        ); //
                        dbm.insertItem(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String changeTimeFormat(String serverDate)//server date format : 2017-04-03T15:06:59.900Z

    {
        StringTokenizer strTok = new StringTokenizer(serverDate,"T");
        String date = strTok.nextToken();
        String hour = strTok.nextToken();
        strTok = new StringTokenizer(hour,".");
        hour = strTok.nextToken();

        return date+" "+hour;
        //return format :  2017-04-03 15:06:59"
    }



}
