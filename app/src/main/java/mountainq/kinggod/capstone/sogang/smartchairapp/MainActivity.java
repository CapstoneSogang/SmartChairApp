package mountainq.kinggod.capstone.sogang.smartchairapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

import java.util.List;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.DbOpenHelper;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.BarGraph;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.Graph;
import mountainq.kinggod.capstone.sogang.smartchairapp.datas.GetCordFromDB;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.LineGraph;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.PieGraph;
import mountainq.kinggod.capstone.sogang.smartchairapp.managers.HueManager;
import mountainq.kinggod.capstone.sogang.smartchairapp.managers.LOG;
///
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TestDataBaseActivity";
    private DbOpenHelper mDbOpenHelper = new DbOpenHelper(this);
    private Cursor mCursor;
    private Graph waistGraph, neckGraph, pieGraph, barGraph;

    private String date = "0319";
    // private InfoClass mInfoClass;
    // private ArrayList<infoclass> mInfoArray;
    // private CustomAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("???","zzzz");

        setDataBase(mDbOpenHelper);
        GetCordFromDB getCordFromDB = new GetCordFromDB(mDbOpenHelper);
        //getCordFromDB.getData();
       // getCordFromDB.getCoordinate();

        // mDbOpenHelper = new DbOpenHelper(this);
        Log.d("insert", "zzz");

        LineChart waistChart = (LineChart) findViewById(R.id.waist_chart);
        waistGraph = new LineGraph(getCordFromDB,waistChart);
        waistGraph.drawGraph("waist");

        LineChart neckChart = (LineChart) findViewById(R.id.neck_chart);
        neckGraph = new LineGraph(getCordFromDB,neckChart);
        neckGraph.drawGraph("neck");

        PieChart pieChart = (PieChart) findViewById(R.id.pie_chart);
        pieGraph = new PieGraph(getCordFromDB, pieChart);
        pieGraph.drawGraph(date);

        BarChart barChartWork = (BarChart) findViewById(R.id.bar_chart_work);
        barGraph = new BarGraph(getCordFromDB, barChartWork);
        barGraph.drawGraph("work");

        BarChart barChartPosture = (BarChart) findViewById(R.id.bar_chart_posture);
        barGraph = new BarGraph(getCordFromDB, barChartPosture);
        barGraph.drawGraph("posture");





    }


    public void tempFuntion(){

        //필드
        HueManager hueManager = HueManager.getInstance();
        PHHueSDK phHueSDK = hueManager.getPhHueSDK();
        PHBridgeSearchManager sm;
        PHAccessPoint accessPoint = new PHAccessPoint();


        //메소드
        phHueSDK.setAppName("default");
        phHueSDK.setDeviceName("default");
        phHueSDK.getNotificationManager().registerSDKListener(null);

//        검색하기
        sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);

//        검색하고 나면 결과 저장
        accessPoint.setIpAddress("");
        accessPoint.setUsername("");

//        저장된 결과 IP 주소에 연결
        phHueSDK.connect(accessPoint);
    }

    /**
     * 휴 디바이스를 컨트롤
     */
    private PHSDKListener hueListener = new PHSDKListener() {

        //        캐시데이터가 바뀐것을 감지한다 라이트의 ON/OFF 여부와 dimmer 등 데이터 변하는거다
        @Override
        public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {
            if(list.contains(PHMessageType.LIGHTS_CACHE_UPDATED)){
                LOG.DEBUG("Lights Cache Updated");
            }
        }

        //        브릿지와 연결되었을 때 사용할 코드를 여따 만들면 된다는군
        @Override
        public void onBridgeConnected(PHBridge phBridge, String s) {

        }

        //        원하는 IP주소로 보내기
        @Override
        public void onAuthenticationRequired(PHAccessPoint phAccessPoint) {

        }

        //브릿지 검색결과를 보여주는 곳이다
        @Override
        public void onAccessPointsFound(List<PHAccessPoint> list) {

        }

        //에러생기면 발생 뭔지는 모른다 생긴 오류를 서버로 보내는 걸로 해볼까?
        @Override
        public void onError(int i, String s) {

        }
        //        연결이 유지되면 주기적으로 나오는건가?
        @Override
        public void onConnectionResumed(PHBridge phBridge) {
            Log.d("test", "recalled function is onConnectionResumed" + phBridge.toString());
        }
        //        연결이 해제되면 호출
        @Override
        public void onConnectionLost(PHAccessPoint phAccessPoint) {

        }
        //        제이슨 파싱하다가 에러가 나면 이게뜸
        @Override
        public void onParsingErrors(List<PHHueParsingError> list) {

        }
    };

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
