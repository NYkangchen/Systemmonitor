package com.example.systemmonitor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class ThirdPage extends Activity implements View.OnTouchListener, GestureDetector.OnGestureListener, OnChartValueSelectedListener {
    private final int WC= ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FP=ViewGroup.LayoutParams.MATCH_PARENT;
    private GestureDetector gestureDetector;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
        findVeiw();
        showTable();
        gestureDetector =new GestureDetector((GestureDetector.OnGestureListener)this);

    }
    public void showTable(){
        TableLayout tableLayout= findViewById(R.id.third_table);
        tableLayout.setStretchAllColumns(true);
        for(int row=1;row<20;row++){
            TableRow tableRow=new TableRow(this);
            for(int col=0;col<4;col++){
                TextView textView=new TextView(this);
                textView.setText("");
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow,new TableLayout.LayoutParams(FP,WC));
        }
    }
    public void findVeiw(){
        LinearLayout tLinearLayout=findViewById(R.id.third_activity);
        tLinearLayout.setOnTouchListener(this);
        tLinearLayout.setLongClickable(true);
        barChart=findViewById(R.id.barchart);
    }
    private void showbarChart(){

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
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this,"left slide",Toast.LENGTH_SHORT).show();
        }else if(e2.getX()-e1.getX()>FLING_MIN_DISTANCE && Math.abs(velocityX)>FLING_MIN_VELOCITY){
            //switch activity
            Intent intent=new Intent(this, SecondPage.class);
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
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
