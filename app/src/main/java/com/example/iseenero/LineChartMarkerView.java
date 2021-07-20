package com.example.iseenero;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LineChartMarkerView extends MarkerView {

    TextView square1;
    TextView square2;
    TextView square3;
    TextView item1;
    TextView item2;
    TextView item3;

    TextView Title;
    Context context;
    LineChart lineChart;
    int layoutResource;
    private long referenceTimestamp;
    private DateFormat mDataFormat;
    private Date mDate;
    public LineChartMarkerView(Context context, LineChart lineChart, int layoutResource, long axisX) {
        super(context, layoutResource);
        this.context = context;
        this.lineChart = lineChart;
        this.layoutResource = layoutResource;
        this.referenceTimestamp = axisX;
        this.mDataFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        this.mDate = new Date();
    }

    public void refreshContent(Entry e, Highlight highlight){
        square1 = findViewById(R.id.square1);
        item1 = findViewById(R.id.item1);
        Title = findViewById(R.id.txtTitle);
        long currentTimestamp = (int)e.getX() + referenceTimestamp;
        String warning="";
        try{
            Entry val1 = lineChart.getData().getDataSetByIndex(0).getEntryForXValue(e.getX(), Float.NaN, DataSet.Rounding.CLOSEST);
            if(val1.getY() >= 100){
                warning = "Aman";
                square1.setBackgroundColor(Color.BLUE);
            }else if(val1.getY() > 50 && val1.getY() < 100){
                warning = "Waspada";
                square1.setBackgroundColor(Color.YELLOW);
            }else if(val1.getY() <= 50){
                warning = "Berbahaya";
                square1.setBackgroundColor(Color.RED);
            }
            Title.setText(getTimedate(currentTimestamp) + " (" + warning + ")");
            item1.setText((int)val1.getY()+" CM");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset = null;
    public MPPointF getOffset(){
        if (mOffset == null) {
            mOffset = new MPPointF((-(getWidth() / 2)), (float)(-getHeight()));
        }
        return mOffset;
    }

    private String getTimedate(long timestamp){

        try{
            mDate.setTime(timestamp);
            return mDataFormat.format(mDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}

