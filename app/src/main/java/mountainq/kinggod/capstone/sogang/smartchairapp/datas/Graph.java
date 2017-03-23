package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

/**
 * Created by jwahn37 on 2017. 3. 20..
 */

import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jwahn37 on 2017. 3. 19..
 */

public class Graph {

    private final int MAX_DATA=1000;
    private final float TIME_SPAN = 1; //1분단위 센서값 가정시
    private final float FULL_SPAN = 5; //30분 앉아있으면 가장 멕시멈 / 미니멈된다고 가정

    DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;
    private LineChart chart;
    private PieChart pieChart;

    private String _date[] = new String[ MAX_DATA];     //디비 내용 저장
    private String _time[] = new String[ MAX_DATA];
    private String waist[] = new String[ MAX_DATA];
    private String neck[] = new String[ MAX_DATA];

    private float waistHealth[] = new float[MAX_DATA];
    private float neckHealth[] = new float[MAX_DATA];



    private int numOfData;

    public Graph(DbOpenHelper mDbOpenHelper, LineChart chart)    //여기서 디비 내용을 읽어온다
    {
        this.mDbOpenHelper = mDbOpenHelper;
        this.chart = chart;
        getDataBase("line chart");
    }

    public Graph(DbOpenHelper mDbOpenHelper, PieChart chart)    //pie chart : 여기서 디비 내용을 읽어온다
    {
        this.mDbOpenHelper = mDbOpenHelper;
        this.pieChart = chart;
        getDataBase("pie chart");
    }


    private void getDataBase(String chartFlag)
    {
        mCursor = mDbOpenHelper.readDbHelper("0319");
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

    private void graphAlgorithm() //미분시켜서 그래프 좌표 뽑아내기 -> 30분 자세면 100%변화라 가정하자
    {
        if (waist[0].equals("0")) //잘못된 자세로 앉기 시작
        {
            waistHealth[0]=0;   //초기값
        }
        else    //정자세
            waistHealth[0]=100; //단위는 %

        if (neck[0].equals("0")) //잘못된 자세로 앉기 시작
        {
            neckHealth[0]=0;   //초기값
        }
        else    //정자세
            neckHealth[0]=100; //단위는 %


        for(int i=1;i<numOfData;i++)
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

            Log.d("waistHealth",Float.toString(waistHealth[i]));
        }





    }

    public void drawLineGraph(String sensorFlag)
    {
        List<Entry> entries = new ArrayList<Entry>();
        LineDataSet dataSet;
        //waist health data grpah
        if (sensorFlag.equals("waist")) {
            for (int i = 0; i < numOfData; i++)
                entries.add(new Entry(Float.parseFloat(_time[i]), waistHealth[i]));
            //     entries.add(new Entry(Float.parseFloat(_time[i]), Float.parseFloat(waist[i])));
            dataSet= new LineDataSet(entries, "Waist Health");
        }
        else {      //sensorFlag = neck
            for (int i = 0; i < numOfData; i++)
                entries.add(new Entry(Float.parseFloat(_time[i]), neckHealth[i]));
            dataSet = new LineDataSet(entries, "Neck Health");
        }


        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        //dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        //dataSet.setDrawFilled(true);
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(1.8f);
        dataSet.setCircleRadius(1f);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setHighLightColor(Color.rgb(244, 117, 117));
        dataSet.setColor(Color.BLACK);
        dataSet.setFillColor(Color.rgb(244, 117, 117));
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(100);
        //dataSet.setDrawHorizontalHighlightIndicator(false);
        dataSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });

        //
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(dataSet);



        LineData data = new LineData(dataSet);
        //     data.setValueTypeface(mTfLight);
        data.setValueTextSize(9f);
        data.setDrawValues(false);


        //axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        //   xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
// set a custom value formatter
        xAxis.setValueFormatter(new AxisTimeFormatter());
        //

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setValueFormatter(new AxisPercentFormatter());
        // yAxis.setTypeface(...); // set a different font
        yAxis.setTextSize(12f); // set the text size
        yAxis.setAxisMinimum(0f); // start at zero
        yAxis.setAxisMaximum(100f); // the axis maximum is 100
        yAxis.setTextColor(Color.BLACK);
        //yAxis.setValueFormatter(new MyValueFormatter());
        yAxis.setGranularity(1f); // interval 1
        yAxis.setLabelCount(6, true); // force 6 labels
        yAxis.setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);

        //   chart.setViewPortOffsets(0, 0, 0, 0);
        chart.setBackgroundColor(Color.WHITE);

        // no description text
        //  chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(false);

        // enable scaling and dragging
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);

        chart.setData(data);

        chart.invalidate();

    }

    public void drawPieGraph(String date)
    {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        //pieChart.setCenterTextTypeface(mTfLight);
        //pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener
        // pieChart.setOnChartValueSelectedListener(this);

        // setData(4, 100);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);



        //set data
        ArrayList<PieEntry> entries = setPieData();
        /*
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        for (int i = 0; i < 5 ; i++) {
            //여기서 막혔당 ㅠㅠ

            entries.add(new PieEntry((float)  5));
        }
*/
        PieDataSet dataSet = new PieDataSet(entries, "Evaluation of your posture");

        //dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        //dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
       // data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }


    ArrayList<PieEntry> setPieData()
    {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        float waistPro=0,neckPro=0,bothPro=0,goodPos=0;

        for(int i=0;i<(numOfData=mCursor.getCount());i++) {
            if(_date[i].equals("0319"))
            {
                if(neck[i].equals("0") && waist[i].equals("0")) // 건강한 상태
                    goodPos++;
                if(!neck[i].equals("0") && waist[i].equals("0")) // 건강한 상태
                    neckPro++;
                if(neck[i].equals("0") && !waist[i].equals("0")) // 건강한 상태
                    waistPro++;
                if(!neck[i].equals("0") && !waist[i].equals("0")) // 건강한 상태
                    bothPro++;

            }

        }

        entries.add(new PieEntry(goodPos,"Healthy"));
        entries.add(new PieEntry(neckPro,"Neck Problem"));
        entries.add(new PieEntry(waistPro,"Waist Problem"));
        entries.add(new PieEntry(bothPro,"Both problem"));


        return entries;

    }






    class AxisTimeFormatter implements IAxisValueFormatter {  //axis format x축 시간화

        // private DecimalFormat mFormat;

        public AxisTimeFormatter() {

            // format values to 1 decimal digit
            // mFormat = new DecimalFormat();
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            String time=Float.toString(value);
            String hour, minute;
            time = time.substring(0,time.length()-2);


            while(time.length()!=4) //앞에 0014 일경우 00 을 추가해야함
            {
                time='0'+time;
            }

            // Log.d("time" , time);

            hour = time.substring(0,2);
            minute = time.substring(2,4);

            return hour+":"+minute;
        }

        /** this is only needed if numbers are returned, else return 0 */
        //@Override
        public int getDecimalDigits() { return 1; }
    }

    class AxisPercentFormatter implements IAxisValueFormatter { //axis formatter : y축 %화

        // private DecimalFormat mFormat;

        public AxisPercentFormatter() {

            // format values to 1 decimal digit
            // mFormat = new DecimalFormat();
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            String percent=Float.toString(value);
            percent = percent.substring(0,percent.length()-2);
            return percent+"%";
        }

        /** this is only needed if numbers are returned, else return 0 */
        //@Override
        public int getDecimalDigits() { return 1; }
    }
}


