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
    private EditText description;
    private EditText remind_time;
    private MaterialSpinner materialSpinner;

    private ActionMenuItemView save;
    private long taskid;
    private TaskBeen tasknow;
    private Long id;
    private CheckBox checkbox;
    private int mhour;
    private int mminute;
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

        taskid = getIntent().getExtras().getLong("taskId");
        DaoMaster.DevOpenHelper devopenhelper = new DaoMaster.DevOpenHelper(getApplicationContext(),"task-db",null);
        DaoMaster daomaster = new DaoMaster(devopenhelper.getWritableDatabase());
        DaoSession daosession = daomaster.newSession();
        final TaskBeenDao taskBeenDao = daosession.getTaskBeenDao();
        description = (EditText)findViewById(R.id.task_description);
        startTxtview = (EditText)findViewById(R.id.starttime);
        remind_time = (EditText)findViewById(R.id.remind_time);
        titleView = (EditText)findViewById(R.id.tasktitle_set);
        materialSpinner = (MaterialSpinner)findViewById(R.id.spinner);
        materialSpinner.setItems("每天","每周");
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

            }
        });
        endTxtview = (EditText)findViewById(R.id.endtime);
        taskid = getIntent().getLongExtra("taskId",-1);
        if(taskid!=-1) {
            tasknow = taskBeenDao.queryBuilder().where(TaskBeenDao.Properties.Id.eq(taskid)).build().unique();
            startTxtview.setText(tasknow.getStartTime());
            endTxtview.setText(tasknow.getEndTime());
            titleView.setText(tasknow.getTitle());
            description.setText(tasknow.getDescription());
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
                if(taskid!=-1) {
                    tasknow.setEndTime(endTxtview.getText().toString());
                     tasknow.setRemindTime(remind_time.getText().toString());
                    tasknow.setTitle(titleView.getText().toString());
                    tasknow.setStartTime(startTxtview.getText().toString());
                    taskBeenDao.update(tasknow);
                }else{
                    TaskBeen temp = taskBeenDao.queryBuilder().orderDesc(TaskBeenDao.Properties.Id).limit(1).unique();
                    if(temp==null){
                        id = 1L;
                    }else{
                        id = temp.getId()+1;
                    }
                    String date = getIntent().getStringExtra("date");
                    tasknow = new TaskBeen(id,date,startTxtview.getText().toString(),endTxtview.getText().toString(),description.getText().toString(),false,remind_time.getText().toString(),titleView.getText().toString());
                    taskBeenDao.insert(tasknow);
                }
                checkbox = (CheckBox)findViewById(R.id.checkboxAlarm);
                if(tasknow.getRemindTime()!=null&&checkbox.isCheck()){
                    Intent intentemp = new Intent(TaskSettingactivity.this,AlarmReceiver.class);
                    intentemp.setAction("com.example.wangchang.wda1150_xwk1151.alarmAction");
                    PendingIntent sender = PendingIntent.getBroadcast(TaskSettingactivity.this, 0, intentemp, 0);
                    long firstTime = SystemClock.elapsedRealtime();
                    long systemTime = System.currentTimeMillis();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    calendar.set(Calendar.MINUTE, mminute);
                    calendar.set(Calendar.HOUR_OF_DAY, mhour);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    long selectTime = calendar.getTimeInMillis();
                    long time = selectTime - systemTime;
                    firstTime += time;
                    AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
                    manager.set(AlarmManager.RTC_WAKEUP, time, sender);
                }
                Intent intent = new Intent(TaskSettingactivity.this, MainActivity.class);

                startActivity(intent);
            }
        });

    }
    private TimePickerDialog.OnTimeSetListener TimeListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            startTxtview.setText(i+":"+i1);
        }

    };
    private TimePickerDialog.OnTimeSetListener TimeListener2 = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            endTxtview.setText(i+":"+i1);
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
