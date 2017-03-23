package mountainq.kinggod.capstone.sogang.smartchairapp.graphs;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import mountainq.kinggod.capstone.sogang.smartchairapp.datas.DbOpenHelper;

/**
 * Created by jwahn37 on 2017. 3. 23..
 */

public class PieGraph extends Graph {
    public PieGraph(DbOpenHelper mDbOpenHelper, PieChart chart) {
        super(mDbOpenHelper, chart);
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
        ArrayList<PieEntry> entries = setPieData(date);
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


    ArrayList<PieEntry> setPieData(String date)
    {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        float waistPro=0,neckPro=0,bothPro=0,goodPos=0;

        for(int i=dateBase;i<numOfData;i++) {
            if(_date[i].equals(date))
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

}
