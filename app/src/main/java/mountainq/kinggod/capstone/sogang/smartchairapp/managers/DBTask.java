package mountainq.kinggod.capstone.sogang.smartchairapp.managers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.PostureData;
import mountainq.kinggod.capstone.sogang.smartchairapp.datas.StaticDatas;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.Graph;

/**
 * Created by dnay2 on 2017-03-29.
 */

public class DBTask extends RegisterTask {

    public static final int GET_DATA_FROM_SERVER = 10;
    public static final int DELETE_ALL_DATA = 90;
    public static final int DELETE_ITEM = 80;
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
                setGetDataFromServer();
                return GET_DATA_FROM_SERVER;
            case DRAW_GRAPH:
                itemList = new ArrayList<>(dbm.selectAll());
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

    private void setGetDataFromServer(){
        DataBaseManager dbm = new DataBaseManager(mContext);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("token", PropertyManager.getInstance().getPushToken());
        }catch (JSONException e){
            e.printStackTrace();
        }
        String jsonBody = jsonObject.toString();
        String result;
        try {
            result = postMethod(StaticDatas.LOGIN_URL, jsonBody);
            if(result != null){
                try{
                    JSONArray array = new JSONArray(result);
                    JSONObject temp;
                    for(int i=0;i<array.length();i++){
                        temp = array.getJSONObject(i);
                        PostureData item = new PostureData(
                                temp.getString("_date"),
                                temp.getString("_time"),
                                temp.getString("waist"),
                                temp.getString("neck"));
                        dbm.insertItem(item);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }



    }

}
