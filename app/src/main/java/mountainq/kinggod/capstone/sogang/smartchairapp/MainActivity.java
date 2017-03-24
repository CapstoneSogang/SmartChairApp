package mountainq.kinggod.capstone.sogang.smartchairapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.DbOpenHelper;
import mountainq.kinggod.capstone.sogang.smartchairapp.datas.Graph;
import mountainq.kinggod.capstone.sogang.smartchairapp.datas.ReceiveData;
///
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TestDataBaseActivity";
    private DbOpenHelper mDbOpenHelper = new DbOpenHelper(this);
    private Cursor mCursor;
    private Graph waistGraph, neckGraph, pieGraph;
    private String date = "0319";
    // private InfoClass mInfoClass;
    // private ArrayList<infoclass> mInfoArray;
    // private CustomAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("???","zzzz");
        ReceiveData receiveData = new ReceiveData();
        receiveData.getData();



        // mDbOpenHelper = new DbOpenHelper(this);
        Log.d("insert", "zzz");
        setDataBase(mDbOpenHelper);
        LineChart waistChart = (LineChart) findViewById(R.id.waist_chart);
        waistGraph = new Graph(mDbOpenHelper,waistChart);
        waistGraph.drawLineGraph("waist");

        LineChart neckChart = (LineChart) findViewById(R.id.neck_chart);
        neckGraph = new Graph(mDbOpenHelper,neckChart);
        neckGraph.drawLineGraph("neck");
/*
        PieChart pieChart = (PieChart) findViewById(R.id.pie_chart);
        pieGraph = new Graph(mDbOpenHelper, pieChart);

        pieGraph.drawPieGraph(date);
*/

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
