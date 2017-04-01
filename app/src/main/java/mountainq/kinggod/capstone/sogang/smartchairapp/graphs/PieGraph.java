package mountainq.kinggod.capstone.sogang.smartchairapp.graphs;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.StaticDatas;

/**
 * Created by jwahn37 on 2017. 3. 23..
 */

public class PieGraph extends Graph {
    public PieGraph(GetCordFromDB getCordFromDB, PieChart chart) {
        super(getCordFromDB, chart);
    }

    public void drawGraph(String date)
    {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        //pieChart.setCenterTextTypeface(mTfLight);
        //pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);

        pieChart.setTransparentCircleColor(StaticDatas.COLOR_BACKGROUND);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(false);

        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener
        // pieChart.setOnChartValueSelectedListener(this);

        // setData(4, 100);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);



        //set data

        ArrayList<PieEntry> entries = setPieData(date);
        Log.d("recent date " ,date);
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

        dataSet.setSliceSpace(5f);
        //dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setColors(StaticDatas.COLOR_ARRAY);

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


    ArrayList<PieEntry> setPieData(String date)
    {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        float waistPro=0,neckPro=0,bothPro=0,goodPos=0;

        for(int i=cord.recentDateIdx;i<cord.numOfData;i++) {

            if(cord._date[i].equals(date))
            {
                if(cord.neck[i].equals("0") && cord.waist[i].equals("0")) // 건강한 상태
                    goodPos++;
                if(!cord.neck[i].equals("0") && cord.waist[i].equals("0")) // 목디스크 상태
                    neckPro++;
                if(cord.neck[i].equals("0") && !cord.waist[i].equals("0")) // 허리디스크 상태
                    waistPro++;
                if(!cord.neck[i].equals("0") && !cord.waist[i].equals("0")) // 양쪽문제 상태
                    bothPro++;

                Log.d("pie waist : ",Float.toString(waistPro));

            }
           /* if(cord._date[i].equals(date))
            {
                if(cord.neck[i].equals("0") && cord.waist[i].equals("0")) // 건강한 상태
                    goodPos++;
                if(!cord.neck[i].equals("0") && cord.waist[i].equals("0")) // 건강한 상태
                    neckPro++;
                if(cord.neck[i].equals("0") && !cord.waist[i].equals("0")) // 건강한 상태
                    waistPro++;
                if(!cord.neck[i].equals("0") && !cord.waist[i].equals("0")) // 건강한 상태
                    bothPro++;

            }
*/

        }
       // Log.d("Pie graph : ",Float.toString(goodPos));

        entries.add(new PieEntry(goodPos,"Healthy"));
        entries.add(new PieEntry(neckPro,"Neck Problem"));
        entries.add(new PieEntry(waistPro,"Waist Problem"));
        entries.add(new PieEntry(bothPro,"Both problem"));


        return entries;

    }

}
