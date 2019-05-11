package com.example.systemmonitor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class SecondPage extends Activity implements View.OnTouchListener, GestureDetector.OnGestureListener, View.OnClickListener {
    private GestureDetector gestureDetector;
    private ImageButton moreIB;
    private PieChart pieChart;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        gestureDetector =new GestureDetector((GestureDetector.OnGestureListener)this);
        findVeiw();
        showpieChart();
    }
    public void findVeiw(){
        moreIB=findViewById(R.id.second_more);
        LinearLayout sLinearLayout=findViewById(R.id.second_activity);
        sLinearLayout.setOnTouchListener(this);
        sLinearLayout.setLongClickable(true);
        pieChart=findViewById(R.id.piechart);
    }

    private void showpieChart(){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.2f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(20f);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.animateX(1000, Easing.EaseInExpo);

        ArrayList<PieEntry> pieValues=new ArrayList<>();
        pieValues.add(new PieEntry(40,"Game"));
        pieValues.add(new PieEntry(20,"Others"));
        pieValues.add(new PieEntry(20,"Music"));
        pieValues.add(new PieEntry(50,"Communication"));


        PieDataSet dataSet=new PieDataSet(pieValues,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(8f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData pieData=new PieData(dataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.BLUE);
        pieData.setValueFormatter(new PercentFormatter());
        pieChart.setData(pieData);

        Legend l = pieChart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(10f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(10f); // set the space between the legend entries on the y-axis



    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final int FLING_MIN_DISTANCE=100;
        final int FLING_MIN_VELOCITY=0;
        if(e1.getX()-e2.getX()>FLING_MIN_DISTANCE && Math.abs(velocityX)> FLING_MIN_VELOCITY){
            //switch activity
            Intent intent=new Intent(this, ThirdPage.class);
            startActivity(intent);
            Toast.makeText(this,"left slide",Toast.LENGTH_SHORT).show();
        }else if(e2.getX()-e1.getX()>FLING_MIN_DISTANCE && Math.abs(velocityX)>FLING_MIN_VELOCITY){
            //switch activity
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this,"right slide",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.second_more:
                Intent intent=new Intent(this, ThirdPage.class);
                startActivity(intent);
                default:
                    break;
        }
    }
}
