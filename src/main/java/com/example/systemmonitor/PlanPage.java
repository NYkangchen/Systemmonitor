package com.example.systemmonitor;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlanPage extends AppCompatActivity {
    private EditText usage;
    private Button confirmButton;
    private Button cancelButton;
    private int[] planInfo;
    private Calendar calendar;
    private Button planDate;
    private String USER_INFO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_plan);
        init();
        planClick();

    }
    private void init(){
        usage=findViewById(R.id.plan_usage);
        confirmButton=findViewById(R.id.plan_confirm);
        cancelButton=findViewById(R.id.plan_cancel);
        planDate=findViewById(R.id.plan_Date);
        calendar=Calendar.getInstance();
        planInfo=new int[7];
    }


    private void planClick(){
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usage.getEditableText()!=null){
                    int plan_total=Integer.parseInt(usage.getText().toString());
                    planInfo[6]=plan_total;
                    Intent intent=new Intent(PlanPage.this,MainActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putIntArray("planInfo",planInfo);
                    intent.putExtra("plan",bundle);
                    startActivity(intent);
                }else {
                    Toast.makeText(PlanPage.this, "input fail, please check your plan setting", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(PlanPage.this,MainActivity.class);
                startActivity(intent2);
                Toast.makeText(PlanPage.this,"cancel",Toast.LENGTH_SHORT).show();
            }
        });
        planDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new DoubleDatePickerDialog(PlanPage.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth) {
                        SimpleDateFormat sdf=new SimpleDateFormat("mm-dd-yyyy");
                        String textString = String.format("Start Date：%d-%d-%d\nEnd Date：%d-%d-%d\n",
                                startMonthOfYear + 1, startDayOfMonth,startYear, endMonthOfYear + 1, endDayOfMonth,endYear);
                        planDate.setText(textString);
                        planInfo[0]=startYear;
                        planInfo[1]=startMonthOfYear;
                        planInfo[2]=startDayOfMonth;
                        planInfo[3]=endYear;
                        planInfo[4]=endMonthOfYear;
                        planInfo[5]=endDayOfMonth;
                        try {
                            Date date=(Date)sdf.parse(textString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),true).show();
            }
        });
    }
}
