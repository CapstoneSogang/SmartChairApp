package mountainq.kinggod.capstone.sogang.smartchairapp.graphs;

import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.StaticDatas;

/**
 * Created by jwahn37 on 2017. 3. 23..
 */

public class BarGraph extends Graph {
    public BarGraph(GetCordFromDB getCordFromDB, BarChart chart) {
        super(getCordFromDB, chart);
    }


    public void drawGraph(String barFlag)
    {
        //  barChart.setOnChartValueSelectedListener(this);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);

        barChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawGridBackground(false);
        // barChart.setDrawYLabels(false);
        barChart.setTouchEnabled(false);

        //IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new AxisTimeFormatter());
        //xAxis.setValueFormatter(xAxisFormatter);

        //IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = barChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        // leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        // rightAxis.setTypeface(mTfLight);
        rightAxis.setLabelCount(8, false);
        // rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
/*
        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(barChart); // For bounds control
        barChart.setMarker(mv); // Set the marker to the chart

        setData(12, 50);

        // setting data
        mSeekBarY.setProgress(50);
        mSeekBarX.setProgress(12);

        mSeekBarY.setOnSeekBarChangeListener(this);
        mSeekBarX.setOnSeekBarChangeListener(this);
*/

        setBarData(barFlag);
        // barChart.setDrawLegend(false);




    }

    void setBarData(String barFlag)
    {
        float start = 1f;
        float allData=0,goodPos=0;
        int tp=0;
        int dateIdx;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        //날짜별 자세의 건강도 = goodPos/allData
//        Log.d("numofdata",Integer.toString(numOfData=mCursor.getCount()));
        if(barFlag.equals("posture")) { //자세 막대 그래프 그리기
            dateIdx=0;
            for (int i = 0; i < cord.numOfData; i++) {
               // if(cord._date[i].equals(cord._date))
                 //   dateIdx++;

                if (i == 0) {
                    if (cord.neck[i].equals("0") && cord.waist[i].equals("0")) // 건강한 상태
                        goodPos++;
                    allData++;
                } else if (cord._date[i].equals(cord._date[i - 1]) && i != cord.numOfData - 1) {
                    if (cord.neck[i].equals("0") && cord.waist[i].equals("0")) // 건강한 상태
                        goodPos++;
                    allData++;
                } else {
/*
                    long hours= (long)(Long.parseLong(cord._time[i]))/100 +15;
                    long minutes = (long)(Long.parseLong(cord._time[i]))%100;
                    Log.d("value : ",Long.toString((Long.parseLong(cord._time[i]))));
                    Log.d("hours : ",Long.toString(hours));
                    Log.d("mintues : ",Long.toString(minutes));
                    long millis = TimeUnit.HOURS.toMillis((long) hours) + TimeUnit.MINUTES.toMillis((long)minutes);
                    //  entries.add(new Entry(Float.parseFloat(cord._time[i]), cord.waistHealth[i]));
                    entries.add(new Entry((float)millis, cord.waistHealth[i]));
  */
                //    long month = (long)(Long.parseLong(cord._date[i]))/100;
                 //   long days= (long)(Long.parseLong(cord._date[i]))%100;
                  //  long millis = TimeUnit.DAYS.toMillis((long) (getDaysFromMonth((int)month))+days) ;

                    if (allData == 0) {

                    } else if (goodPos == 0) //여기가 add 부분

                       // yVals1.add(new BarEntry(Integer.parseInt(cord._date[i - 1]), 0));
                        yVals1.add(new BarEntry(dateIdx++, 0));
                    else {
                       // yVals1.add(new BarEntry(Integer.parseInt(cord._date[i - 1]), goodPos / allData));
                         yVals1.add(new BarEntry(dateIdx++, goodPos / allData));
                        // Log.d(Integer.toString(tp),Float.toString(goodPos / allData));
                        Log.d("bar", cord._date[i]);
                    }
                    allData = 0;
                    goodPos = 0;
                }

            }
        }
        else { //공부량 막대 그래프 그리기
            dateIdx=0;
            for(int i=0; i<cord.numOfData;i ++)
            {
                //if(cord._date[i].equals(cord._date))
                 //   dateIdx++;
               // long month = (long)(Long.parseLong(cord._date[i]))/100;
                //long days= (long)(Long.parseLong(cord._date[i]))%100;
               // long millis = TimeUnit.DAYS.toMillis((long) (getDaysFromMonth((int)month))+days) ;


                if(i==0)
                    allData++;
                else if(cord._date[i].equals(cord._date[i-1]) && i != cord.numOfData-1)
                    allData++;
                else{
                    yVals1.add(new BarEntry(dateIdx++, allData));
                    allData=0;
                }
            }

        }

        BarDataSet set1;

        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            if(barFlag.equals("posture")) //자세 막대그래프 이름 설정
                set1 = new BarDataSet(yVals1, "Your posture habit");
            else    //작업량 막대 그래프 이름 설정
                set1 = new BarDataSet(yVals1, "Your working hour");

            // set1.setDrawIcons(false);

            set1.setColors(StaticDatas.COLOR_ARRAY2);


            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            //data.setBarWidth(90000f);

            barChart.setData(data);
        }

    }


    public int getDaysFromMonth(int month)
    {
        int[] eachMonth=new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        int days=0;

        for(int i=0;i<month-1;i++)
        {
            days+=eachMonth[i];
        }
        return days;
    }

    class AxisTimeFormatter implements IAxisValueFormatter {  //axis format x축 시간화

        // private DecimalFormat mFormat;
        private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM");

        public AxisTimeFormatter() {

            // format values to 1 decimal digit
            // mFormat = new DecimalFormat();
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            //Log.d("haha0 : ",cord._date[0]);

            long month = (long)(Long.parseLong(cord._date[0]))/100;
            long days= (long)(Long.parseLong(cord._date[0]))%100;


            Log.d("haha : ",Float.toString(value));
          //  Log.d("haha : ",Long.toString(days));
            long millis = TimeUnit.DAYS.toMillis((long) (getDaysFromMonth((int)month))+days+(long)value) ;


            return mFormat.format(new Date((long)millis));
        }

        /** this is only needed if numbers are returned, else return 0 */
        //@Override
        public int getDecimalDigits() { return 1; }
    }

}

