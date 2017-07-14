package com.example.wangchang.wda1150_xwk1151;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;

import android.os.SystemClock;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wangchang.wda1150_xwk1151.Been.TaskBeen;
import com.gc.materialdesign.views.CheckBox;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.example.wangchang.wda1150_xwk1151.R.color.colorAccent;

/**
 * Created by ACM on 2017/7/2.
 */

public class TaskSettingactivity extends AppCompatActivity {
    private EditText startTxtview;
    private EditText titleView;
    private EditText endTxtview;
    private ListPopupWindow mListPop;
    private EditText fre;
    private CheckBox isalarm;
    private EditText description;
    private EditText remind_time;
    private MaterialSpinner materialSpinner;
    private int start = 0;
    private int end = 0;
    private ActionMenuItemView save;
    private long taskid;
    private TaskBeen tasknow;
    private Long id;
    private CheckBox checkbox;
    private int mhour;
    private int mminute;
    private int day;
    private int month;
    private int year;
    private int frequency = 0;
    private  String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.task_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(colorAccent));
        toolbar.setTitle("任务详细设置");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.inflateMenu(R.menu.task_toolbar_menu);
        frequency=0;
        taskid = getIntent().getExtras().getLong("taskId");
        DaoMaster.DevOpenHelper devopenhelper = new DaoMaster.DevOpenHelper(getApplicationContext(),"task-db",null);
        DaoMaster daomaster = new DaoMaster(devopenhelper.getWritableDatabase());
        DaoSession daosession = daomaster.newSession();
        final TaskBeenDao taskBeenDao = daosession.getTaskBeenDao();
        description = (EditText)findViewById(R.id.task_description);

        startTxtview = (EditText)findViewById(R.id.starttime);
        remind_time = (EditText)findViewById(R.id.remind_time);
        titleView = (EditText)findViewById(R.id.tasktitle_set);
        isalarm = (CheckBox)findViewById(R.id.isAlarm);
        materialSpinner = (MaterialSpinner)findViewById(R.id.spinner);
        materialSpinner.setItems("一次性","每天","每周");
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    if(item.toString().equals("一次性")){
                        frequency = 0;
                    }else if(item.toString().equals("每天")){
                        frequency = 1;
                    }else {
                        frequency = 2;
                    }
            }
        });
        checkbox = (CheckBox)findViewById(R.id.checkboxAlarm);
        endTxtview = (EditText)findViewById(R.id.endtime);
        taskid = getIntent().getLongExtra("taskId",-1);

        if(taskid!=-1) {
            tasknow = taskBeenDao.queryBuilder().where(TaskBeenDao.Properties.Id.eq(taskid)).build().unique();

            date = tasknow.getDate();
            Log.d("date", "onClick: "+date);
            year = Integer.valueOf(date.split("-")[0]);
            month = Integer.valueOf(date.split("-")[1]);
            day = Integer.valueOf(date.split("-")[2]);

            if(tasknow.getRemindTime()!=null) {
                mhour = Integer.valueOf(tasknow.getRemindTime().split(":")[0]);
                mminute = Integer.valueOf(tasknow.getRemindTime().split(":")[1]);
            }


            startTxtview.setText(tasknow.getStartTime());
            endTxtview.setText(tasknow.getEndTime());
            titleView.setText(tasknow.getTitle());
            description.setText(tasknow.getDescription());
            if(tasknow.getRemindTime()!=null){
                isalarm.setChecked(true);
            }else{
                isalarm.setChecked(false);
            }
            remind_time.setText(tasknow.getRemindTime());
        }



        startTxtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(TaskSettingactivity.this, TimeListener,1,0,true);
                dialog.show();


            }
        });
        remind_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(TaskSettingactivity.this, TimeListener3,1,0,true);
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


        save = (ActionMenuItemView)findViewById(R.id.ok);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(start>end){
                    Toast.makeText(getApplicationContext(),"开始时间不能比结束时间迟",Toast.LENGTH_LONG).show();
                }else {
                    if (taskid != -1) {
                        tasknow.setEndTime(endTxtview.getText().toString());
                        tasknow.setRemindTime(remind_time.getText().toString());
                        tasknow.setTitle(titleView.getText().toString());
                        tasknow.setStartTime(startTxtview.getText().toString());
                        tasknow.setDescription(description.getText().toString());
                        tasknow.setIsCompleted(checkbox.isCheck());
                        tasknow.setDate(date);

                        taskBeenDao.update(tasknow);
                    } else {
                        TaskBeen temp = taskBeenDao.queryBuilder().orderDesc(TaskBeenDao.Properties.Id).limit(1).unique();
                        if (temp == null) {
                            id = 1L;
                        } else {
                            id = temp.getId() + 1;
                        }
                        date = getIntent().getStringExtra("date");
                        year = Integer.valueOf(date.split("-")[0]);
                        month = Integer.valueOf(date.split("-")[1]);
                        day = Integer.valueOf(date.split("-")[2]);

                        tasknow = new TaskBeen(id, date, startTxtview.getText().toString(), endTxtview.getText().toString(), description.getText().toString(), checkbox.isCheck(), remind_time.getText().toString(), titleView.getText().toString());
                        taskBeenDao.insert(tasknow);
                    }


                    if (tasknow.getRemindTime() != null&&isalarm.isCheck()) {
                        Intent intentemp = new Intent(TaskSettingactivity.this, AlarmReceiver.class);

                        PendingIntent sender = PendingIntent.getBroadcast(TaskSettingactivity.this, 0, intentemp, 0);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month-1);
                        calendar.set(Calendar.DAY_OF_MONTH,day);
                        calendar.set(Calendar.MINUTE, mminute);
                        calendar.set(Calendar.HOUR_OF_DAY, mhour);
                        Log.d("年", "onClick: "+calendar.get(Calendar.YEAR));
                        Log.d("月","onClick: "+calendar.get(Calendar.MONTH));
                        Log.d("日","onClick: "+calendar.get(Calendar.DAY_OF_MONTH));
                        Log.d("小时", "onClick: "+calendar.get(Calendar.HOUR_OF_DAY));
                        Log.d("分钟", "onClick: "+calendar.get(Calendar.MINUTE));
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        Log.d("frequency", "onClick: "+frequency);
                        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        //防止用户提醒时间小于当前时间闹钟马上触发，根据频率推迟一天或一周
                        if(calendar.getTimeInMillis()<System.currentTimeMillis()){
                            if(frequency==1){
                                calendar.set(Calendar.DAY_OF_MONTH,calendar.DAY_OF_MONTH+1);
                                manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
                                Toast.makeText(getApplicationContext(),"设置闹钟时间为"+mhour+":"+mminute,Toast.LENGTH_LONG).show();
                            }else if(frequency==2){
                                calendar.set(Calendar.DAY_OF_MONTH,calendar.DAY_OF_MONTH+7);
                                manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
                                Toast.makeText(getApplicationContext(),"设置闹钟时间为"+mhour+":"+mminute,Toast.LENGTH_LONG).show();
                            }else if(frequency==0){
                                Toast.makeText(getApplicationContext(),"该时间已过，请重新设置提醒时间",Toast.LENGTH_LONG).show();
                            }

                        }

                        if(frequency ==1){
                            manager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*60*60*24,sender);
                        }else if(frequency==2){
                            manager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*60*60*24*7,sender);
                        }else{
                            manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
                            Toast.makeText(getApplicationContext(),"设置闹钟时间为"+mhour+":"+mminute,Toast.LENGTH_LONG).show();
                        }


                    }else if(isalarm.isCheck()==false){
                        Intent intentemp = new Intent(TaskSettingactivity.this, AlarmReceiver.class);

                        PendingIntent sender = PendingIntent.getBroadcast(TaskSettingactivity.this, 0, intentemp, 0);
                        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        manager.cancel(sender);
                        Toast.makeText(getApplicationContext(),"闹钟已取消//未设置闹钟",Toast.LENGTH_LONG).show();
                    }
                    setResult(3);
                    finish();

                }
            }
        });

    }
    private TimePickerDialog.OnTimeSetListener TimeListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            startTxtview.setText(i+":"+i1);
            start = i*1000+i1;
        }

    };
    private TimePickerDialog.OnTimeSetListener TimeListener2 = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            endTxtview.setText(i+":"+i1);
            end = i*1000+i1;
        }

    };
    private TimePickerDialog.OnTimeSetListener TimeListener3 = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            mhour = i ;
            mminute = i1;
            remind_time.setText(i+":"+i1);
        }

    };

}
