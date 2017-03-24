package mountainq.kinggod.capstone.sogang.smartchairapp.graphs;

/**
 * Created by jwahn37 on 2017. 3. 20..
 */

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

/**
 * Created by jwahn37 on 2017. 3. 19..
 */

public class Graph {

 //   protected final int MAX_DATA = 1000;
  //  protected final float TIME_SPAN = 1; //1분단위 센서값 가정시
   // protected final float FULL_SPAN = 5; //30분 앉아있으면 가장 멕시멈 / 미니멈된다고 가정
    protected GetCordFromDB cord; //corridnate 가 모조리 몰려있다
    //  DbOpenHelper mDbOpenHelper;
    //   protected Cursor mCursor;
    protected LineChart chart;
    protected PieChart pieChart;
    protected BarChart barChart;
/*
    protected String _date[] = new String[ MAX_DATA];     //디비 내용 저장
    protected String _time[] = new String[ MAX_DATA];
    protected String waist[] = new String[ MAX_DATA];
    protected String neck[] = new String[ MAX_DATA];
*/

  //  protected float waistHealth[] = new float[MAX_DATA];
   // protected float neckHealth[] = new float[MAX_DATA];

 //   protected int dateBase = 0; //가장 최근 날짜의 인덱스


    //   protected int numOfData;

    public Graph(GetCordFromDB getCordFromDB, LineChart chart)    //여기서 디비 내용을 읽어온다
    {
        //  this.mDbOpenHelper = mDbOpenHelper;
        this.cord = getCordFromDB;
        this.chart = chart;
        //   getDataBase("line chart");
    }

    public Graph(GetCordFromDB getCordFromDB, PieChart chart)    //pie chart : 여기서 디비 내용을 읽어온다
    {
        //this.mDbOpenHelper = mDbOpenHelper;
        this.cord = getCordFromDB;
        this.pieChart = chart;
        //getDataBase("pie chart");
    }

    public Graph(GetCordFromDB getCordFromDB, BarChart chart)    //pie chart : 여기서 디비 내용을 읽어온다
    {
        //this.mDbOpenHelper = mDbOpenHelper;
        this.cord = getCordFromDB;
        this.barChart = chart;
        //  getDataBase("bar chart");
    }

    public void drawGraph(String flag) {
    }


}



