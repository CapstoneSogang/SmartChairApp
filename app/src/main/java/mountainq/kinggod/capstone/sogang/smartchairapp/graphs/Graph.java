package mountainq.kinggod.capstone.sogang.smartchairapp.graphs;

/**
 * Created by jwahn37 on 2017. 3. 20..
 */

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.GetCordFromDB;

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

/*
    private void getDataBase(String chartFlag)
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
        if (chartFlag.equals("line chart"))
            graphAlgorithm();
        //  else if (chartFlag.equals("pie chart"))
    }
*/
/*
    private void graphAlgorithm() //미분시켜서 그래프 좌표 뽑아내기 -> 30분 자세면 100%변화라 가정하자
    {
       // int dateBase=0; //가장 최근 날짜로부터의 데이터들

        for(int i=0;i<cord.numOfData;i++)
        {
            if(cord._date[i].equals(cord._date[cord.numOfData-1]))
            {
                dateBase=i; //가장 최근 날짜로부터의 데이터들
                Log.d("datebase", Integer.toString(i));
                Log.d("numOfData", Integer.toString(cord.numOfData));
                break;
            }
        }

        if (cord.waist[dateBase].equals("0")) //잘못된 자세로 앉기 시작
        {
            waistHealth[dateBase]=0;   //초기값
        }
        else    //정자세
            waistHealth[dateBase]=100; //단위는 %

        if (cord.neck[dateBase].equals("0")) //잘못된 자세로 앉기 시작
        {
            neckHealth[dateBase]=0;   //초기값
        }
        else    //정자세
            neckHealth[dateBase]=100; //단위는 %


        for(int i=dateBase+1;i<cord.numOfData;i++)
        {
            int waistFlag = (cord.waist[i].equals("0")) ? -1 : 1 ;    //+면 그래프상승 -면 그래프 하강
            int neckFlag = (cord.neck[i].equals("0")) ? -1 : 1 ;    //+면 그래프상승 -면 그래프 하강

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
            Log.d("datebase",Integer.toString(dateBase));
            Log.d("date",cord._date[i]);
            Log.d("waistHealth",cord.waist[i]);
            Log.d("neckHealth",cord.neck[i]);
        }





    }
*/


}



