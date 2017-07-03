package com.example.wangchang.testbottomnavigationbar;

import android.animation.TimeAnimator;
import android.app.Activity;

import android.app.TimePickerDialog;
import android.os.Bundle;


import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.wangchang.testbottomnavigationbar.fragment.TimePickerFragment;

import java.text.SimpleDateFormat;

import static android.R.attr.format;

/**
 * Created by ACM on 2017/7/2.
 */

public class TaskSettingactivity extends AppCompatActivity {
    private EditText txtview;
    private EditText endTxtview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        txtview = (EditText)findViewById(R.id.starttime);
        endTxtview = (EditText)findViewById(R.id.endtime);
        txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(TaskSettingactivity.this, TimeListener,1,0,true);
                dialog.show();


            }
        });
        endTxtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(TaskSettingactivity.this, TimeListener2,1,0,true);
                dialog.show();
            }
        });

    }
    private TimePickerDialog.OnTimeSetListener TimeListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            txtview.setText(i+":"+i1);
        }

    };
    private TimePickerDialog.OnTimeSetListener TimeListener2 = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            endTxtview.setText(i+":"+i1);
        }

    };
}
