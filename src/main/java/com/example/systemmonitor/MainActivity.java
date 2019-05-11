package com.example.systemmonitor;

import android.app.AlertDialog;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener,GestureDetector.OnGestureListener, View.OnClickListener{
    private GestureDetector gestureDetector;
    private ImageButton imageButton;
    private TextView usedText;
    private TextView totalText;
    private TextView remianText;
    private NetworkStatsManager networkStatsManager;
    private int plan_total=0;
    private LinearLayout planLayout;
    private Paint mCirclePaint;
    private Paint mCircleInnerPaint;
    private TextPaint mTextPaint;
    private WaveProgressView waveProgressView;
    private int[] plan=new int[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("plan");
        //receive plan information
        if(bundle!=null){
            plan=bundle.getIntArray("planInfo");
            plan_total=plan[6];
        }
        PermissionHelper.checkPermission(this);
        setContentView(R.layout.activity_main);
        init();
        showPlan();
        netWorkStats();
    }

    private void init(){
        imageButton=findViewById(R.id.ic_setting);
        LinearLayout fLinearLayout=findViewById(R.id.first_activity);
        fLinearLayout.setOnTouchListener(this);
        fLinearLayout.setLongClickable(true);
        gestureDetector =new GestureDetector((GestureDetector.OnGestureListener)this);
        planLayout=findViewById(R.id.main_planLayout);
        usedText=findViewById(R.id.main_used);
        totalText=findViewById(R.id.main_total);
        remianText=findViewById(R.id.usage_remain);
        networkStatsManager=(NetworkStatsManager)getSystemService(NETWORK_STATS_SERVICE);
        waveProgressView=findViewById(R.id.wave_progress);

    }
    private void showPlan(){

        totalText.setText(Integer.toString(plan_total));
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

    /**
     * left slide and right slide
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final int FLING_MIN_DISTANCE=100;
        final int FLING_MIN_VELOCITY=0;
        if(e1.getX()-e2.getX()>FLING_MIN_DISTANCE && Math.abs(velocityX)> FLING_MIN_VELOCITY){
            //switch activity
            Intent intent=new Intent(this, SecondPage.class);
            startActivity(intent);
            Toast.makeText(this,"left slide",Toast.LENGTH_SHORT).show();
        }else if(e2.getX()-e1.getX()>FLING_MIN_DISTANCE && Math.abs(velocityX)>FLING_MIN_VELOCITY){
            //switch activity
            Intent intent=new Intent(this, ThirdPage.class);
            startActivity(intent);
            Toast.makeText(this,"right slide",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * click functions
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.ic_setting:
            Intent intent=new Intent(this, PlanPage.class);
            startActivity(intent);
            Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
            case R.id.main_planLayout:
                Intent intent2=new Intent(this,PlanPage.class);
                startActivity(intent2);
                Toast.makeText(this,"setting plan",Toast.LENGTH_SHORT).show();
        default:
           break;
        }
    }

    /**
     * get usage
     */
    public void netWorkStats() {
        //get the total usage in this device
        NetworkStats.Bucket bucket;
        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI, "", 0, System.currentTimeMillis());
            long used=(bucket.getRxBytes() + bucket.getTxBytes());
            long remain=plan_total*1073741824-used;
            if(remain<0){
                remain=0;
            }
            if(used>=1073741824){
                used=used/1073741824;
                usedText.setText(Long.toString(used)+" "+"GB");
            }else if(used<1073741824 && used>=1048576){
                used=used/1048576;
                usedText.setText(Long.toString(used)+" "+"GB");
            }else if(used>=1024 && used<1048576){
                used=used/1024;
                usedText.setText(Long.toString(used)+" "+"GB");
            }
            usedText.setText(Long.toString(used)+" "+"GB");
            if(remain>=1073741824){
                remain=remain/1073741824;
                remianText.setText(Long.toString(remain)+" "+"GB");
            }else if(remain<1073741824 && remain>=1048576){
                remain=remain/1048576;
                remianText.setText(Long.toString(remain)+" "+"MB");
            }else if(remain>=1024 && remain<1048576){
                remain=remain/1024;
                remianText.setText(Long.toString(remain)+" "+"KB");
            }else {
                remianText.setText(Long.toString(remain) + " " + "B");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
