package mountainq.kinggod.capstone.sogang.smartchairapp.graphs;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.StaticDatas;

/**
 * Created by jwahn37 on 2017. 3. 23..
 */

public class LineGraph extends Graph {

    public LineGraph(GetCordFromDB getCordFromDB, LineChart chart) {
        super(getCordFromDB, chart);
    }

    public void drawGraph(String sensorFlag)
    {
        List<Entry> entries = new ArrayList<Entry>();
        LineDataSet dataSet;
        //waist health data grpah
        if (sensorFlag.equals("waist")) {
            for (int i = cord.recentDateIdx; i < cord.numOfData; i++) {

                long hours= (long)(Long.parseLong(cord._time[i]))/100 +15;
                long minutes = (long)(Long.parseLong(cord._time[i]))%100;
                Log.d("value : ",Long.toString((Long.parseLong(cord._time[i]))));
                Log.d("hours : ",Long.toString(hours));
                Log.d("mintues : ",Long.toString(minutes));
                long millis = TimeUnit.HOURS.toMillis((long) hours) + TimeUnit.MINUTES.toMillis((long)minutes);
              //  entries.add(new Entry(Float.parseFloat(cord._time[i]), cord.waistHealth[i]));
                entries.add(new Entry((float)millis, cord.waistHealth[i]));
                //     entries.add(new Entry(Float.parseFloat(_time[i]), Float.parseFloat(waist[i])));

            }

            dataSet= new LineDataSet(entries, "Waist Health");

        }
        else {      //sensorFlag = neck
            for (int i = cord.recentDateIdx; i < cord.numOfData; i++) {
                long hours= (long)(Long.parseLong(cord._time[i]))/100 +15;
                long minutes = (long)(Long.parseLong(cord._time[i]))%100;
                Log.d("value : ",Long.toString((Long.parseLong(cord._time[i]))));
                Log.d("hours : ",Long.toString(hours));
                Log.d("mintues : ",Long.toString(minutes));
                long millis = TimeUnit.HOURS.toMillis((long) hours) + TimeUnit.MINUTES.toMillis((long)minutes);
                entries.add(new Entry((float)millis, cord.neckHealth[i]));
                //entries.add(new Entry(Float.parseFloat(cord._time[i]), cord.neckHealth[i]));
            }
            dataSet = new LineDataSet(entries, "Neck Health");
        }


        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        //dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        //dataSet.setDrawFilled(true);
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(4f);
        dataSet.setCircleRadius(1f);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setHighLightColor(StaticDatas.COLOR_PINK);
        dataSet.setColor(StaticDatas.COLOR_PINK);
        dataSet.setFillColor(StaticDatas.COLOR_PINK_ALPHA);
        dataSet.setDrawFilled(true);
//        dataSet.setFillAlpha(30);
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

        chart.setBackgroundColor(StaticDatas.COLOR_BACKGROUND);

        chart.setData(data);

        chart.invalidate();

    }



    class AxisTimeFormatter implements IAxisValueFormatter {  //axis format x축 시간화

        // private DecimalFormat mFormat;
        private SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm");

        public AxisTimeFormatter() {

            // format values to 1 decimal digit
            // mFormat = new DecimalFormat();
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            return mFormat.format(new Date((long)value));
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
