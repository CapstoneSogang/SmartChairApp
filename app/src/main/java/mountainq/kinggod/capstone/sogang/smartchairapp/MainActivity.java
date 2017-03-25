package mountainq.kinggod.capstone.sogang.smartchairapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.DbOpenHelper;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.BarGraph;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.GetCordFromDB;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.Graph;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.LineGraph;
import mountainq.kinggod.capstone.sogang.smartchairapp.graphs.PieGraph;
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

      //  setDataBase(mDbOpenHelper);
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


    protected void setDataBase(DbOpenHelper mDbOpenHelper) //디비 설정하기
    {
        mDbOpenHelper.open();

        //정자세 -> 걸쳐앉음 -> 정자세 -> 거북목 -> 정자세
        mDbOpenHelper.deleteAll(); //나중에 삭제할 부분. 디버깅용

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


        Log.d("insert", "zzz");

    }
}
